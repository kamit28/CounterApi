package com.optus.codereview.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.optus.codereview.model.SearchRequest;
import com.optus.codereview.model.SearchResponse;
import com.optus.codereview.service.CounterService;

public class CounterApiControllerTest {

	private CounterService service;

	private CounterApiController controller;

	@Before
	public void setUp() {
		service = mock(CounterService.class);
		controller = new CounterApiController(service);
	}

	@Test
	public void searchTest_success() throws Exception {
		List<String> searchText = List.of("word1", "word2");
		SearchRequest request = new SearchRequest();
		request.setSearchText(searchText);
		when(service.search(searchText)).thenReturn(Map.of("word1", 100L, "word2", 0L));

		ResponseEntity<SearchResponse> response = controller.search(request);

		assertNotNull(response);
		assertThat(response.getStatusCode().equals(HttpStatus.OK));
		assertThat(response.getBody() instanceof SearchResponse);
		assertEquals(2, response.getBody().getCounts().size());
		long count = response.getBody().getCounts().get("word1");
		assertEquals(100L, count);
		count = response.getBody().getCounts().get("word2");
		assertEquals(0L, count);
	}
}
