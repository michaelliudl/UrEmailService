# Email Service
A Restful service built based on JAX-RS specification using Jersey.

The service takes email messages in JSON format and randomly select one from the four service providers to send. If one service provider fails, another one will be randomly selected.

## Interface
Email fields implemented: from address, list of to/cc/bcc addresses, subject and content(text). Further enhancement will be HTML format and attachment, etc.

Service path:

```
/emailservice/sendEmail
```

Sample request:

```
{
  "emailSvcUser" : "abc@gmail.com",
  "message" : {
    "from" : "abc@gmail.com",
    "to" : [ "xzy@yahoo.com", "defg@hotmail.com" ],
    "cc" : [ "efgh@live.com", "hijk@hotmail.com" ],
    "bcc" : [ "mnop@gmail.com", "pqsrt@yahoo.com" ],
    "subject" : "Test subject",
    "content" : "Test content"
  }
}
```
Sample response:

```
{
    "status": "OK",
    "error": ""
}
```

## Design
* ```EmailServiceResource``` is the Jersey resource for service interface.
* ```IEmailService``` implementation object is created by ```EmailServiceFactory``` for processing.
* Each service call runs through several ```IEmailServiceStage```s. Now we have three stages: validation, sending, post-sending (future enhancement).
* The sending stage uses random provider selection strategy to send emails.
* The strategy randomly choose one provider from four options. If this one fails, it will randomly choose another one from the remaining three providers. If all options fail, the service call is considered failure.

## Build and deployment

Code repo: ```https://github.com/michaelliudl/UrEmailService```

Deployed to heroku: ```https://limitless-hollows-7477.herokuapp.com/emailservice/sendEmail```

Run on local:

* Install heroku toolbelt: ```https://toolbelt.heroku.com```
* Heroku webapp-runner is already configured as maven plugin: ```https://github.com/jsimone/webapp-runner```
* Clone source and run ```mvn package```
* Run ```java -jar target/dependency/webapp-runner.jar target/UrEmailService.war```

Run ```mvn test``` for JUnit cases

## Future enhancements
* Support more email formats and attachment.
* Support authentication and authorization of service user (sender).
* Support structured log view of service calls, e.g. success / failure, email provider used, etc.