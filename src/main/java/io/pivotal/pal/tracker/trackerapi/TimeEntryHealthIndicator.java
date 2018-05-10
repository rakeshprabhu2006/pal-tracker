package io.pivotal.pal.tracker.trackerapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator  implements HealthIndicator {

    private static final int MIN_NUMBER_OF_ENTRIES = 5;
    private final TimeEntryRepository timeEntryRepo;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepo) {
        this.timeEntryRepo = timeEntryRepo;
    }

    @Override
    public Health health() {

        if( timeEntryRepo.list().size() > MIN_NUMBER_OF_ENTRIES ){
           return  new Health.Builder().down().build();
        }
        return  new Health.Builder().up().build();
    }
}
