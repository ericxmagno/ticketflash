package com.ericmagno.ticketflash.common.event;

import java.time.Instant;

/**
 * Event published when a ticket booking is requested.
 */
public record TicketRequestedEvent(
        String userId,
        String eventId,
        String seatId,
        Instant timestamp
) {}

