package com.sami.controller;

import static com.sami.utils.ApiError.error;
import static com.sami.utils.ApiResponseBuilder.errors;
import static com.sami.utils.ApiResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sami.dto.PaymentDto;
import com.sami.dto.ProfileDto;
import com.sami.dto.ShippingDto;
import com.sami.entity.AppUser;
import com.sami.entity.Payment;
import com.sami.entity.Shipping;
import com.sami.exceptions.AppException;
import com.sami.security.ActiveUserContext;
import com.sami.service.AppUserService;
import com.sami.service.ProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "my-profile")
@RequiredArgsConstructor
@Api(tags = "my profile's Data")
public class MyProfileController {

	private final ProfileService profileService;

	private final AppUserService appUserService;

	private final ActiveUserContext context;

	@PostMapping("/update")
	@ApiOperation(value = "update profile", response = ProfileDto.class)
	public ResponseEntity<JSONObject> updateProfile(@Valid @RequestBody ProfileDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		AppUser user = profileService.get(dto.getId()).orElseThrow(AppException::new);

		dto.update(user);

		profileService.update(user);

		return ok(success(ProfileDto.from(user)).getJson());
	}

	@GetMapping("/view-profile/{username}")
	@ApiOperation(value = "get user by username", response = ProfileDto.class)
	public ResponseEntity<JSONObject> viewProfile(@PathVariable String username) {

		AppUser user = appUserService.findByUsername(username);

		ProfileDto dto = ProfileDto.from(user);

		return ok(success(dto).getJson());
	}

	@PostMapping("/save/payment")
	@ApiOperation(value = "save payment", response = PaymentDto.class)
	public ResponseEntity<JSONObject> addPayment(@Valid @RequestBody PaymentDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		AppUser appUser = appUserService.findByUsername(context.getLoggedInUserName());

		Payment payment = dto.to();

		payment.setAppUser(appUser);

		profileService.savePayment(payment);

		return ok(success(PaymentDto.from(payment)).getJson());
	}

	@PutMapping("/update/payment")
	@ApiOperation(value = "update payment", response = PaymentDto.class)
	public ResponseEntity<JSONObject> updatePayment(@Valid @RequestBody PaymentDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		Payment payment = profileService.findByPaymentId(dto.getId()).orElseThrow(AppException::new);

		dto.update(payment);

		payment = profileService.updatePayment(payment);

		return ok(success(PaymentDto.from(payment)).getJson());
	}

	@GetMapping("/view/payment/{id}")
	@ApiOperation(value = "get payment by id", response = PaymentDto.class)
	public ResponseEntity<JSONObject> findByPaymentId(@PathVariable Long id) {

		Payment payment = profileService.findByPaymentId(id).orElseThrow(AppException::new);

		return ok(success(payment).getJson());
	}

	@GetMapping("/view/payments/{username}")
	@ApiOperation(value = "get payment list by username", response = PaymentDto.class)
	public ResponseEntity<JSONObject> viewPaymentListByAppUser(@PathVariable String username) {

		AppUser appUser = appUserService.findByUsername(username);

		List<Payment> payments = profileService.findByPaymentAppUser(appUser);

		List<PaymentDto> dtos = payments.stream().map(PaymentDto::from).collect(Collectors.toList());

		return ok(success(dtos).getJson());
	}

	@PostMapping("/save/shipping")
	@ApiOperation(value = "save shipping", response = ShippingDto.class)
	public ResponseEntity<JSONObject> addShipping(@Valid @RequestBody ShippingDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		AppUser appUser = appUserService.findByUsername(context.getLoggedInUserName());

		Shipping shipping = dto.to();

		shipping.setAppUser(appUser);

		profileService.saveShipping(shipping);

		return ok(success(ShippingDto.from(shipping)).getJson());
	}

	@PutMapping("/update/shipping")
	@ApiOperation(value = "update shipping", response = ShippingDto.class)
	public ResponseEntity<JSONObject> updateShipping(@Valid @RequestBody ShippingDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		Shipping shipping = profileService.findByShippingId(dto.getId()).orElseThrow(AppException::new);

		dto.update(shipping);

		shipping = profileService.updateShipping(shipping);

		return ok(success(ShippingDto.from(shipping)).getJson());
	}

	@GetMapping("/view/shipping/{id}")
	@ApiOperation(value = "get shipping by id", response = ShippingDto.class)
	public ResponseEntity<JSONObject> findByShippingId(@PathVariable Long id) {

		Shipping shipping = profileService.findByShippingId(id).orElseThrow(AppException::new);

		return ok(success(shipping).getJson());
	}

	@GetMapping("/view/shippings/{username}")
	@ApiOperation(value = "get shipping list by username", response = ShippingDto.class)
	public ResponseEntity<JSONObject> viewShippingListByAppUser(@PathVariable String username) {

		AppUser appUser = appUserService.findByUsername(username);

		List<Shipping> shippings = profileService.findByShippingAppUser(appUser);

		List<ShippingDto> dtos = shippings.stream().map(ShippingDto::from).collect(Collectors.toList());

		return ok(success(dtos).getJson());
	}

}
