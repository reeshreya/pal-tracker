package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.TimeZone;

@SpringBootApplication
public class PalTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }

   // @Bean
  //  public TimeEntryRepository timeEntryRepository()
    //{
       // return new InMemoryTimeEntryRepository();
   // }



    @Bean
    public TimeEntryRepository timeEntryRepository(DataSource dataSource){
       // setUp();
        return new JdbcTimeEntryRepository(dataSource);
    }


}
