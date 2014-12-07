package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Default email service implementation which runs three stages:
 * 1. Validation
 * 2. Sending
 * 3. Post sending, e.g. save record to database
 */
class EmailServiceImpl implements IEmailService {

	private List<IEmailServiceStage> stages = new ArrayList<IEmailServiceStage>();

	EmailServiceImpl() {
		stages.add(new EmailServiceValidationStage());
		stages.add(new EmailServiceSendingStage());
		stages.add(new EmailServicePostSendStage());
	}

	@Override
	public EmailResponse send(EmailRequest request) {
		for (IEmailServiceStage stage : stages) {
			EmailResponse response = stage.execStage(request);
			if (response.getStatus() != EmailResponse.EmailState.OK) {
				return response;
			}
		}
		return EmailResponse.OK;
	}
}
