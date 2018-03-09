package org.project.stockapp.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.project.stockapp.converter.StockConverter;
import org.project.stockapp.dto.StockDto;
import org.project.stockapp.exception.DuplicateObjectException;
import org.project.stockapp.exception.StockNotFoundException;
import org.project.stockapp.model.Stock;
import org.project.stockapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
			@PathVariable int id) throws StockNotFoundException,
			NoSuchAlgorithmException {
		Stock stock = stockService.findStockById(id);
		StockDto stockDto = stockConverter.toDtO(stock);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Etag", createEtag(stockDto.getTimeStamp()));
		ResponseEntity<StockDto> responseEntity = new ResponseEntity<StockDto>(
				stockDto, headers, HttpStatus.OK);
		return responseEntity;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/stocks", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<StockDto> createStock(
			@RequestBody StockDto stock) throws DuplicateObjectException {
		Stock stockDo = stockConverter.toDO(stock);
		Stock createdStock = stockService.createStock(stockDo);
		StockDto createdStockDto = stockConverter.toDtO(createdStock);
		HttpHeaders headers = new HttpHeaders();
		headers.add("location", "/api/stocks/" + createdStock.getId());
		ResponseEntity<StockDto> response = new ResponseEntity<StockDto>(
				createdStockDto, headers, HttpStatus.CREATED);
		return response;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/stocks/{id}", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<StockDto> updateStock(
			@RequestHeader HttpHeaders headers, @PathVariable int id,
			@RequestBody StockDto stockDto) throws DuplicateObjectException,
			StockNotFoundException, NoSuchAlgorithmException {
		String eTag = headers.getETag();
		Stock retrievedStock = stockService.findStockById(id);
		if (retrievedStock == null) {
			throw new StockNotFoundException("Stock with id " + id
					+ " not found");
		}
		if (eTag == null
				|| !eTag.equals(createEtag("" + retrievedStock.getTimeStamp()))) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}

		Stock stock = stockConverter.toDO(stockDto);
		retrievedStock.setCurrency(stock.getCurrency());
		retrievedStock.setName(stock.getName());
		retrievedStock.setPrice(stock.getPrice());
		Stock updatedStock = stockService.updateStock(retrievedStock);
		StockDto updatedStockDto = stockConverter.toDtO(updatedStock);
		ResponseEntity<StockDto> response = new ResponseEntity<StockDto>(
				updatedStockDto, HttpStatus.OK);
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/stocks/{id}")
	public @ResponseBody ResponseEntity<?> deleteStock(@PathVariable int id)
			throws DuplicateObjectException, StockNotFoundException {
		Stock retrievedStock = stockService.findStockById(id);
		if (retrievedStock == null) {
			throw new StockNotFoundException("Stock with id " + id
					+ " not found");
		}
		stockService.deleteStock(retrievedStock);
		ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return response;
	}

	private String createEtag(String key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update(key.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
}
