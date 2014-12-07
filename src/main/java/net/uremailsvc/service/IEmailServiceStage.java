package net.uremailsvc.service;

import net.uremailsvc.pojo.EmailRequest;
import net.uremailsvc.pojo.EmailResponse;

/**
 * Created by doliu on 12/6/14.
 */
interface IEmailServiceStage {
	EmailResponse execStage(EmailRequest request);
}
