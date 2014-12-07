package net.uremailsvc.service;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Send email by Mandrill using Java library lutung.
 */
class EmailProviderMandrill implements IEmailProvider {

	private static final Logger LOGGER = Logger.getLogger(EmailProviderMandrill.class.getSimpleName());

	private final MandrillApi api;
	private static final EmailProviderMandrill INST = new EmailProviderMandrill();

	private static final String SENT = "sent";

	private EmailProviderMandrill() {
		api = new MandrillApi("VNijesACNPMozHp1h02ZsQ");
	}

	public static final EmailProviderMandrill getInstance() {
		return INST;
	}

	@Override
	public String getName() {
		return "Mandrill";
	}

	@Override
	public EmailResponse send(EmailRequest request) {
		MandrillMessage message = convertEmail(request);
		EmailResponse response = EmailResponse.OK;
		try {
			MandrillMessageStatus[] status = api.messages().send(message, false);
			if (status == null || status.length == 0) {
				response = new EmailResponse(EmailResponse.EmailState.InternalError,
						"No response from Mandrill.");
			}
			boolean allFail = true;
			for (MandrillMessageStatus s : status) {
				if (SENT.equalsIgnoreCase(s.getStatus())) {
					allFail = false;
					break;
				}
			}
			if (allFail) {
				response = new EmailResponse(EmailResponse.EmailState.InternalError,
						"All send request to Mandrill failed.");
			}
			LOGGER.log(Level.INFO, status.toString());
		} catch (MandrillApiError mae) {
			LOGGER.log(Level.WARNING, mae.getMandrillErrorMessage());
			response = new EmailResponse(EmailResponse.EmailState.InternalError,
					mae.getMandrillErrorMessage());
		} catch (IOException ioe) {
			LOGGER.log(Level.WARNING, ioe.getMessage());
			response = new EmailResponse(EmailResponse.EmailState.InternalError,
					ioe.getMessage());
		}
		return response;
	}

	private MandrillMessage convertEmail(EmailRequest request) {
		MandrillMessage message = new MandrillMessage();
		EmailMessage msg = request.getMessage();
		message.setSubject(msg.getSubject());
		message.setText(msg.getContent());
		message.setFromEmail(msg.getFrom());
		List<MandrillMessage.Recipient> recipients = convertRecipient(msg.getTo(), MandrillMessage.Recipient.Type.TO);
		recipients.addAll(convertRecipient(msg.getCc(), MandrillMessage.Recipient.Type.CC));
		recipients.addAll(convertRecipient(msg.getBcc(), MandrillMessage.Recipient.Type.BCC));
		message.setTo(recipients);
		return message;
	}

	private List<MandrillMessage.Recipient> convertRecipient(List<String> emails,
	                                                         MandrillMessage.Recipient.Type type) {
		List<MandrillMessage.Recipient> result = new ArrayList<MandrillMessage.Recipient>(emails.size());
		for (String email : emails) {
			MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
			recipient.setEmail(email);
			recipient.setType(type);
			result.add(recipient);
		}
		return result;
	}
}
