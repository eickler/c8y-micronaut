package c8y;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Document {

	private Map<String, Object> fragments = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getFragments() {
		return fragments;
	}

	@JsonAnySetter
	public void setFragments(String name, Object value) {
		this.fragments.put(name, value);
	}
}
