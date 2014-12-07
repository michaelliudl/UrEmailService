package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * Sending email stage sends email using default or specified strategy.
 */
class EmailServiceSendingStage implements IEmailServiceStage {

	private IEmailSendingStrategy strategy;

	EmailServiceSendingStage() {
		strategy = new EmailSendingRandomProviderStrategy();
	}

	EmailServiceSendingStage(IEmailSendingStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public EmailResponse execStage(EmailRequest request) {
		return strategy.send(request);
	}
}
