package c8y.utils;

import org.reactivestreams.Publisher;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;

public class BaiscAuthFilter implements HttpClientFilter {
	private final String username;
	private final String password;

	public BaiscAuthFilter(@Value("${cumulocity.username}") String username,
			@Value("${cumulocity.password}") String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
		return chain.proceed(request.basicAuth(username, password));
	}
}
