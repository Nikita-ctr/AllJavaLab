package org.example.fifth.domain;

import java.time.LocalDateTime;

public record Request(int id, Passanger passenger, Route route, LocalDateTime tripDateTime) {
}
