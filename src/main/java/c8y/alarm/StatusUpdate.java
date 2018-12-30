package c8y.alarm;

import c8y.alarm.Alarm.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusUpdate {
	private Status status;
}
