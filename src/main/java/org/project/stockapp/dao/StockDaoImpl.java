package org.project.stockapp.dao;

import java.util.List;

import org.project.stockapp.exception.DuplicateObjectException;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.project.stockapp.util.StockRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockDaoImpl implements StockDao {

	@Autowired
	private StockRegistry stockRegistry;

	public List<Stock> findAllStocks() {
		List<Stock> allStocks = stockRegistry.getAllStocks();
		return allStocks;
	}

	public Stock findStockById(int id) throws StockNotFoundException {

		Stock stock=stockRegistry.getStockById(id);
		if(stock==null){
			throw new StockNotFoundException("Stock with id "
					+ id + " not found");
		}
		return stock;
		
	}

	public Stock createStock(Stock newStock) throws DuplicateObjectException {
		if (stockRegistry.getStockById(newStock.getId()) != null) {
			throw new DuplicateObjectException("Stock with id "
					+ newStock.getId() + " already Exists");
		}
		stockRegistry.getStockMap().put(newStock.getId(), newStock);
		return stockRegistry.getStockById(newStock.getId());
	}

	public Stock updateStock(Stock stock) throws StockNotFoundException {
		if (stockRegistry.getStockById(stock.getId()) == null) {
			throw new StockNotFoundException("Stock with id "
					+ stock.getId() + " not found");
		}
		Stock persistedStock = new Stock(stock.getId(), stock.getName(),
				stock.getPrice(), stock.getCurrency());
        stockRegistry.getStockMap().put(persistedStock.getId(), persistedStock);
        return stockRegistry.getStockById(persistedStock.getId());
	}

	public void deleteStock(Stock stock) throws StockNotFoundException{
		if (stockRegistry.getStockById(stock.getId()) == null) {
			throw new StockNotFoundException("Stock with id "
					+ stock.getId() + " not found");
		}
		stockRegistry.getStockMap().remove(stock.getId());

	}

	public StockRegistry getStockRegistry() {
		return stockRegistry;
	}

	public void setStockRegistry(StockRegistry stockRegistry) {
		this.stockRegistry = stockRegistry;
	}

}
