package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailMessage;
import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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

	@Test
	public void testValidateNoFrom() {
		EmailMessage message = new EmailMessage(
				"",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateInvalidFrom() {
		EmailMessage message = new EmailMessage(
				"michael",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateNoTo() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Collections.<String>emptyList(),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateInvalidTo() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu", "ldliang@hotmail.com"),
				Collections.<String>emptyList(),
				Collections.<String>emptyList(),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateInvalidCc() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com", "ldliang@hot"),
				Collections.<String>emptyList(),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}

	@Test
	public void testValidateInvalidBcc() {
		EmailMessage message = new EmailMessage(
				"michael.liudl@gmail.com",
				Arrays.asList("doliu@ebay.com", "ldliang@hotmail.com"),
				Arrays.asList("doliu@ebay.com"),
				Arrays.asList("ldliang@"),
				"Test subject",
				"Test content"
		);
		EmailRequest request = new EmailRequest("michael.liudl@gmail.com", message);
		Assert.assertTrue(EmailResponse.EmailState.Invalid == new EmailAddressValidator().validate(request).getStatus());
	}
}
