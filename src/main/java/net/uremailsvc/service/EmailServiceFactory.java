package net.uremailsvc.service;

/**
 * Factory for creating instance of email service implementation
 */
public class EmailServiceFactory {

	private EmailServiceFactory() {}

	public static IEmailService getService() {
		return new EmailServiceImpl();
	}
}
