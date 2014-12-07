package net.uremailsvc.service;

import junit.framework.Assert;
import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test for Amazon SES to send email.
 */
public class EmailSendingRandomProviderStrategyTest {
	@Test
	public void testSend() {
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com",
			new EmailMessage(
					"michael.liudl@gmail.com",
					Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
					Arrays.asList("doliu@ebay.com"),
					Arrays.asList("ldliang@hotmail.com"),
					"Test subject",
					"Test content"
			)
		);
		EmailResponse response = new EmailSendingRandomProviderStrategy().send(request);
		Assert.assertEquals(EmailResponse.EmailState.OK, response.getStatus());
	}
}
