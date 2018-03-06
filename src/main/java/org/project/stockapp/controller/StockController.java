package org.project.stockapp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.project.stockapp.converter.StockConverter;
import org.project.stockapp.dto.StockDto;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.project.stockapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
	@Autowired
	private StockService stockService;
	@Autowired
	private StockConverter stockConverter;

	@RequestMapping(method = RequestMethod.GET, value = "/api/stocks", produces = "application/json")
	public @ResponseBody ResponseEntity<List<StockDto>> listAllStock() {
		List<Stock> stockList = stockService.findAllStocks();
		List<StockDto> stockDtoList = null;
		if (stockList.isEmpty()) {
			stockDtoList = Collections.emptyList();
		} else {
			stockDtoList = new ArrayList<StockDto>();
			for (Stock stock : stockList) {
				stockDtoList.add(stockConverter.toDtO(stock));
			}
		}
		ResponseEntity<List<StockDto>> responseEntity = new ResponseEntity<List<StockDto>>(
				stockDtoList, HttpStatus.OK);
		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/stocks/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<StockDto> findStockById(
			@PathVariable int id) throws StockNotFoundException {
		Stock stock = stockService.findStockById(id);
		StockDto stockDto = stockConverter.toDtO(stock);
		ResponseEntity<StockDto> responseEntity = new ResponseEntity<StockDto>(
				stockDto, HttpStatus.OK);
		return responseEntity;

	}

}
