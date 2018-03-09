package org.project.stockapp.configuration;

import org.project.stockapp.dao.StockDaoImpl;
import org.project.stockapp.service.StockServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StockConfiguration {

	@Bean
	public StockDaoImpl stockDaoImpl(){
		return new StockDaoImpl();
	}
	@Bean
	public StockServiceImpl stockServiceImpl(){
		return new StockServiceImpl();
	}
}

