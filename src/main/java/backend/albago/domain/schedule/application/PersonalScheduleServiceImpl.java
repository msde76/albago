package backend.albago.domain.schedule.application;

import backend.albago.domain.member.domain.entity.Member;
import backend.albago.domain.member.domain.repository.MemberRepository;
import backend.albago.domain.schedule.converter.PersonalScheduleConverter;
import backend.albago.domain.schedule.domain.entity.PersonalSchedule;
import backend.albago.domain.schedule.domain.repository.PersonalScheduleRepository;
import backend.albago.domain.schedule.dto.PersonalScheduleRequestDTO;
import backend.albago.domain.schedule.dto.PersonalScheduleResponseDTO;
import backend.albago.domain.schedule.exception.ScheduleException;
import backend.albago.global.error.code.status.ErrorStatus;
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

    @Override
    @Transactional
    public PersonalScheduleResponseDTO.CreatePersonalScheduleResult createPersonalSchedule(PersonalScheduleRequestDTO.CreatePersonalScheduleDTO request, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        PersonalSchedule personalSchedule = PersonalScheduleConverter.toPersonalSchedule(member, request);

        PersonalSchedule savedSchedule = personalScheduleRepository.save(personalSchedule);

        return PersonalScheduleConverter.toCreatePersonalScheduleResult(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalScheduleResponseDTO.FindPersonalScheduleResult findPersonalSchedule(LocalDate startDate, LocalDate endDate, String memberId){

        Long memberIdLong = Long.parseLong(memberId);

        Member member = memberRepository.findById(memberIdLong)
                .orElseThrow(() -> new ScheduleException(ErrorStatus.NO_SUCH_MEMBER));

        if (startDate.isAfter(endDate)) {
            throw new ScheduleException(ErrorStatus._BAD_REQUEST);
        }

        List<PersonalSchedule> personalSchedules =
                personalScheduleRepository.findByMemberIdAndDateBetweenOrderByDateAscStartTimeAsc(memberIdLong, startDate, endDate);

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

        Optional.ofNullable(request.getDate()).ifPresent(personalSchedule::setDate);
        Optional.ofNullable(request.getStartTime()).ifPresent(personalSchedule::setStartTime);
        Optional.ofNullable(request.getEndTime()).ifPresent(personalSchedule::setEndTime);
        Optional.ofNullable(request.getTitle()).ifPresent(personalSchedule::setTitle);
        Optional.ofNullable(request.getMemo()).ifPresent(personalSchedule::setMemo);
        Optional.ofNullable(request.getColor()).ifPresent(personalSchedule::setColor);

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


}
