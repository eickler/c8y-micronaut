package c8y.alarm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import c8y.JsonUtils;
import c8y.alarm.Alarm.Severity;
import c8y.alarm.Alarm.Status;
import c8y.inventory.ManagedObjectRef;

public class AlarmTest extends JsonUtils {
	public static final int NANOSECONDS = 1000 * 1000;

	private static final String REF_SERIALIZED = "{\"count\":433,\"creationTime\":\"2018-12-18T15:07:58.706Z\",\"time\":\"2018-12-24T06:52:48.963Z\",\"firstOccurrenceTime\":\"2018-12-18T15:06:54.918Z\",\"history\":{},\"id\":\"882693256\",\"self\":\"https://demos.cumulocity.com/alarm/alarms/882693256\",\"severity\":\"MAJOR\",\"source\":{\"id\":\"697612951\",\"name\":\"CEP Engine demos\",\"self\":\"https://demos.cumulocity.com/inventory/managedObjects/697612951\"},\"status\":\"ACTIVE\",\"text\":\"Something went wrong.\",\"type\":\"c8y_CepRuntimeException@system:CreateMeasurement\",\"c8y_ExceptionDetails\":{\"statementName\":\"system:CreateMeasurement\"}}";
	private static final String REF_ID = "882693256";
	private static final String REF_SELF = "https://demos.cumulocity.com/alarm/alarms/882693256";
	private static final OffsetDateTime REF_CREATIONTIME = OffsetDateTime.of(2018, 12, 18, 15, 7, 58, 706 * NANOSECONDS,
			ZoneOffset.UTC);
	private static final OffsetDateTime REF_TIME = OffsetDateTime.of(2018, 12, 24, 06, 52, 48, 963 * NANOSECONDS, ZoneOffset.UTC);
	private static final OffsetDateTime REF_FIRSTOCCURRENCE = OffsetDateTime.of(2018, 12, 18, 15, 06, 54,
			918 * NANOSECONDS, ZoneOffset.UTC);
	private static final String REF_TYPE = "c8y_CepRuntimeException@system:CreateMeasurement";
	private static final String REF_TEXT = "Something went wrong.";
	private static final Status REF_STATUS = Status.ACTIVE;
	private static final Severity REF_SEVERITY = Severity.MAJOR;
	private static final Long REF_COUNT = 433L;
	
	private ManagedObjectRef source;
	private Map<String,Object> cepError = new HashMap<String,Object>();
	private Map<String,Object> fragments = new HashMap<String,Object>();
	
	@Before
	public void setup() throws MalformedURLException {
		this.source = new ManagedObjectRef("697612951", "CEP Engine demos",
				new URL("https://demos.cumulocity.com/inventory/managedObjects/697612951"));
		this.cepError.put("statementName", "system:CreateMeasurement");
		this.fragments.put("c8y_ExceptionDetails", this.cepError);
		this.fragments.put("history", new HashMap<String,Object>());
	}

	@Test
	public void serializeTimeStampsCorrectly() throws JsonProcessingException {
		String time = om.writeValueAsString(REF_TIME);
		assertEquals("\"2018-12-24T06:52:48.963Z\"", time);
	}
	
	@Test
	public void serializeCorrectly() throws IOException {
		Alarm alarm = new Alarm(REF_ID, new URL(REF_SELF), REF_CREATIONTIME, REF_TIME, REF_FIRSTOCCURRENCE, REF_TYPE, REF_TEXT,
				source, REF_STATUS, REF_SEVERITY, REF_COUNT);

		testReserialized(alarm, Alarm.class);
	}

	@Test
	public void serializePostCorrectly() throws IOException {
		Alarm alarm = new Alarm(REF_TYPE, source.getId(), REF_TIME, REF_TEXT, REF_SEVERITY);
		
		Alarm actual = testReserialized(alarm, Alarm.class);
		assertEquals(Status.ACTIVE, actual.getStatus());
	}

	@Test
	public void deserializeCorrectly() throws JsonParseException, JsonMappingException, IOException {
		Alarm alarm = om.readValue(REF_SERIALIZED, Alarm.class);

		assertEquals(REF_ID, alarm.getId());
		assertEquals(new URL(REF_SELF), alarm.getSelf());
		assertEquals(REF_CREATIONTIME, alarm.getCreationTime());
		assertEquals(REF_TIME, alarm.getTime());
		assertEquals(REF_FIRSTOCCURRENCE, alarm.getFirstOccurrenceTime());
		assertEquals(REF_TYPE, alarm.getType());
		assertEquals(REF_TEXT, alarm.getText());
		assertEquals(source, alarm.getSource());
		assertEquals(REF_STATUS, alarm.getStatus());
		assertEquals(REF_SEVERITY, alarm.getSeverity());
		assertEquals(REF_COUNT, alarm.getCount());

		Map<String, Object> fragments = alarm.getFragments();
		assertEquals(2, fragments.size());
		assertNotNull(fragments.get("c8y_ExceptionDetails"));
		assertNotNull(fragments.get("history"));
		assertTrue(fragments.get("c8y_ExceptionDetails") instanceof Map);

		Map<String, Object> fragment = (Map<String, Object>) fragments.get("c8y_ExceptionDetails");
		assertEquals("system:CreateMeasurement", fragment.get("statementName"));
	}
	
	@Test
	public void serializeCustom() throws JsonParseException, JsonMappingException, IOException {
		MyAlarm myAlarm = om.readValue(REF_SERIALIZED, MyAlarm.class);
		
		assertEquals("system:CreateMeasurement", myAlarm.getExceptionDetails().getStatementName());
	}
}
