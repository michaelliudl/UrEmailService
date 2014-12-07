package net.uremailsvc.pojo;

/**
 * Service response object for email.
 */
public class EmailResponse {
	private EmailState status = EmailState.OK;
	private String error = "";

	public static final EmailResponse OK = new EmailResponse();

	public EmailResponse() {}

	public EmailResponse(EmailState status, String error) {
		this.status = status;
		this.error = error;
	}

	public EmailState getStatus() {return status;}
	public String getError() {return error;}

	public enum EmailState {
		OK,
		Invalid,
		InternalError
	}
}
