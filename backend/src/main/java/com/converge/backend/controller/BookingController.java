package com.converge.backend.controller;

import com.converge.backend.dto.BookingResponse;
import com.converge.backend.security.JwtService;
import com.converge.backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final JwtService jwtService;

    public BookingController(
            BookingService bookingService,
            JwtService jwtService
    ) {
        this.bookingService = bookingService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long scheduleId,
            @RequestParam Integer seatCount
    ) {

        String token = authorizationHeader.substring(7);

        String email = jwtService.extractEmail(token);

        BookingResponse booking =
                bookingService.createBooking(
                        email,
                        scheduleId,
                        seatCount
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }

    @PostMapping("/group/{groupCode}")
    public ResponseEntity<BookingResponse> createGroupBooking(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupCode,
            @RequestParam Integer seatCount
    ) {

        String token = authorizationHeader.substring(7);

        String email = jwtService.extractEmail(token);

        BookingResponse booking =
                bookingService.createBookingFromGroupDecision(
                        email,
                        groupCode,
                        seatCount
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        String token = authorizationHeader.substring(7);

        String email = jwtService.extractEmail(token);

        List<BookingResponse> bookings =
                bookingService.getUserBookingsByEmail(email);

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingReference}")
    public ResponseEntity<BookingResponse> getBooking(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String bookingReference
    ) {

        String token = authorizationHeader.substring(7);

        String email = jwtService.extractEmail(token);

        BookingResponse booking =
                bookingService.getBookingByReference(
                        bookingReference
                );

        if (!booking.getUserEmail().equalsIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "You are not authorized to view this booking"
            );
        }

        return ResponseEntity.ok(booking);
    }
}