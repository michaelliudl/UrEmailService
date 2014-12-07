package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * TBD
 */
class EmailServicePostSendStage implements IEmailServiceStage {
	@Override
	public EmailResponse execStage(EmailRequest request) {
		return new EmailResponse();
	}
}
