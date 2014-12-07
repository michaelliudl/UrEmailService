package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test email address validator.
 */
public class EmailAddressValidatorTest {
	@Test
	public void testValidate() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.OK == new EmailAddressValidator().validate(request).getStatus());
	}
}
