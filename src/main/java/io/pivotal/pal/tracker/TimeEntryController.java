package io.pivotal.pal.tracker;

import io.pivotal.pal.trackerapi.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.pivotal.pal.trackerapi.TimeEntry;

import java.util.List;

@RestController()
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @PutMapping("/time-entries/{l}")
    public ResponseEntity update(@PathVariable Long l , @RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry entry = timeEntryRepository.update(l, timeEntryToCreate);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(entry, HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{l}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long l) {
        return new ResponseEntity(timeEntryRepository.delete(l), HttpStatus.NO_CONTENT);

    }

    @GetMapping(value = "/time-entries/v2/{l}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<TimeEntry> readXml(@PathVariable  Long l) {
        TimeEntry entry = timeEntryRepository.find(l);
        if( null == entry){
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(entry, HttpStatus.OK);
    }
    @GetMapping(value = "/time-entries/{l}")
    public ResponseEntity<TimeEntry> read(@PathVariable  Long l) {
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
