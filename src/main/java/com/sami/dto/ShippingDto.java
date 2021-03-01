package com.sami.dto;

import com.sami.entity.Shipping;

import lombok.Data;

@Data
public class ShippingDto {

	private Long id;

	private String shippingName;
	
	private boolean defaultshipping;

	private String shippingStreet1;

	private String shippingStreet2;

	private String shippingCity;

	private String shippingState;

	private String shippingCountry;

	private String shippingZipcode;

	public Shipping to() {

		Shipping shipping = new Shipping();
		shipping.setShippingName(shippingName);
		shipping.setDefaultshipping(defaultshipping);
		shipping.setShippingStreet1(shippingStreet1);
		shipping.setShippingStreet2(shippingStreet2);
		shipping.setShippingCity(shippingCity);
		shipping.setShippingState(shippingState);
		shipping.setShippingCountry(shippingCountry);
		shipping.setShippingZipcode(shippingZipcode);
		return shipping;
	}

	public void update(Shipping shipping) {

		shipping.setShippingName(shippingName);
		shipping.setDefaultshipping(defaultshipping);
		shipping.setShippingStreet1(shippingStreet1);
		shipping.setShippingStreet2(shippingStreet2);
		shipping.setShippingCity(shippingCity);
		shipping.setShippingState(shippingState);
		shipping.setShippingCountry(shippingCountry);
		shipping.setShippingZipcode(shippingZipcode);
	}

	public static ShippingDto from(Shipping shipping) {

		ShippingDto dto = new ShippingDto();
		dto.setId(shipping.getId());
		dto.setShippingName(shipping.getShippingName());
		dto.setDefaultshipping(shipping.isDefaultshipping());
		dto.setShippingStreet1(shipping.getShippingStreet1());
		dto.setShippingStreet2(shipping.getShippingStreet2());
		dto.setShippingCity(shipping.getShippingCity());
		dto.setShippingState(shipping.getShippingState());
		dto.setShippingCountry(shipping.getShippingCountry());
		dto.setShippingZipcode(shipping.getShippingZipcode());
		return dto;
	}
}
