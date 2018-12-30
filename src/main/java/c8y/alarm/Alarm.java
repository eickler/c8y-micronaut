package c8y.alarm;

import java.net.URL;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import c8y.Document;
import c8y.inventory.ManagedObjectRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Alarm extends Document {
	public enum Status { ACTIVE, ACKNOWLEDGED, CLEARED };
	public enum Severity { CRITICAL, MAJOR, MINOR, WARNING };
	
	private String id;
	private URL self;
	private OffsetDateTime creationTime;
	private OffsetDateTime time;
	private OffsetDateTime firstOccurrenceTime;
	private String type;
	private String text;
	private ManagedObjectRef source;
	private Status status;
	private Severity severity;
	private Long count;

	/**
	 * Convenience method for creating a new, active alarm. 
	 */
	public Alarm(String type, String source, OffsetDateTime time, String text, Severity severity) {
		super();
		this.type = type;
		this.source = new ManagedObjectRef(source);
		this.time = time;
		this.text = text;
		this.severity = severity;
		this.status = Status.ACTIVE;
	}
}
