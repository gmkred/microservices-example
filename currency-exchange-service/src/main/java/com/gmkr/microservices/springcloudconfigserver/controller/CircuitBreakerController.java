package com.gmkr.microservices.springcloudconfigserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "fallBackResponse")
//	@CircuitBreaker(name = "default", fallbackMethod = "fallBackResponse")
	@RateLimiter(name = "default2")
	@Bulkhead(name = "default2")
	public String sampleApi() {
		logger.info("sample api call recieved");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
//				String.class);
//		return forEntity.getBody();
		return "sample api";
	}

	public String fallBackResponse(Exception exception) {
		return "fall back response";
	}
}
