package com.ericmagno.ticketflash.common.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for booking requests.
 */
public record BookingRequest(
        @NotNull(message = "userId cannot be null")
        String userId,
        
        @NotNull(message = "eventId cannot be null")
        String eventId,
        
        @NotNull(message = "seatId cannot be null")
        String seatId
) {}

