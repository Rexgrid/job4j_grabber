package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    private static Connection connection;

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            initConnection();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("DateBase", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(readSettings())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static int readSettings() {
        Settings settings = getSettings();
        return Integer.parseInt(settings.getValues("rabbit.interval"));
    }


    private static Settings getSettings() {
        Settings settings = new Settings();
        ClassLoader cl = Settings.class.getClassLoader();
        try (InputStream input = cl.getResourceAsStream("rabbit.properties")) {
            settings.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void initConnection()  {
       Settings settings = getSettings();
       try {
           Class.forName(settings.getValues("driver"));
           connection = DriverManager.getConnection(settings.getValues("url"),
                   settings.getValues("user"),
                   settings.getValues("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            try (PreparedStatement statement = connection.prepareStatement("insert into rabbit(created_date) values (?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


