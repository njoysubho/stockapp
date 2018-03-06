package org.project.stockapp.dao;

import java.util.List;

import org.project.stockapp.exception.DuplicateObjectException;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;

public interface StockDao {

	public List<Stock> findAllStocks();
	public Stock findStockById(int id) throws StockNotFoundException;
	public Stock createStock(Stock stock) throws DuplicateObjectException;
	public Stock updateStock(Stock stock) throws StockNotFoundException;
	public void deleteStock(Stock stock) throws StockNotFoundException;
}
