package org.project.stockapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class DuplicateObjectException extends Exception{

	public DuplicateObjectException(String msg){
		super(msg);
	}
}
