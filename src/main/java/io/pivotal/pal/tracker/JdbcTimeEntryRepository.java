package io.pivotal.pal.tracker;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

private final JdbcTemplate jdbcTemplate;
    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new  JdbcTemplate(dataSource);

    }

    @Override
    public TimeEntry create(TimeEntry any) {

        KeyHolder genkh = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into time_entries (project_id,user_id,date,hours)" +
                    " values (?,?,?,?)",RETURN_GENERATED_KEYS);

            stmt.setLong(1, any.getProjectId());
            stmt.setLong(2, any.getUserId());
            stmt.setDate(3, Date.valueOf(any.getDate()));
            stmt.setInt(4, any.getHours());


            return stmt;

        },genkh);

        return find(genkh.getKey().longValue());




       // return null;
    }


    @Override
    public TimeEntry find(long timeEntryId) {


        //jdbcTemplate.query()

        return jdbcTemplate.query("select id,project_id,user_id,date,hours from time_entries where id = ?",new Object[]{timeEntryId},extractor);


        //return null;
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {

        jdbcTemplate.update("update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?", any.getProjectId(),any.getUserId(),any.getDate(),
        any.getHours(),eq);


        return find(eq);
        //return null;
    }

    @Override
    public void delete(long timeEntryId) {

         jdbcTemplate.update("delete from time_entries where id = ?",timeEntryId);

    }

    @Override
    public List<TimeEntry> list() {

        return jdbcTemplate.query("select id,project_id,user_id,date,hours from time_entries",mapper);


        // return null;
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
