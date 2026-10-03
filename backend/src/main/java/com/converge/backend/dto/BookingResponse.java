package com.converge.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingResponse {

    private Long id;
    private String bookingReference;

    private Long userId;
    private String userName;
    private String userEmail;

    private Long scheduleId;
    private Long experienceId;
    private String experienceTitle;
    private String category;
    private String venueName;
    private String city;

    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Integer seatCount;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public BookingResponse() {
    }

    public BookingResponse(
            Long id,
            String bookingReference,
            Long userId,
            String userName,
            String userEmail,
            Long scheduleId,
            Long experienceId,
            String experienceTitle,
            String category,
            String venueName,
            String city,
            LocalDate scheduleDate,
            LocalTime startTime,
            LocalTime endTime,
            Integer seatCount,
            BigDecimal totalAmount,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.scheduleId = scheduleId;
        this.experienceId = experienceId;
        this.experienceTitle = experienceTitle;
        this.category = category;
        this.venueName = venueName;
        this.city = city;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatCount = seatCount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public String getExperienceTitle() {
        return experienceTitle;
    }

    public String getCategory() {
        return category;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}