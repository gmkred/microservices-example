package com.gmkr.microservices.springcloudconfigserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmkr.microservices.springcloudconfigserver.bean.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
	CurrencyExchange findByFromAndTo(String from, String to);


}
 