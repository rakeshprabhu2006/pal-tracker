package io.pivotal.pal.trackerapi;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry any);

    TimeEntry find(long l);

    TimeEntry delete(long l);

    List<TimeEntry> list();

    TimeEntry update(long l, TimeEntry expected);
}
