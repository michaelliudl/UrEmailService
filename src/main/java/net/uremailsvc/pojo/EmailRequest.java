package net.uremailsvc.pojo;

/**
 * Service request object.
 */
public class EmailRequest {
	private String emailSvcUser;
	private EmailMessage message;

	public EmailRequest() {}

	/**
	 * Email request for the service.
	 * @param emailSvcUser Email service user name for authentication and logging.
	 * @param message Email message
	 */
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
