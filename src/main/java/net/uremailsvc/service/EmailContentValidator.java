package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Validate email subject and contents.
 */
class EmailContentValidator implements IEmailValidator {
	@Override
	public EmailResponse validate(EmailRequest request) {
		EmailMessage message = request.getMessage();
		if (StringUtils.isBlank(message.getSubject())
				|| StringUtils.isBlank(message.getContent())) {
			return new EmailResponse(EmailResponse.EmailState.Invalid,
					"Subject or content can not be empty.");
		}
		return EmailResponse.OK;
	}
}
