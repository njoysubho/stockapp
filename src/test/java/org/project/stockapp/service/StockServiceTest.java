package org.project.stockapp.service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.project.stockapp.configuration.StockConfiguration;
import org.project.stockapp.dao.StockDao;
import org.project.stockapp.exception.DuplicateObjectException;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={StockConfiguration.class})
public class StockServiceTest {
	@Autowired
	private StockService stockService;
	
	@MockBean
	private StockDao stockDao;
	
	@Test
	public void testFindAllStocks(){
		List<Stock> stockList=new ArrayList<>();
		stockList.add(new Stock(1,"ABC", 1, Currency.getInstance("USD")));
		stockList.add(new Stock(2, "DEF", 2, Currency.getInstance("USD")));
		Mockito.when(stockDao.findAllStocks()).thenReturn(stockList);
		Assert.assertEquals(stockList.size(), stockService.findAllStocks().size());
	}
	
	@Test
	public void testFindById() throws StockNotFoundException{
		Stock stock=new Stock(1,"ABC", 1, Currency.getInstance("USD"));
		
		Mockito.when(stockDao.findStockById(Mockito.anyInt())).thenReturn(stock);
		Assert.assertEquals(stock, stockService.findStockById(1));
	}
	
	@Test
	public void testCreate() throws StockNotFoundException, DuplicateObjectException{
		Stock stock=new Stock(1,"ABC", 1, Currency.getInstance("USD"));
		Mockito.when(stockDao.createStock(Mockito.any(Stock.class))).thenReturn(stock);
		Assert.assertEquals(stock, stockService.createStock(stock));
	}

}
