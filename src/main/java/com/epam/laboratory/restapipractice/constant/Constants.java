package com.epam.laboratory.restapipractice.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss z");
    public static final ZoneId ZONE_UTC_0 = ZoneId.of("UTC");
}
