package io.pivotal.pal.trackerapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.pivotal.pal.trackerapi.TimeEntry;

import java.util.List;

@RestController()
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntrsyToCreate) {
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable long l , @RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry entry = timeEntryRepository.update(l, timeEntryToCreate);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(entry, HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long l) {
        return new ResponseEntity(timeEntryRepository.delete(l), HttpStatus.NO_CONTENT);

    }

    @GetMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> read(long l) {
        TimeEntry entry = timeEntryRepository.find(l);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(entry, HttpStatus.OK);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return  new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }
}
