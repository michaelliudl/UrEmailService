package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Randomly selecting email provider to send.
 * If current selected one fails, randomly select another one from the remaining.
 * Once all providers are exhausted, error response is returned.
 */
class EmailSendingRandomProviderStrategy implements IEmailSendingStrategy {

	private static final Logger LOGGER = Logger.getLogger(EmailSendingRandomProviderStrategy.class.getSimpleName());

	private static final List<IEmailProvider> PROVIDERS = Arrays.asList(
			EmailProviderAmazonSES.getInstance(),
			EmailProviderMandrill.getInstance(),
			EmailProviderMailGun.getInstance(),
			EmailProviderSendGrid.getInstance()
	);

	@Override
	public EmailResponse send(EmailRequest request) {
		boolean done = false;
		Set<IEmailProvider> usedProviders = new HashSet<IEmailProvider>();
		EmailResponse response = EmailResponse.OK;
		while (!done) {
			IEmailProvider provider = getRandom(usedProviders);
			if (provider == null) {
				LOGGER.log(Level.SEVERE, "Unable to send email by any provider.");
				response = new EmailResponse(EmailResponse.EmailState.InternalError,
						"Unable to send email with any provider.");
				break;
			}
			LOGGER.log(Level.INFO, "Provider " + provider.getName() + " is selected.");
			response = provider.send(request);
			if (EmailResponse.EmailState.OK == response.getStatus()) {
				LOGGER.log(Level.INFO, "Sent by provider " + provider.getName());
				done = true;
			} else {
				LOGGER.log(Level.WARNING, "Provider " + provider.getName() + " was tried but failed.");
				usedProviders.add(provider);
			}
		}
		return response;
	}
	/*
	 * Randomly select email provider from those not yet tried.
	 */
	private IEmailProvider getRandom(Set<IEmailProvider> usedProviders) {
		if (usedProviders.size() == PROVIDERS.size()) {
			return null;
		}
		List<IEmailProvider> remaining = new ArrayList<IEmailProvider>();
		for (IEmailProvider provider : PROVIDERS) {
			if (!usedProviders.contains(provider)) {
				remaining.add(provider);
			}
		}
		return remaining.get(new Random().nextInt(remaining.size()));
	}
}
