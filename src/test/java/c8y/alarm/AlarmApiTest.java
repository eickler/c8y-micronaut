package c8y.alarm;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import c8y.alarm.Alarm;
import c8y.alarm.AlarmApi;
import c8y.alarm.Alarms;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;

public class AlarmApiTest {
	private static EmbeddedServer embeddedServer;
	private static ApplicationContext appContext;
	private static AlarmApi alarmApi;

	@BeforeClass
	public static void beforeClass() {
		embeddedServer = ApplicationContext.run(EmbeddedServer.class);
		appContext = embeddedServer.getApplicationContext();
		alarmApi = appContext.getBean(AlarmApi.class);
	}

	@AfterClass
	public static void afterClass() {
		embeddedServer.close();
	}
 
	@Test
	public void getAlarms() {
		Alarms alarms = alarmApi.getAll().blockingGet();
	}
	
	@Test
	public void getAlarm() {
		Alarm alarm = alarmApi.get("882693256").blockingGet();
		assertNotNull(alarm.getId());
		assertNotNull(alarm.getFirstOccurrenceTime());
		assertNotNull(alarm.getFragments().get("c8y_ExceptionDetails"));
	}
}