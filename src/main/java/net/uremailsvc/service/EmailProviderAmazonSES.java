package net.uremailsvc.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Send email by Amazon SES using AWS SDK.
 * Not working right now because my AWS account is suspended.
 */
class EmailProviderAmazonSES implements IEmailProvider {

	private static final Logger LOGGER = Logger.getLogger(EmailProviderAmazonSES.class.getSimpleName());

	private final AmazonSimpleEmailServiceClient client;
	private static final EmailProviderAmazonSES INST = new EmailProviderAmazonSES();

	private EmailProviderAmazonSES() {
		AWSCredentials credentials = new AWSCredentials() {
			@Override
			public String getAWSAccessKeyId() {
				return null;
			}

			@Override
			public String getAWSSecretKey() {
				return null;
			}
		};
		client = new AmazonSimpleEmailServiceClient(credentials);
		client.setRegion(Region.getRegion(Regions.DEFAULT_REGION));
	}

	public static final EmailProviderAmazonSES getInstance() {
		return INST;
	}

	@Override
	public String getName() {
		return "AmazonSES";
	}

	@Override
	public EmailResponse send(EmailRequest request) {
		EmailResponse response = new EmailResponse();
		try {
			SendEmailRequest req = convertRequest(request);
			SendEmailResult result = client.sendEmail(req);
			LOGGER.log(Level.INFO, result.toString());
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			response = new EmailResponse(EmailResponse.EmailState.InternalError, e.getMessage(), e.getLocalizedMessage());
		}
		return response;
	}

	private SendEmailRequest convertRequest(EmailRequest request) {
		EmailMessage message = request.getMessage();
		Message msg = new Message().withSubject(
				new Content().withData(message.getSubject())
		).withBody(
				new Body().withText(new Content().withData(message.getContent()))
		);
		return new SendEmailRequest().withSource(
				message.getFrom()
		).withDestination(
				new Destination().withToAddresses(
						message.getTo()
				).withCcAddresses(
						message.getCc()
				).withBccAddresses(
						message.getBcc()
				)
		).withMessage(
				msg
		);
	}
}
