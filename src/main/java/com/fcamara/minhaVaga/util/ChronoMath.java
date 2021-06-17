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

	public static ZonedDateTime now() {
		return ZonedDateTime.now();
	}

	public static ZonedDateTime addOneOfTimeTypeFromNow(ChronoUnit time, ZonedDateTime date) {
		return time.addTo(date, -1);
	}
	
	public static ZonedDateTime removeOneOfTimeTypeFromNow(ChronoUnit time) {
		return time.addTo(ZonedDateTime.now(), -1);
	}

}
