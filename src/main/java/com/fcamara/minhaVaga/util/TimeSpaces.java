package com.fcamara.minhaVaga.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;


public enum TimeSpaces {
	HORA(ChronoMath.removeOneOfTimeTypeFromNow(ChronoUnit.HOURS), ChronoMath.now()),
	DIA(ChronoMath.removeOneOfTimeTypeFromNow(ChronoUnit.DAYS), ChronoMath.now()),
	SEMANA(ChronoMath.removeOneOfTimeTypeFromNow(ChronoUnit.WEEKS), ChronoMath.now()),
	MES(ChronoMath.removeOneOfTimeTypeFromNow(ChronoUnit.MONTHS), ChronoMath.now()),
	ANO(ChronoMath.removeOneOfTimeTypeFromNow(ChronoUnit.YEARS), ChronoMath.now());
	
	private final ZonedDateTime inicialTime;
	private final ZonedDateTime finalTime;
	
	private TimeSpaces(ZonedDateTime initialTime, ZonedDateTime finalTime) {
		this.inicialTime = initialTime;
		this.finalTime = finalTime;
	}

	public ZonedDateTime getInitialTime() {
		return this.inicialTime;
	}
	
	public ZonedDateTime getFinalTime() {
		return this.finalTime;
	}
}
