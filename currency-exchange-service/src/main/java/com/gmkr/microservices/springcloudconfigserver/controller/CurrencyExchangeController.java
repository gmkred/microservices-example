package com.gmkr.microservices.springcloudconfigserver.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmkr.microservices.springcloudconfigserver.bean.CurrencyExchange;
import com.gmkr.microservices.springcloudconfigserver.repo.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private CurrencyExchangeRepository repository;
	@Autowired
	private Environment environment;
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ResponseEntity<CurrencyExchange> retrieveExchangeValue(@PathVariable("from") String from,
			@PathVariable("to") String to) {
//		INFO[currency-exchange,65f0231cd0b1cda826b59fdfa618bfe5,26b59fdfa618bfe5] 18052 --- [nio-8000-exec-1] c.g.m.s.c.CurrencyExchangeController     : retrieveExchangeValue called with USD to INR
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		CurrencyExchange ex1 = repository.findByFromAndTo(from, to);
		CurrencyExchange ce = new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50));
		ce.setEnvironment(environment.getProperty("local.server.port"));
		ex1.setEnvironment(environment.getProperty("local.server.port"));
		return ResponseEntity.ok(ex1);

	}

}
