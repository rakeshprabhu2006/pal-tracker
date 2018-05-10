package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.trackerapi.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.pivotal.pal.tracker.trackerapi.TimeEntry;

import java.util.List;

@RestController()
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;


    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            CounterService counter,
            GaugeService gauge
    ) {
        this.timeEntryRepository = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable Long l , @RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry entry = timeEntryRepository.update(l, timeEntryToCreate);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.updated");

        return new ResponseEntity(entry, HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long l) {
        timeEntryRepository.delete(l);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @GetMapping(value = "/time-entries/v2/{l}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<TimeEntry> readXml(@PathVariable  Long l) {
        TimeEntry entry = timeEntryRepository.find(l);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.read");
        return new ResponseEntity(entry, HttpStatus.OK);
    }
    @GetMapping(value = "/time-entries/{l}")
    public ResponseEntity<TimeEntry> read(@PathVariable  Long l) {
        TimeEntry entry = timeEntryRepository.find(l);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.read");
        return new ResponseEntity(entry, HttpStatus.OK);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");

        return  new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }
}
