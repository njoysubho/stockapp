package org.project.stockapp.service;

import java.util.List;

import org.project.stockapp.dao.StockDao;
import org.project.stockapp.exception.DuplicateObjectException;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
	@Autowired
	private StockDao stockDao;

	public List<Stock> findAllStocks() {
		return stockDao.findAllStocks();
	}

	public Stock findStockById(int id) throws StockNotFoundException {
		return stockDao.findStockById(id);
	}

	public Stock createStock(Stock stock) throws DuplicateObjectException {
		return stockDao.createStock(stock);
	}

	public Stock updateStock(Stock stock) throws StockNotFoundException {
		return stockDao.updateStock(stock);
	}

	public void deleteStock(Stock stock) throws StockNotFoundException {
		stockDao.deleteStock(stock);
		
	}

}
