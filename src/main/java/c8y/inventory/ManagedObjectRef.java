package c8y.inventory;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ManagedObjectRef {
	private String id;
	private String name;
	private URL self;
	
	public ManagedObjectRef(String id) {
		this.id = id;
	}
}
