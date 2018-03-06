package org.project.stockapp.converter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.project.stockapp.dto.LinkDto;
import org.project.stockapp.dto.StockDto;
import org.project.stockapp.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockConverter {

	public Stock toDO(StockDto stockDto) {
        
		Stock stock = new Stock(stockDto.getId(), stockDto.getName(),
				stockDto.getPrice(), Currency.getInstance(stockDto.getCurrency()));
		return stock;
	}
	
     public StockDto toDtO(Stock stock) {
        
		StockDto stockDto=new StockDto();
		stockDto.setId(stock.getId());
		stockDto.setCurrency(stock.getCurrency().getCurrencyCode());
		stockDto.setPrice(stock.getPrice());
		stockDto.setName(stock.getName());
		stockDto.setTimeStamp(""+stock.getTimeStamp());
		stockDto.setLinks(createLinks(stockDto.getId()));
		return stockDto;
	}

	private List<LinkDto> createLinks(int id) {
		List<LinkDto> links=new ArrayList<LinkDto>();
		LinkDto self=new LinkDto();
		self.setName("GET");
		self.setUri("/api/stocks/"+id);
		links.add(self);
		LinkDto update=new LinkDto();
		update.setName("PUT");
		update.setUri("/api/stocks/"+id);
		links.add(update);
		LinkDto delete=new LinkDto();
		delete.setName("DELETE");
		delete.setUri("/api/stocks/"+id);
		links.add(delete);
		return links;
		
	}
     
}
