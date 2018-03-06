package org.project.stockapp.dao;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.project.stockapp.configuration.StockConfiguration;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.project.stockapp.util.StockRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StockConfiguration.class })
public class StockDaoTest {
	@MockBean
	private StockRegistry stockRegistry;
	@Autowired
	private StockDao stockDao;

	@Test
	public void testFindAllStock() {

		Map<Integer, Stock> stockMap = new HashMap<Integer, Stock>();
		stockMap.put(1,
				new Stock(1, "ABCCorp", 120, Currency.getInstance("USD")));
		stockMap.put(2, new Stock(2, "XYZLtd", 80, Currency.getInstance("USD")));
		stockMap.put(3,
				new Stock(3, "DEFLtd", 180, Currency.getInstance("USD")));
		stockMap.put(4,
				new Stock(2, "GHILtd", 800, Currency.getInstance("USD")));
		List<Stock> stockList = new ArrayList<Stock>(stockMap.values());
		Mockito.when(stockRegistry.getAllStocks()).thenReturn(stockList);
		Assert.assertEquals(stockMap.size(), stockDao.findAllStocks().size());

	}

	@Test
	public void testFindAllStockNull() {
		Map<Integer, Stock> stockMap = new HashMap<Integer, Stock>();
		List<Stock> stockList = new ArrayList<Stock>(stockMap.values());
		Mockito.when(stockRegistry.getAllStocks()).thenReturn(stockList);
		Assert.assertEquals(stockMap.size(), stockDao.findAllStocks().size());
	}

	@Test
	public void testGetStockById() throws StockNotFoundException {
		Stock testStock = new Stock(1, "test", 100, Currency.getInstance("USD"));
		Mockito.when(stockRegistry.getStockById(1)).thenReturn(testStock);
		Assert.assertEquals(testStock, stockDao.findStockById(1));
	}
	@Test
	public void testGetStockByIdNull() throws StockNotFoundException {
		Stock testStock = new Stock(1, "test", 100, Currency.getInstance("USD"));
		Mockito.when(stockRegistry.getStockById(1)).thenReturn(null);
		Assert.assertNull(stockDao.findStockById(1));
	}

}
