package fr.slixe.benchmarks.http;

import fr.litarvan.paladin.http.routing.RequestException;

public class RateLimitException extends RequestException {

	public RateLimitException(String message) {
		super(message);
	}

}
