package com.flight.query.utils;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 两个datetime相减，本项目用于返回航班总飞行时长
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Component
public class DatetimeSub {
    public String subtract(String datetime1, String datetime2) {
        String[] datetimeStore1 = datetime1.split(" ");
        String[] date1 = datetimeStore1[0].split("-");
        String[] time1 = datetimeStore1[1].split(":");

        String[] datetimeStore2 = datetime2.split(" ");
        String[] date2 = datetimeStore2[0].split("-");
        String[] time2 = datetimeStore2[1].split(":");

        LocalDateTime start = LocalDateTime.of(Integer.parseInt(date1[0]),
                Integer.parseInt(date1[1]),
                Integer.parseInt(date1[2]),
                Integer.parseInt(time1[0]),
                Integer.parseInt(time1[1]));

        LocalDateTime end = LocalDateTime.of(Integer.parseInt(date2[0]),
                Integer.parseInt(date2[1]),
                Integer.parseInt(date2[2]),
                Integer.parseInt(time2[0]),
                Integer.parseInt(time2[1]));
        Duration duration = Duration.between(start,end);

        int hours = (int) Math.floor(duration.toMinutes() / 60);
        int minute = (int)duration.toMinutes() % 60;
        if (hours >= 24) {
            hours %= 24;
        }

        StringBuffer result = new StringBuffer();
        if (duration.toDays() >= 1) {
            result.append(duration.toDays() + "d");
        }
        // 当小时和分钟都为0时
        if (hours == 0 && minute == 0) {
            return result.toString();
        } else if (hours == 0) { // 当小时为0时
            result.append(minute + "m");
        } else if (minute == 0) { // 当分钟为0时
            result.append(hours + "h");
        } else {
            result.append(hours + "h" + minute + "m");
        }
        return result.toString();
    }
}
