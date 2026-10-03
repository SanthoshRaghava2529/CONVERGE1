package com.converge.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converge.backend.dto.BookingResponse;
import com.converge.backend.entity.Booking;
import com.converge.backend.entity.Group;
import com.converge.backend.entity.GroupDecision;
import com.converge.backend.entity.Schedule;
import com.converge.backend.entity.User;
import com.converge.backend.repository.BookingRepository;
import com.converge.backend.repository.GroupDecisionRepository;
import com.converge.backend.repository.GroupMemberRepository;
import com.converge.backend.repository.GroupRepository;
import com.converge.backend.repository.ScheduleRepository;
import com.converge.backend.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupDecisionRepository groupDecisionRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            ScheduleRepository scheduleRepository,
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            GroupDecisionRepository groupDecisionRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupDecisionRepository = groupDecisionRepository;
    }

    @Transactional
    public BookingResponse createBooking(
            String email,
            Long scheduleId,
            Integer seatCount
    ) {

        User user = findEnabledUser(email);

        return createBookingForSchedule(
                user,
                scheduleId,
                seatCount
        );
    }

    @Transactional
    public BookingResponse createBookingFromGroupDecision(
            String email,
            String groupCode,
            Integer seatCount
    ) {

        if (seatCount == null || seatCount <= 0) {
            throw new IllegalArgumentException(
                    "Seat count must be greater than zero"
            );
        }

        User user = findEnabledUser(email);

        Group group = groupRepository
                .findByGroupCode(
                        groupCode.trim().toUpperCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Group not found"
                        )
                );

        if (!groupMemberRepository
                .existsByGroupIdAndUserId(
                        group.getId(),
                        user.getId()
                )) {

            throw new IllegalArgumentException(
                    "You are not a member of this group"
            );
        }

        GroupDecision decision =
                groupDecisionRepository
                        .findByGroupId(group.getId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "No decision has been made for this group"
                                )
                        );

        return createBookingForSchedule(
                user,
                decision.getScheduleId(),
                seatCount
        );
    }

    private BookingResponse createBookingForSchedule(
            User user,
            Long scheduleId,
            Integer seatCount
    ) {

        if (seatCount == null || seatCount <= 0) {
            throw new IllegalArgumentException(
                    "Seat count must be greater than zero"
            );
        }

        Schedule schedule =
                scheduleRepository
                        .findActiveScheduleForUpdate(scheduleId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Schedule not found"
                                )
                        );

        if (schedule.getAvailableSeats() < seatCount) {
            throw new IllegalArgumentException(
                    "Not enough seats available"
            );
        }

        BigDecimal totalAmount =
                schedule.getPrice()
                        .multiply(
                                BigDecimal.valueOf(seatCount)
                        );

        schedule.setAvailableSeats(
                schedule.getAvailableSeats() - seatCount
        );

        scheduleRepository.save(schedule);

        Booking booking = new Booking();

        booking.setBookingReference(
                generateBookingReference()
        );

        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setSeatCount(seatCount);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(
                Booking.BookingStatus.CONFIRMED
        );

        Booking savedBooking =
                bookingRepository.save(booking);

        return toResponse(savedBooking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookingsByEmail(
            String email
    ) {

        User user = findEnabledUser(email);

        return bookingRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingByReference(
            String bookingReference
    ) {

        Booking booking =
                bookingRepository
                        .findByBookingReference(
                                bookingReference
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Booking not found"
                                )
                        );

        return toResponse(booking);
    }

    private User findEnabledUser(String email) {

        return userRepository
                .findByEmail(
                        email.trim().toLowerCase()
                )
                .filter(User::isEnabled)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    private BookingResponse toResponse(
            Booking booking
    ) {

        User user = booking.getUser();
        Schedule schedule = booking.getSchedule();

        return new BookingResponse(
                booking.getId(),
                booking.getBookingReference(),

                user.getId(),
                user.getFullName(),
                user.getEmail(),

                schedule.getId(),
                schedule.getExperience().getId(),
                schedule.getExperience().getTitle(),
                schedule.getExperience().getCategory(),
                schedule.getExperience().getVenueName(),
                schedule.getExperience().getCity(),

                schedule.getScheduleDate(),
                schedule.getStartTime(),
                schedule.getEndTime(),

                booking.getSeatCount(),
                booking.getTotalAmount(),
                booking.getStatus().name(),
                booking.getCreatedAt()
        );
    }

    private String generateBookingReference() {

        String reference;

        do {
            reference =
                    "CNV-" +
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

        } while (
                bookingRepository
                        .existsByBookingReference(reference)
        );

        return reference;
    }
}