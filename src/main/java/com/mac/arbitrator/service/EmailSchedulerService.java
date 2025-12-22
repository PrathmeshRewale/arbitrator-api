package com.mac.arbitrator.service;

import java.time.Instant;
import java.time.LocalDateTime;

public interface EmailSchedulerService {
    void handleEmailScheduler();
    Instant getMinimumDelayMillis();
}
