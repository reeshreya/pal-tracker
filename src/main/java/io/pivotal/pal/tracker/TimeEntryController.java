package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;



    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }
   @PostMapping
   public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

       TimeEntry newTimeEntry= timeEntryRepository.create(timeEntryToCreate);
       return new ResponseEntity<>(newTimeEntry,HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long timeEntryId) {
       TimeEntry timeEntry=timeEntryRepository.find(timeEntryId);
       if(timeEntry!=null) {
           return new ResponseEntity<>(timeEntry,HttpStatus.OK);
       }
       else
       {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries= timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntries,HttpStatus.OK);
    }
    @PutMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updateTimeEntry= timeEntryRepository.update(timeEntryId,expected);
        if(updateTimeEntry!=null)
        {
            return new ResponseEntity<TimeEntry>(updateTimeEntry,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable Long timeEntryId) {
   //     if(timeEntryRepository.find(timeEntryId)!=null) {
            timeEntryRepository.delete(timeEntryId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    //    }
   //     else
     //       return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
