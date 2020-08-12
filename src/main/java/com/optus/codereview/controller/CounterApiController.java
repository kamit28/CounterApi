package com.optus.codereview.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.optus.codereview.model.SearchRequest;
import com.optus.codereview.model.SearchResponse;
import com.optus.codereview.service.CounterService;

@RestController
@RequestMapping("/")
public class CounterApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CounterApiController.class);

	private CounterService counterService;

	@Autowired
	public CounterApiController(CounterService counterService) {
		this.counterService = counterService;
	}

	@PostMapping(value = "/search", consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<SearchResponse> search(
			@Valid @RequestBody(required = true) SearchRequest searchRequest) throws Exception {
		LOGGER.info("Request received: " + searchRequest.toString());

		Map<String, Long> result = counterService.search(searchRequest.getSearchText());
		SearchResponse response = new SearchResponse(result);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/top/{num}")
	public @ResponseBody ResponseEntity<String> top(@PathVariable @Validated Integer num) throws Exception {
		LOGGER.info("Request received: " + num);

		String result = counterService.getTopNWords(num);
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Content-Type", "text/csv;charset=utf-8");

		return ResponseEntity.ok().headers(responseHeaders).body(result);
	}
}
