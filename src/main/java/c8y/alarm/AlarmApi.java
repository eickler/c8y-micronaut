package c8y.alarm;

import java.time.OffsetDateTime;
import java.util.Optional;

import c8y.alarm.Alarm.Severity;
import c8y.alarm.Alarm.Status;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;
//${cumulocity.url}
@Client("/alarm/alarms")
public interface AlarmApi {
	@Get(consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getAll();

	@Get(value = "{?source,status}", consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getForSourceStatus(Optional<String> source, Optional<Status> status);

	@Get(value = "dateFrom,dateTo{?source,status}", consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getForTimeSourceStatus(OffsetDateTime dateFrom, OffsetDateTime dateTo, Optional<String> source, Optional<Status> status);

	@Get(value = "resolved", consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getForResolved(Boolean resolved);

	@Get(value = "severity", consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getForSeverity(Severity severity);

	@Get(value = "source,withAssets,withDevices", consumes = "application/vnd.com.nsn.cumulocity.alarmCollection+json")
	Single<Alarms> getForSourceAssetsDevices(String source, boolean withAssets, boolean withDevices);

	@Get(value = "/{id}", consumes = "application/vnd.com.nsn.cumulocity.alarm+json")
	Single<Alarm> get(String id);

	@Post(produces = "application/vnd.com.nsn.cumulocity.alarm+json")
	Single<HttpResponse> create(Alarm alarm);

	@Put(value = "{/id}{?status}")
	Single<HttpResponse> update(Optional<String> id, Optional<Status> status, StatusUpdate update);
}
