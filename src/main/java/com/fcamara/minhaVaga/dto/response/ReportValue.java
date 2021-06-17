package com.fcamara.minhaVaga.dto.response;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;

import com.fcamara.minhaVaga.model.TypeOfPayment;
import com.fcamara.minhaVaga.model.TypeOfVehicle;

import lombok.Getter;

@Getter
public class ReportValue {
	private TypeOfVehicle type;
	private int entrance;
	private int exit;
	private BigDecimal receivedValue = new BigDecimal(0);
	private HashMap<TypeOfPayment, Integer> typeOfPayers = new HashMap<>();

	public ReportValue(TypeOfVehicle type) {
		this.type = type;
		EnumSet.allOf(TypeOfPayment.class).forEach(PayerType -> {
			this.typeOfPayers.put(PayerType, 0);
		});
	}

	public void oneMoreEntrance() {
		this.entrance++;
	};

	public void oneMoreExit() {
		this.exit++;
	}

	public void addEarn(BigDecimal totalPrice) {
		this.receivedValue = this.receivedValue.add(totalPrice);
	}

	public void oneMorePayerOfType(TypeOfPayment typeOfPayment) {
		int value = this.typeOfPayers.get(typeOfPayment) + 1;
		this.typeOfPayers.put(typeOfPayment, value);
	};
}
