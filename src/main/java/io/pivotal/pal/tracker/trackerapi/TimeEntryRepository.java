package io.pivotal.pal.tracker.trackerapi;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry any);

    TimeEntry find(long l);

    void delete(long l);

    List<TimeEntry> list();

    TimeEntry update(long l, TimeEntry expected);
}
