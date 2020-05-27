package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    //List<TimeEntry> timeEntries=new ArrayList<TimeEntry>();
    Map<Long,TimeEntry> timeEntries=new HashMap<Long, TimeEntry>();
    long timeEntryId=1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long newId = timeEntryId++;
       // timeEntry.setTimeEntryId(newId);
        TimeEntry newTimeEntry=new TimeEntry(newId,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        timeEntries.put(newId,newTimeEntry);
        return find(newId);
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        TimeEntry timeEntry= timeEntries.get(timeEntryId);
        if(timeEntry!=null)
        {
            return timeEntry;
        }
        else
        {
            return null;
        }
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
       TimeEntry updentry= new TimeEntry(timeEntryId, timeEntry.getProjectId(), timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        if(timeEntries.containsKey(timeEntryId))
       timeEntries.replace(timeEntryId, updentry);
        else
            return null;
        return updentry;
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntries.remove(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return (new ArrayList<TimeEntry>(timeEntries.values()));
    }

  /*  public ResponseEntity create(TimeEntry timeEntryToCreate) {
        timeEntry = new TimeEntry(123L,456L, LocalDate.parse("2017-01-08"),8);
        timeEntries.add(timeEntry);
        return new ResponseEntity(timeEntry, HttpStatus.CREATED);

    }

    public ResponseEntity<TimeEntry> find(long timeEntryId) {
        timeEntry = new TimeEntry(1L,123L,456L, LocalDate.parse("2017-01-08"),8);
        timeEntries.add(timeEntry);
        return new ResponseEntity(timeEntry, HttpStatus.FOUND);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>(timeEntries,HttpStatus.OK);
    }

    public ResponseEntity<TimeEntry> update(long eq, TimeEntry any) {
        List<TimeEntry> timeEntriesList =    timeEntries;
        TimeEntry timeEntryNew = new TimeEntry();

        for (TimeEntry timeentry: timeEntriesList) {
            if(timeEntry.getId() == eq){
                timeEntryNew.setTimeEntryId(any.getId());
                timeEntryNew.setTimeEntryId(any.getId());
                timeEntryNew.setHours(any.getHours());
                timeEntryNew.setParse(any.getParse());
                timeEntryNew.setProjectId(any.getProjectId());
                timeEntryNew.setUserId(any.getUserId());

                            }
        }
    return new ResponseEntity<TimeEntry>(timeEntryNew,HttpStatus.OK);

    }

    public ResponseEntity<TimeEntry> delete(long timeEntryId) {
        List<TimeEntry> timeEntryList =   timeEntries;
        TimeEntry timeEntrymock = null;
        for (TimeEntry timeEntry: timeEntryList) {
            if(timeEntry.getId() == timeEntryId){
                timeEntrymock = timeEntry;
            timeEntryList.remove(timeEntry);
            }
        }
        return new ResponseEntity<TimeEntry>(timeEntrymock,HttpStatus.OK);
    }*/
}
