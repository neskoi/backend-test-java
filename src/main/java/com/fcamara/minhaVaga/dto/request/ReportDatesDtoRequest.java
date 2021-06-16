package com.fcamara.minhaVaga.dto.request;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ReportDatesDtoRequest {

	@NotNull
	private ZonedDateTime entraceTime;
	
	@NotNull
	private ZonedDateTime exitTime;
}
