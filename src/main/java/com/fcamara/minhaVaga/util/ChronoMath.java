package com.fcamara.minhaVaga.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class ChronoMath {

	public static long secondsBetween(ZonedDateTime entraceTime, ZonedDateTime exitTime) {
		return ChronoUnit.SECONDS.between(entraceTime, exitTime);
	}

	public static boolean hasMoreThanAYearBetweenDates(ZonedDateTime entraceTime, ZonedDateTime exitTime) {
		return ChronoUnit.DAYS.between(entraceTime, exitTime) > 366;
	}
	
	public static boolean hasMoreThanOneHourBetweenDates(ZonedDateTime entraceTime, ZonedDateTime exitTime) {
		return ChronoUnit.HOURS.between(entraceTime, exitTime) > 1;
	}

	public static ZonedDateTime addAYear(ZonedDateTime entranceTime) {
		return ChronoUnit.DAYS.addTo(entranceTime, 366);
	}
}
