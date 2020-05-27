package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry any);

    TimeEntry find(long timeEntryId);

    TimeEntry update(long eq, TimeEntry any);

    void delete(long timeEntryId);

    List<TimeEntry> list();


    /*@Autowired
    private InMemoryTimeEntryRepository inMemoryTimeEntryRepository;


    public ResponseEntity create(TimeEntry timeEntryToCreate) {
      return this.inMemoryTimeEntryRepository.create(timeEntryToCreate);

    }

    public ResponseEntity<TimeEntry> find(long timeEntryId) {
       return this.inMemoryTimeEntryRepository.find(timeEntryId);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return this.inMemoryTimeEntryRepository.list();
    }

    public ResponseEntity<TimeEntry> update(long eq, TimeEntry any) {
    return inMemoryTimeEntryRepository.update(eq,any);
    }

    public ResponseEntity<TimeEntry> delete(long timeEntryId) {
        return inMemoryTimeEntryRepository.delete(timeEntryId);
    }*/
}
