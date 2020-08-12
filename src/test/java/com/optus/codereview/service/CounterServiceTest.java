package com.optus.codereview.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CounterServiceTest {

	WordFrequencyCache cacheMock;

	private CounterService service;

	@Before
	public void setUp() {
		cacheMock = mock(WordFrequencyCache.class);
		service = new CounterService(cacheMock);
		when(cacheMock.get("word1")).thenReturn(100L);
		when(cacheMock.get("word2")).thenReturn(10L);
		when(cacheMock.get("word3")).thenReturn(35L);
		when(cacheMock.get("word4")).thenReturn(29L);
		Map<String, Long> cache = new LinkedHashMap<>();
		cache.put("word1", 100l);
		cache.put("word2", 90l);
		cache.put("word3", 86l);
		cache.put("word4", 81l);
		cache.put("word5", 80l);
		cache.put("word6", 80l);
		cache.put("word7", 72l);
		cache.put("word8", 59l);
		cache.put("word9", 45l);
		cache.put("word10", 42l);
		cache.put("word11", 28l);
		cache.put("word12", 6l);
		when(cacheMock.getStreamOfN(5)).thenReturn(cache.entrySet().stream().limit(5));
		when(cacheMock.getStreamOfN(0)).thenReturn(cache.entrySet().stream().limit(0));
	}

	@Test
	public void search_success() {
		Map<String, Long> result = service.search(List.of("word1", "word2", "word5"));

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.get("word1") == 100L);
		assertTrue(result.get("word2") == 10L);
		assertTrue(result.get("word5") == 0L);
	}

	@Test
	public void getTopNWords_input_5() {
		String result = service.getTopNWords(5);

		assertNotNull(result);
		assertTrue(result.equals("word1|100\nword2|90\nword3|86\nword4|81\nword5|80"));
	}

	@Test
	public void getTopNWords_input_0() {
		String result = service.getTopNWords(0);

		assertNotNull(result);
		assertTrue(result.equals(""));
	}
}
