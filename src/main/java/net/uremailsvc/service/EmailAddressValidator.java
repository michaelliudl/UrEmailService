package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Use regex to validate email addresses
 */
class EmailAddressValidator implements IEmailValidator {

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;

	EmailAddressValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public EmailResponse validate(EmailRequest request) {
		EmailMessage message = request.getMessage();
		EmailResponse response = validate(message.getFrom());
		if (EmailResponse.EmailState.OK != response.getStatus()) {
			return response;
		}
		if (CollectionUtils.isEmpty(message.getTo())) {
			return new EmailResponse(EmailResponse.EmailState.Invalid,
					"Email to address is empty.");
		}
		response = validate(message.getTo());
		if (EmailResponse.EmailState.OK != response.getStatus()) {
			return response;
		}
		response = validate(message.getCc());
		if (EmailResponse.EmailState.OK != response.getStatus()) {
			return response;
		}
		response = validate(message.getBcc());
		if (EmailResponse.EmailState.OK != response.getStatus()) {
			return response;
		}
		return response;

	}

	private EmailResponse validate(List<String> addresses) {
		for (String address : addresses) {
			EmailResponse response = validate(address);
			if (EmailResponse.EmailState.OK != response.getStatus()) {
				return response;
			}
		}
		return EmailResponse.OK;
	}

	private EmailResponse validate(String address) {
		Matcher matcher = pattern.matcher(address);
		if (matcher.matches()) {
			return EmailResponse.OK;
		}
		return new EmailResponse(EmailResponse.EmailState.Invalid,
				"Invalid email address: " + address);
	}
}
