package c8y;

import static org.junit.Assert.*;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	protected ObjectMapper om = new ObjectMapper();

	protected JsonUtils() {
		om.findAndRegisterModules();
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);		
	}
	
	/**
	 * Serialize, the deserialize and test for equality. String comparison does not
	 * reliably work, since the order of properties in JSON is not guaranteed.
	 */
	protected <T> T testReserialized(Object expected, Class<T> clazz)
			throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
		String serialized = om.writeValueAsString(expected);
		T actual = om.readValue(serialized, clazz);

		assertEquals(expected, actual);
		return actual;
	}

}
