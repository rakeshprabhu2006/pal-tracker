package io.pivotal.pal.trackerapi;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.pivotal.pal.trackerapi.TimeEntry;

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
    public TimeEntry delete(long l) {
        return timeEntries.remove(l);
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
