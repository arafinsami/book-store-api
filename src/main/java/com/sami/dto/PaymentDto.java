package com.sami.dto;

import com.sami.entity.Billing;
import com.sami.entity.Payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {

	private Long id;

	private String type;

	private String cardName;

	private String cardNumber;

	private String expiryMonth;

	private String expiryYear;

	private int cvc;

	private String holderName;

	private boolean defaultPayment;

	private Billing billing;

	public Payment to() {

		Payment payment = new Payment();
		payment.setType(type);
		payment.setCardName(cardName);
		payment.setCardNumber(cardNumber);
		payment.setExpiryMonth(expiryMonth);
		payment.setExpiryYear(expiryYear);
		payment.setCvc(cvc);
		payment.setHolderName(holderName);
		payment.setDefaultPayment(defaultPayment);
		payment.setBilling(billing);
		return payment;
	}

	public static PaymentDto from(Payment payment) {

		PaymentDto dto = new PaymentDto();
		dto.setType(payment.getType());
		dto.setCardName(payment.getCardName());
		dto.setCardNumber(payment.getCardNumber());
		dto.setExpiryMonth(payment.getExpiryMonth());
		dto.setExpiryYear(payment.getExpiryYear());
		dto.setCvc(payment.getCvc());
		dto.setHolderName(payment.getHolderName());
		dto.setDefaultPayment(payment.isDefaultPayment());
		dto.setBilling(payment.getBilling());
		return dto;
	}
}
