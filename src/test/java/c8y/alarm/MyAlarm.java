package c8y.alarm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import c8y.alarm.Alarm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MyAlarm extends Alarm {
	@JsonProperty("c8y_ExceptionDetails")
	private ExceptionDetails exceptionDetails;
	
	@Data
	public class ExceptionDetails {
		private String statementName;
	}
}
