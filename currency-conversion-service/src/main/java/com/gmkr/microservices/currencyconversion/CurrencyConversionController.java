package com.gmkr.microservices.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyExchangeProxy proxy;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable("from") String from,
			@PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		CurrencyConversion conversion1 = forEntity.getBody();
		CurrencyConversion conversion = new CurrencyConversion(10001l, from, to, quantity,
				conversion1.getConversionMultiple(), quantity.multiply(conversion1.getConversionMultiple()),
				conversion1.getEnvironment());
		return conversion;
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable("from") String from,
			@PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {

		CurrencyConversion conversion1 = proxy.retrieveExchangeValue(from, to).getBody();
		CurrencyConversion conversion = new CurrencyConversion(10001l, from, to, quantity,
				conversion1.getConversionMultiple(), quantity.multiply(conversion1.getConversionMultiple()),
				conversion1.getEnvironment() + " feign");
		return conversion;
	}

}
