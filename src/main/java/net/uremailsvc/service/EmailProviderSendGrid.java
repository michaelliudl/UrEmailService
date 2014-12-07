package net.uremailsvc.service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Send email by SendGrid using Java client.
 */
class EmailProviderSendGrid implements IEmailProvider {

	private static final Logger LOGGER = Logger.getLogger(EmailProviderSendGrid.class.getSimpleName());

	private final SendGrid sendGrid;

	private static final EmailProviderSendGrid INST = new EmailProviderSendGrid();

	private EmailProviderSendGrid() {
		sendGrid = new SendGrid("michaelliu", "tXb-hHQ-e54-FUH");
	}

	public static final EmailProviderSendGrid getInstance() {
		return INST;
	}

	@Override
	public String getName() {
		return "SendGrid";
	}

	@Override
	public EmailResponse send(EmailRequest request) {
		SendGrid.Email email = convertEmail(request);
		EmailResponse result = EmailResponse.OK;
		try {
			SendGrid.Response response = sendGrid.send(email);
			LOGGER.log(Level.INFO, response.getMessage());
			if (response == null || !response.getStatus()) {
				result = new EmailResponse(EmailResponse.EmailState.InternalError,
						response.getMessage());
			}
		} catch (SendGridException sge) {
			LOGGER.log(Level.WARNING, sge.getMessage());
			result = new EmailResponse(EmailResponse.EmailState.InternalError,
					sge.getMessage());
		}
		return result;
	}

	private SendGrid.Email convertEmail(EmailRequest request) {
		SendGrid.Email email = new SendGrid.Email();
		EmailMessage message = request.getMessage();
		email.setFrom(message.getFrom());
		email.addTo(message.getTo().toArray(new String[message.getTo().size()]));
		email.addCc(message.getCc().toArray(new String[message.getCc().size()]));
		email.addBcc(message.getBcc().toArray(new String[message.getBcc().size()]));
		email.setSubject(message.getSubject());
		email.setText(message.getContent());
		return email;
	}
}
