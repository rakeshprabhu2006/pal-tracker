package io.pivotal.pal.tracker.trackerapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> timeEntries = new HashMap<>();

     @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long id = timeEntries.size() + 1L;
        TimeEntry newTimeEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        timeEntries.put(id, newTimeEntry);
        return newTimeEntry;

    }

    @Override
    public TimeEntry find(long l) {
        return timeEntries.get(l);
    }

    @Override
    public void delete(long l) {
        timeEntries.remove(l);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntries.values());
    }

    @Override
    public TimeEntry update(long l, TimeEntry expected) {
        TimeEntry updatedEntry = new TimeEntry(
                l,
                expected.getProjectId(),
                expected.getUserId(),
                expected.getDate(),
                expected.getHours()
        );

        timeEntries.replace(l, updatedEntry);
        return updatedEntry;
    }
}
