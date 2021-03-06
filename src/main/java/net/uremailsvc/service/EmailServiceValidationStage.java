package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

import java.util.HashSet;
import java.util.Set;

/**
 * Validation stage for email addresses and contents.
 */
class EmailServiceValidationStage implements IEmailServiceStage {

	private Set<IEmailValidator> validators;

	EmailServiceValidationStage() {
		validators = new HashSet<IEmailValidator>();
		validators.add(new EmailAddressValidator());
		validators.add(new EmailContentValidator());
	}

	@Override
	public EmailResponse execStage(EmailRequest request) {
		EmailResponse response = EmailResponse.OK;
		for (IEmailValidator validator : validators) {
			response = validator.validate(request);
			if (EmailResponse.EmailState.OK != response.getStatus()) {
				return response;
			}
		}
		return response;
	}
}
