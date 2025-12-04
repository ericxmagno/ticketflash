package com.ericmagno.ticketflash.booking.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericmagno.ticketflash.booking.service.BookingProducer;
import com.ericmagno.ticketflash.common.dto.BookingRequest;
import com.ericmagno.ticketflash.common.event.TicketRequestedEvent;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingProducer bookingProducer;

    /**
     * Accepts a booking request and publishes it to Kafka.
     * Returns HTTP 202 (Accepted) immediately without waiting for processing.
     *
     * @param request the booking request
     * @return ResponseEntity with HTTP 202 status
     */
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody @Valid BookingRequest request) {
        TicketRequestedEvent event = new TicketRequestedEvent(
                request.userId(),
                request.eventId(),
                request.seatId(),
                Instant.now());

        bookingProducer.publishTicketRequestedEvent(event);

        return ResponseEntity.accepted().body("Request accepted");
    }
}
