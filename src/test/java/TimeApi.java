import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeApi {
    // https://www.youtube.com/watch?v=OIg9lNpMJew

    /*
      Java Date
      - it's been it the language since Jan 23, 1996
      - mutability
      - Date is DateTime, but there are other classes for SQL
      - no timezones
      - not easy to use
      
      Calendar
      - still mutable
      - can't format date directly
      - performing arithmetic operations on dates not clearly supported
        e.g. time between two points
     */

    public void basics() throws Exception {
        /*
         new things:
         - new package java.time
         - new objects for representing Dates and Time
            - LocalDate
            - LocalTime
            - LocalDateTime
            - ZonedDateTime

         - Instant - момент времени, идейно близок к java.util.Date (java.util.Date имеет метод toInstant()
         - Duration - мера времени в (мили)секундах - This class models a quantity or amount of time in terms of seconds and nanoseconds
         - Period - основано на человеческом понимании времени - This class models a quantity or amount of time in terms of years, months and days

         */
    }

    //Create a sample Date - June 27th, 1491
    public LocalDate createSampleLocalDate() throws Exception {
        return LocalDate.of(1491, Month.JUNE, 28);
    }

    //Create a sample Time - 13:51
    public LocalTime createSampleLocalTime() throws Exception {
        return LocalTime.of(13, 51);
    }

    //Create a sample LocalDateTime of the above
    public LocalDateTime createSampleLocalDateTime() throws Exception {
        return LocalDateTime.of(1491, Month.JUNE, 28, 13, 51);
    }

    //Create a sample LocalDateTime of the above
    public LocalDateTime createComponentDateTime() throws Exception {
        return LocalDateTime.of(createSampleLocalDate(), createSampleLocalTime());
    }

    public LocalDate getTodayFromLocalDateTime() {
        return LocalDateTime.now().toLocalDate();
    }

    public int getDifferenceBetweenParisAndLondon() {
        ZonedDateTime paris = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        ZonedDateTime london = ZonedDateTime.now(ZoneId.of("Europe/London"));

        return paris.getHour() - london.getHour();
    }

    /*
        working with Date and Time
     */

    public boolean isTodayAfterTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return tomorrow.isAfter(today);
    }

    public DayOfWeek getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfWeek();
    }

    public int howManyDaysUntilLongestDayOfTheYear() {
        LocalDate today = LocalDate.now();
        LocalDate longestDay = today.with(Month.JUNE).withDayOfMonth(21);
        return Period.between(today, longestDay).getDays();
    }

    public Duration howLongUntilNewYear() {
        ZonedDateTime here = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        ZonedDateTime gmtNewYear = ZonedDateTime.of(2019, 12, 31, 23, 59, 59, 0, ZoneId.of("Europe/London")); //??

        return Duration.between(here, gmtNewYear);
    }

    public LocalDate createDateFromJavaUtilDate() {
        return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public List<DayOfWeek> lastDaysOfMonthsInYear(int year) {
//        List<DayOfWeek> list = new ArrayList<>();
//
//        for (Month month : Month.values()) {
//            DayOfWeek day = LocalDate.now().withYear(year)
//                    .with(month).with(TemporalAdjusters.lastDayOfMonth()).getDayOfWeek();
//            list.add(day);
//        }
//
//        return list;
//
        // 1. Months
        // 2. Apply a transformation
        // 3. Collect to a list

        return Stream.of(Month.values()).map(month -> LocalDate.now()
                .withYear(year)
                .with(month)
                .with(TemporalAdjusters.lastDayOfMonth()).getDayOfWeek())
                .collect(Collectors.toList());

    }

    /*
     Parsing and formatting

     - DateTimeFormatter
     - nice predefined formatters such as DateTimeFormatter.ISO_DATE
     - can build a custom pattern
     - .format and .parse conveniently on objects

     */

    public String formatSimpleDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    //MM/DD/yyyy
    public String formatMonthDayYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM/DD/yyyy"));
    }

    public LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString);
    }


    enum Quarter {
        FIRST, SECOND, THIRD, FOURTH
    }

    class QuarterOfYearQuery implements TemporalQuery<Quarter> {
        @Override
        public Quarter queryFrom(TemporalAccessor temporal) {
            LocalDate now = LocalDate.from(temporal);

            if (now.isBefore(now.with(Month.APRIL).withDayOfMonth(1))) {
                return Quarter.FIRST;
            } else if (now.isBefore(now.with(Month.JULY).withDayOfMonth(1))) {
                return Quarter.SECOND;
            } else if (now.isBefore(now.with(Month.NOVEMBER).withDayOfMonth(1))) {
                return Quarter.THIRD;
            } else {
                return Quarter.FOURTH;
            }
        }
    }

    public Quarter getQuarterOfYear(LocalDate date) {
        return date.query(new QuarterOfYearQuery());
    }


    @Test
    public void test1() throws Exception {
        System.out.println(howLongUntilNewYear());
    }
}
