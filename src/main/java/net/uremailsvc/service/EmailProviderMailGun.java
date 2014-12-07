package net.uremailsvc.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Send email by MailGun sandbox server using Jersey.
 */
class EmailProviderMailGun implements IEmailProvider {

	private static final Logger LOGGER = Logger.getLogger(EmailProviderMailGun.class.getSimpleName());

	private final Client client;
	private static final EmailProviderMailGun INST = new EmailProviderMailGun();

	private static final String API_URL = "https://api.mailgun.net/v2/sandboxe00d98ebb7a54a5196667d5b399d0eb7.mailgun.org/messages";

	private EmailProviderMailGun() {
		client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", "key-27374eaa2b79339aff3154779f61c8f0"));
	}

	public static final EmailProviderMailGun getInstance() {
		return INST;
	}

	@Override
	public String getName() {
		return "MailGun";
	}

	@Override
	public EmailResponse send(EmailRequest request) {
		EmailResponse response = new EmailResponse();
		try {
			WebResource resource = client.resource(API_URL);
			MultivaluedMapImpl data = convertMessage(request);
			ClientResponse res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, data);
			LOGGER.log(Level.INFO, res.toString());
			if (HttpStatus.SC_OK != res.getStatus()) {
				response = new EmailResponse(EmailResponse.EmailState.InternalError,
						String.valueOf(res.getStatus()), res.getStatusInfo().getReasonPhrase());
			}
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			response = new EmailResponse(EmailResponse.EmailState.InternalError, e.getMessage(), e.getLocalizedMessage());
		}
		return response;
	}

	private MultivaluedMapImpl convertMessage(EmailRequest request) {
		MultivaluedMapImpl data = new MultivaluedMapImpl();
		EmailMessage message = request.getMessage();
		data.add("from", message.getFrom());
		data.add("subject", message.getSubject());
		data.add("text", message.getContent());
		for (String s : message.getTo()) {
			data.add("to", s);
		}
		for (String s : message.getCc()) {
			data.add("cc", s);
		}
		for (String s : message.getBcc()) {
			data.add("bcc", s);
		}
		return data;
	}
}
