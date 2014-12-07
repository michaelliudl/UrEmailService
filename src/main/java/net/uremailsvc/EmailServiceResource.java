
package net.uremailsvc;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;
import net.uremailsvc.service.EmailServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/** Example resource class hosted at the URI path "/myresource"
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

	@POST
	@Path("/sendEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmailResponse sendEmail(EmailRequest request) {
		System.out.println(request.toString());
		System.out.println(Thread.currentThread().getName());
		EmailResponse response = EmailServiceFactory.getService().send(request);
		return new EmailResponse();
	}
}
