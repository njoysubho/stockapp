package org.project.stockapp.util;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.project.stockapp.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockRegistry {
	private Map<Integer,Stock> stockMap=new HashMap<Integer,Stock>();
	
	@PostConstruct
	public void init(){
		stockMap.put(1,new Stock(1,"ABCCorp",120,Currency.getInstance("USD")));
		stockMap.put(2,new Stock(2,"XYZLtd", 80,Currency.getInstance("USD")));
		stockMap.put(3,new Stock(3,"DEFLtd", 180,Currency.getInstance("USD")));
		stockMap.put(4,new Stock(4,"GHILtd", 800,Currency.getInstance("USD")));		
	}

	public Map<Integer, Stock> getStockMap() {
		return stockMap;
	}

	public void setStockMap(Map<Integer, Stock> stockMap) {
		this.stockMap = stockMap;
	}
	
	public List<Stock> getAllStocks(){
		return new ArrayList<Stock>(stockMap.values());
	}
	public Stock getStockById(int id){
		return stockMap.get(id);
	}

}
