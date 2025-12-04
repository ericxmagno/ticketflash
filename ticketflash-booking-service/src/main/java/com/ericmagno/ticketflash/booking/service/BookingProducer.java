package com.ericmagno.ticketflash.booking.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ericmagno.ticketflash.common.event.TicketRequestedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for publishing booking events to Kafka.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingProducer {

    private static final String TOPIC_NAME = "ticket.requests";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Publishes a ticket requested event to Kafka.
     * Uses eventId as the key to ensure requests for the same event land on the
     * same partition.
     *
     * @param event the ticket requested event
     */
    public void publishTicketRequestedEvent(TicketRequestedEvent event) {
        log.info("Publishing booking event: [eventId={}, seatId={}]", event.eventId(), event.seatId());

        kafkaTemplate.send(TOPIC_NAME, event.eventId(), event);
    }
}
