package utils;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class SqlRuDateTimeParser implements DateTimeParser {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd', 'HH:mm");
    private  Calendar cal = Calendar.getInstance();

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "0"), Map.entry("фев", "1"),
            Map.entry("мар", "2"), Map.entry("апр", "3"),
            Map.entry("май", "4"), Map.entry("июнь", "5"),
            Map.entry("июль", "6"), Map.entry("авг", "7"),
            Map.entry("сент", "8"), Map.entry("окт", "9"),
            Map.entry("ноя", "10"), Map.entry("дек", "11"));



    public static void main(String[] args) {
        SqlRuDateTimeParser sa = new SqlRuDateTimeParser();
        String a = "20 сент 21, 01:55";
        System.out.println(sa.parse(a));
    }

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime date;
        if (parse.contains("Сегодня")) {
            date = getLocalDateTime(parse, cal);
        }
        if (parse.contains("Вчера")) {
           cal.add(Calendar.DATE, -1);
        }
        date = getLocalDateTime(parse, cal);
        return date;
    }

    private LocalDateTime getLocalDateTime(String parse, Calendar cal) {
        LocalDateTime ldt;
        String[] split = parse.split(",");
        String[] hoursMinutes = split[1].split(":");
        if (!split[0].contains("Сегодня") && !split[0].contains("Вчера")) {
            String[] mounthsName = split[0].split(" ");
            String mounth = mounthsName[1];
            int day =  Integer.parseInt(mounthsName[0]);
            int year = Integer.parseInt(mounthsName[2]);
            cal.set(Calendar.MONTH, Integer.parseInt(MONTHS.get(mounth)));
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.YEAR, year);
        }
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursMinutes[0].trim()));
        cal.set(Calendar.MINUTE, Integer.parseInt(hoursMinutes[1]));
        ldt = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
        ldt = LocalDateTime.parse(ldt.format(dtf), dtf);
        return ldt;
    }
}
