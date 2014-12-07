
package net.uremailsvc;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import net.uremailsvc.service.EmailServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS resource for email service using Jersey
 */
@Path("/")
public class EmailServiceResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET
    @Path("/getEmailLog")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmailLog() {
        return "Hi there!";
    }

	/**
	 * Operation to send email using HTTP POST
	 * Interface is JSON.
	 *
	 * @param request email request with from address, list of to/cc/bcc addresses, subject and content
	 * @return result object indicating success or failure (with messages)
	 */
	@POST
	@Path("/sendEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmailResponse sendEmail(EmailRequest request) {
		return EmailServiceFactory.getService().send(request);
	}
}
