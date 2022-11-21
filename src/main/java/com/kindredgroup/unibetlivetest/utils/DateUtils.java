package com.kindredgroup.unibetlivetest.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static final ZoneId UNIBET_TIMEZONE = ZoneId.of("Europe/Paris");

    private DateUtils() {}

    public static Date getDateBeforeMinutes(final int minutes) {
        return Date.from(
                LocalDateTime.now().minusMinutes(minutes).atZone(UNIBET_TIMEZONE).toInstant()
        );
    }

}
