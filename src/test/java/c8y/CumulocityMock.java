package c8y;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class CumulocityMock {
	
	private String allAlarms = getFile("/allAlarms.json");
	private String oneAlarm = getFile("/oneAlarm.json");
	
    @Get(value = "/alarm/alarms", produces = MediaType.APPLICATION_JSON)
    public String getAllAlarms() {
    	return allAlarms;
    }

    @Get(value = "/alarm/alarms/{id}", produces = MediaType.APPLICATION_JSON)
    public String getAllAlarms(HttpRequest<?> request, String id) {
    	return oneAlarm;
    }
    
    private static String getFile(String filename) {
    	try {
			return new String(Files.readAllBytes(Paths.get(CumulocityMock.class.getResource(filename).toURI())));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
    }
}
