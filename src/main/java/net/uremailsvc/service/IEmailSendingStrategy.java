package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * Abstraction for strategy to choose among several email sending providers.
 */
interface IEmailSendingStrategy {
	/**
	 * Email sending strategy
	 * @param request Email request
	 * @return Email response
	 */
	EmailResponse send(EmailRequest request);
}
