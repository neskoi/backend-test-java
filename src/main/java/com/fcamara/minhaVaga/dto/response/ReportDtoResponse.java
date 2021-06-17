package com.fcamara.minhaVaga.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.fcamara.minhaVaga.model.CarParkUsage;
import com.fcamara.minhaVaga.model.TypeOfVehicle;

import lombok.Getter;

@Getter
public class ReportDtoResponse {
	private int totalEntrances;
	private int totalExits;
	private BigDecimal totalReceivedValue = new BigDecimal(0);
	private List<ReportValue> reportValue;

	public ReportDtoResponse(List<ReportValue> valuesToReport) {
		this.reportValue = valuesToReport;
		this.reportValue.forEach(value -> {
			this.totalEntrances += value.getEntrance();
			this.totalExits += value.getExit();
			this.totalReceivedValue = this.totalReceivedValue.add(value.getReceivedValue());
		});
	}

	public static ReportDtoResponse createReport(List<CarParkUsage> data) {
		List<ReportValue> valuesToReport = new ArrayList<ReportValue>();

		EnumSet.allOf(TypeOfVehicle.class).forEach(type -> {
			ReportValue valueToReport = new ReportValue(type);
			data.forEach(register -> {
				if (type == register.getVehicle().getModel().getTypeOfVehicle()) {
					valueToReport.oneMoreEntrance();
					valueToReport.oneMorePayerOfType(register.getTypeOfPayment());
					if (register.getExitTime() != null) {
						valueToReport.oneMoreExit();
						valueToReport.addEarn(register.getTotalPrice());
					}
				}
			});
			if (valueToReport.getEntrance() > 0)
				valuesToReport.add(valueToReport);
		});

		return new ReportDtoResponse(valuesToReport);
	}
}
