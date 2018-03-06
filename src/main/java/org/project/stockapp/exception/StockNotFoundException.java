package org.project.stockapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class StockNotFoundException extends Exception{

	public StockNotFoundException(String msg){
		super(msg);
	}
}
