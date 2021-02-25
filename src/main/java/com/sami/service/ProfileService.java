package com.sami.service;

import static com.sami.constants.Comments.PAYMENT_SAVE_COMMENTS;
import static com.sami.constants.Comments.BILLING_SAVE_COMMENTS;
import static com.sami.constants.Comments.PROFILE_UPDATE_COMMENTS;
import static com.sami.enums.Action.SAVE;
import static com.sami.enums.Action.UPDATE;
import static com.sami.enums.ModuleName.ADD_PAYMENT;
import static com.sami.enums.ModuleName.ADD_BILLING;
import static com.sami.enums.ModuleName.PROFILE_UPDATE;
import static java.lang.String.valueOf;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sami.entity.AppUser;
import com.sami.entity.Billing;
import com.sami.entity.Payment;
import com.sami.repository.AppUserRepository;
import com.sami.repository.BillingRepository;
import com.sami.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final AppUserRepository repository;
	
	private final PaymentRepository paymentRepository;
	
	private final BillingRepository billingRepository;

	private final ActionLogService actionLogService;

	private final BCryptPasswordEncoder encoder;

	public AppUser update(AppUser user) {

		user.setPassword(encoder.encode(user.getPassword()));
		AppUser u = repository.save(user);

		actionLogService.publishActivity(
				PROFILE_UPDATE,
				UPDATE,
				valueOf(u.getId()),
				PROFILE_UPDATE_COMMENTS
		);
		return u;
	}
	
	public Payment save(Payment payment) {
		
		Payment p = paymentRepository.save(payment);
		
		Billing billing = p.getBilling();
		billing.setPayment(p);
		
		billingRepository.save(billing);

		actionLogService.publishActivity(
				ADD_PAYMENT,
				SAVE,
				valueOf(p.getId()),
				PAYMENT_SAVE_COMMENTS
		);
		
		actionLogService.publishActivity(
				ADD_BILLING,
				SAVE,
				valueOf(billing.getId()),
				BILLING_SAVE_COMMENTS
		);
		
		return p;
	}

	public Optional<AppUser> get(Long id) {
		return repository.findById(id);
	}
}
