package com.optus.codereview.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

public class WordFrequencyCacheTest {
	
	private WordFrequencyCache cache;
	
	@Before
	public void setUp() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		cache = WordFrequencyCache.instance();
		ReflectionTestUtils.setField(cache, "resourceFile", new ClassPathResource("data/sample_text.txt"));
		Method postConstruct =  WordFrequencyCache.class.getDeclaredMethod("init"); // methodName,parameters
	    postConstruct.setAccessible(true);
	    postConstruct.invoke(cache);
		//cache.resourceFile = new ClassPathResource("classpath:data/sample_text.txt");
	}
	
	@Test
	public void getTest() {
		Long result = cache.get("come");
		assertEquals(1L, result.longValue());
		result = cache.get("and");
		assertEquals(2L, result.longValue());
		result = cache.get("XXXX");
		assertNull(result);
	}

	@Test
	public void getStreamOfNTest() {
		Stream<Entry<String, Long>> stream = cache.getStreamOfN(5);
		
		assertNotNull(stream);
		assertTrue(stream.count() == 5);
	}
}
