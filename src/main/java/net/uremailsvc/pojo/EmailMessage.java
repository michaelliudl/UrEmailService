package net.uremailsvc.pojo;

import net.uremailsvc.util.Util;

import java.util.List;

/**
 * Simple email message model that supports only text.
 * HTML and attachment support can be added.
 */
public class EmailMessage {
	private String from;
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	private String subject;
	private String content;

	public EmailMessage() {}

	public EmailMessage(String from,
	                    List<String> to,
	                    List<String> cc,
	                    List<String> bcc,
	                    String subject,
	                    String content) {
		this.from = from;
		this.to = Util.copyList(to);
		this.cc = Util.copyList(cc);
		this.bcc = Util.copyList(bcc);
		this.subject = subject;
		this.content = content;
	}

	public String getFrom() {return from;}

	public List<String> getTo() {return this.to;}
	public List<String> getCc() {return this.cc;}
	public List<String> getBcc() {return this.bcc;}
	public String getSubject() {return subject;}
	public String getContent() {return content;}

	@Override
	public String toString() {
		return "EmailMessage{" +
				"from='" + from + '\'' +
				", to=" + to +
				", cc=" + cc +
				", bcc=" + bcc +
				", subject='" + subject + '\'' +
				", content='" + content + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EmailMessage message = (EmailMessage) o;

		if (bcc != null ? !bcc.equals(message.bcc) : message.bcc != null) return false;
		if (cc != null ? !cc.equals(message.cc) : message.cc != null) return false;
		if (!content.equals(message.content)) return false;
		if (!from.equals(message.from)) return false;
		if (!subject.equals(message.subject)) return false;
		if (!to.equals(message.to)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = from.hashCode();
		result = 31 * result + to.hashCode();
		result = 31 * result + (cc != null ? cc.hashCode() : 0);
		result = 31 * result + (bcc != null ? bcc.hashCode() : 0);
		result = 31 * result + subject.hashCode();
		result = 31 * result + content.hashCode();
		return result;
	}
}
