package net.uremailsvc.pojo;

/**
 * Service response object for email.
 */
public class EmailResponse {
	private EmailState status = EmailState.OK;
	private String shortError = "";
	private String longError = "";

	public EmailResponse() {}

	public EmailResponse(EmailState status, String shortError, String longError) {
		this.status = status;
		this.shortError = shortError;
		this.longError = longError;
	}

	public EmailState getStatus() {return status;}
	public String getShortError() {return shortError;}
	public String getLongError() {return longError;}

	public enum EmailState {
		OK,
		Invalid,
		InternalError
	}
}
