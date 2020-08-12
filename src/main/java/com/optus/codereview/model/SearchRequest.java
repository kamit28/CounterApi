package com.optus.codereview.model;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class SearchRequest {

	@Size(min = 1, message = "Word Search should have at least one word in the list.")
	private List<String> searchText;
}
