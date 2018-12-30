package c8y.inventory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import c8y.JsonUtils;
import c8y.inventory.ManagedObjectRef;

public class ManagedObjectRefTest extends JsonUtils {
	private static final String REF_SERIALIZED = "{\"id\":\"697612951\",\"name\":\"CEP Engine demos\",\"self\":\"https://demos.cumulocity.com/inventory/managedObjects/697612951\"}";
	private static final String REF_ID = "697612951";
	private static final String REF_NAME = "CEP Engine demos";
	private static final String REF_SELF = "https://demos.cumulocity.com/inventory/managedObjects/697612951";

	@Test
	public void deserializeCorrectly() throws JsonParseException, JsonMappingException, IOException {
		ManagedObjectRef actual = om.readValue(REF_SERIALIZED, ManagedObjectRef.class);

		assertEquals(REF_ID, actual.getId());
		assertEquals(REF_NAME, actual.getName());
		assertEquals(new URL(REF_SELF), actual.getSelf());
	}

	@Test
	public void serializeCorrectly() throws IOException {
		ManagedObjectRef moref = new ManagedObjectRef(REF_ID, REF_NAME, new URL(REF_SELF));

		testReserialized(moref, ManagedObjectRef.class);
	}

	@Test
	public void serializeOnlyIdForPosts() throws IOException {
		ManagedObjectRef moref = new ManagedObjectRef(REF_ID);

		testReserialized(moref, ManagedObjectRef.class);
	}

}
