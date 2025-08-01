package backend.albago.domain.schedule.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.schedule.converter.PersonalScheduleConverter;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.domain.repository.PersonalScheduleRepository;
import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;
import backend.albago.domain.schedule.exception.ScheduleException;
import backend.albago.domain.team.domain.entity.Team;
import backend.albago.domain.team.domain.repository.TeamRepository;
import backend.albago.global.error.code.status.ErrorStatus;
import backend.albago.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalScheduleServiceImpl implements PersonalScheduleService {

    private final MemberRepository memberRepository;
    private final PersonalScheduleRepository personalScheduleRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public PersonalScheduleResponseDTO.CreatePersonalScheduleResult createPersonalSchedule(PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));

        PersonalSchedule personalSchedule = PersonalScheduleConverter.toPersonalSchedule(member, team, request);

        PersonalSchedule savedSchedule = personalScheduleRepository.save(personalSchedule);

        return PersonalScheduleConverter.toCreatePersonalScheduleResult(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalScheduleResponseDTO.FindPersonalScheduleResult findPersonalSchedule(LocalDate startDate, LocalDate endDate, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        if (startDate.isAfter(endDate)) {
            throw new ScheduleException(ErrorStatus._BAD_REQUEST);
        }

        List<PersonalSchedule> personalSchedules = personalScheduleRepository.findByMemberIdAndDateRangeOrderByStartTimeAsc(
                memberIdLong, startDate, endDate
        );

        return PersonalScheduleConverter.toFindPersonalScheduleResult(memberIdLong, personalSchedules);
    }

    @Override
    @Transactional
    public PersonalScheduleResponseDTO.UpdatePersonalScheduleResult updatePersonalSchedule(PersonalScheduleRequestDTO.UpdatePersonalScheduleDTO request, Long scheduleId, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        PersonalSchedule personalSchedule = personalScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.SCHEDULE_NOT_FOUND));

        if (!personalSchedule.getMember().getId().equals(requestMember.getId())) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }

        Optional.ofNullable(request.getName()).ifPresent(personalSchedule::setName);
        Optional.ofNullable(request.getScheduleType()).ifPresent(personalSchedule::setScheduleType);

        if (request.getTeamId() != null) {
            Team newTeam = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.TEAM_NOT_FOUND));
            personalSchedule.setTeam(newTeam);
        }

        Optional.ofNullable(request.getStartTime()).ifPresent(personalSchedule::setStartTime);
        Optional.ofNullable(request.getEndTime()).ifPresent(personalSchedule::setEndTime);
        Optional.ofNullable(request.getMemo()).ifPresent(personalSchedule::setMemo);
        Optional.ofNullable(request.getColor()).ifPresent(personalSchedule::setColor);

        Optional.ofNullable(request.getHourlyWage()).ifPresent(personalSchedule::setHourlyWage);
        Optional.ofNullable(request.getWeeklyAllowance()).ifPresent(personalSchedule::setWeeklyAllowance);
        Optional.ofNullable(request.getNightAllowance()).ifPresent(personalSchedule::setNightAllowance);
        Optional.ofNullable(request.getNightRate()).ifPresent(personalSchedule::setNightRate);
        Optional.ofNullable(request.getOvertimeAllowance()).ifPresent(personalSchedule::setOvertimeAllowance);
        Optional.ofNullable(request.getOvertimeRate()).ifPresent(personalSchedule::setOvertimeRate);
        Optional.ofNullable(request.getHolidayAllowance()).ifPresent(personalSchedule::setHolidayAllowance);
        Optional.ofNullable(request.getHolidayRate()).ifPresent(personalSchedule::setHolidayRate);
        Optional.ofNullable(request.getDeductions()).ifPresent(personalSchedule::setDeductions);


        personalScheduleRepository.save(personalSchedule);

        return PersonalScheduleConverter.toUpdatePersonalScheduleResult(personalSchedule);
    }

    @Override
    @Transactional
    public void deletePersonalSchedule(Long scheduleId, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member requestMember = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        PersonalSchedule personalSchedule = personalScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.SCHEDULE_NOT_FOUND));

        if (!personalSchedule.getMember().getId().equals(requestMember.getId())) {
            throw new ScheduleException(ErrorStatus._FORBIDDEN);
        }

        personalScheduleRepository.delete(personalSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalScheduleResponseDTO.FindAllPersonalScheduleResult findAllPersonalSchedule(String memberIdString) {

        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NO_SUCH_MEMBER));

        List<PersonalSchedule> personalSchedules = personalScheduleRepository.findByMemberOrderByStartTimeDesc(member);

        return PersonalScheduleConverter.toFindAllPersonalScheduleResult(member, personalSchedules);
    }
}