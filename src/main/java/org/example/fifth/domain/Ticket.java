package org.example.fifth.domain;

import java.math.BigDecimal;

public record Ticket(int id, Request request, Train train, BigDecimal price) {
}
