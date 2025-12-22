package com.mac.arbitrator.config;

import com.mac.arbitrator.service.EmailSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DynamicSchedulerConfig implements SchedulingConfigurer {

    private final EmailSchedulerService emailSchedulerService;

    @Bean
    public Executor schedulerExecutor() {
        // Single-threaded scheduler; can be changed to a thread pool if needed
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(schedulerExecutor());

        // Master dynamic task — runs every minute and checks if any scheduler should execute
        taskRegistrar.addTriggerTask(
                () -> {
                    try {
                            emailSchedulerService.handleEmailScheduler();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                new Trigger() {
                    @Override
                    public Instant nextExecution(TriggerContext context) {
                        return emailSchedulerService.getMinimumDelayMillis();
                    }
                }
        );
    }


}
