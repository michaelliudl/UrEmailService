package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test email content validator.
 */
public class EmailContentValidatorTest {

	@Test
	public void testValidateNoSubject() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com"),
				Arrays.asList("ldliang@hotmail.com"),
				"",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailContentValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateNoContent() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com"),
				Arrays.asList("ldliang@hotmail.com"),
				"Test subject",
				""
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailContentValidator().validate(request).getStatus());
	}
}
