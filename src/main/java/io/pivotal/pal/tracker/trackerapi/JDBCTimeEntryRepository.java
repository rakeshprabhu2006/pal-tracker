package io.pivotal.pal.tracker.trackerapi;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JDBCTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;
    private final String sqlString ="INSERT INTO time_entries (project_id, user_id, date, hours) " +
            "VALUES (?, ?, ?, ?)";

    public JDBCTimeEntryRepository(DataSource source){
        this.jdbcTemplate = new JdbcTemplate(source);
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

     @Override
    public TimeEntry create(TimeEntry timeEntry) {
         KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

         this.jdbcTemplate.update(connection -> {
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                             "VALUES (?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS
             );

             statement.setLong(1, timeEntry.getProjectId());
             statement.setLong(2, timeEntry.getUserId());
             statement.setDate(3, Date.valueOf(timeEntry.getDate()));
             statement.setInt(4, timeEntry.getHours());

             return statement;
         }, generatedKeyHolder);
         return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long l) {
        return this.jdbcTemplate.query("select id, project_id, user_id, date, hours from time_entries where id = ?",
                new Object[]{l}, extractor);
    }

    @Override
    public void delete(long l) {
         this.jdbcTemplate.update("delete from time_entries where id = ?", l);
    }

    @Override
    public List<TimeEntry> list() {
        return this.jdbcTemplate.query("select id, project_id, user_id, date, hours from time_entries", mapper);
    }

    @Override
    public TimeEntry update(long l, TimeEntry expected) {
        this.jdbcTemplate.update("update time_entries  set project_id=?, user_id=?, date=?, hours=? where id = ?", expected.getProjectId(),
                expected.getUserId(),
                Date.valueOf(expected.getDate()),
                expected.getHours(), l);

        return find(l);
    }
}
