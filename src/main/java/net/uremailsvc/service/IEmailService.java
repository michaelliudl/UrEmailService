package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * Abstraction for email sending service.
 *
 *
 */
public interface IEmailService {
	public EmailResponse send(EmailRequest request);
}
