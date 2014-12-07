package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * Abstraction for different email sending provider.
 */
interface IEmailProvider {
	/**
	 *
	 * @return Provider's name
	 */
	String getName();

	/**
	 * Send email using the provider associated with implementation
	 * @param request Email request
	 * @return Email response
	 */
	EmailResponse send(EmailRequest request);
}
