package c8y.utils;

import java.net.URI;

import org.reactivestreams.Publisher;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import io.micronaut.http.uri.UriBuilder;

public class PageSizeFilter implements HttpClientFilter {
	private final String pageSize;

	public PageSizeFilter(@Value("${cumulocity.pagesize}") String pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
		return chain.proceed(addPageSizeIfCollection(request));
	}

	private MutableHttpRequest<?> addPageSizeIfCollection(MutableHttpRequest<?> request) {
		if (request.getHeaders().get("Accept").contains("Collection")) {
			URI uri = UriBuilder.of(request.getUri()).queryParam("pageSize", pageSize).build();
			request = request.uri(uri);
		}
		return request;
	}
}
