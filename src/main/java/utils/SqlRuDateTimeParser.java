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
            Map.entry("0", "янв"), Map.entry("1", "фев"),
            Map.entry("2", "мар"), Map.entry("3", "апр"),
            Map.entry("4", "май"), Map.entry("5", "июнь"),
            Map.entry("6", "июль"), Map.entry("7", "авг"),
            Map.entry("8", "сент"), Map.entry("9", "окт"),
            Map.entry("10", "ноя"), Map.entry("11", "дек"));



    public static void main(String[] args) {
        SqlRuDateTimeParser sa = new SqlRuDateTimeParser();
        String a = "12 май 21, 01:55";
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
        if (!split[0].contains("Сегодня") || !split[0].contains("Вчера")) {
            String[] mounthsName = split[0].split(" ");
            String mounth = mounthsName[1];
            Optional<String> resultMounth = MONTHS.entrySet()
                    .stream().filter(val -> mounth.equals(val.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst();
            cal.set(Calendar.MONTH, Integer.parseInt(resultMounth.get()));
        }
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursMinutes[0].trim()));
        cal.set(Calendar.MINUTE, Integer.parseInt(hoursMinutes[1]));
        ldt = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
        ldt = LocalDateTime.parse(ldt.format(dtf), dtf);
        return ldt;
    }
}
