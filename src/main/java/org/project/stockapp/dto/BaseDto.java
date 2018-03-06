package org.project.stockapp.dto;

import java.util.ArrayList;
import java.util.List;

public class BaseDto {

	List<LinkDto> links=new ArrayList<LinkDto>();

	public List<LinkDto> getLinks() {
		return links;
	}

	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}
	
}
