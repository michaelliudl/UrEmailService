package net.uremailsvc.pojo;

/**
 * Service request object.
 */
public class EmailRequest {
	private String emailSvcUser;
	private EmailMessage message;

	public EmailRequest() {}

	public EmailRequest(final String emailSvcUser, final EmailMessage message) {
		this.emailSvcUser = emailSvcUser;
		this.message = message;
	}

	public String getEmailSvcUser() {
		return emailSvcUser;
	}

	public EmailMessage getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "EmailRequest{" +
				"emailSvcUser='" + emailSvcUser + '\'' +
				", message=" + message +
				'}';
	}
}
