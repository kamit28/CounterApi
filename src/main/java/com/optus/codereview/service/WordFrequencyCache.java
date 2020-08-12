package com.optus.codereview.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class WordFrequencyCache {

	private Resource resourceFile;

	private static class InstanceHolder {
		public static WordFrequencyCache INSTANCE = new WordFrequencyCache();
	}

	private final Map<String, Long> cache;

	private WordFrequencyCache() {
		cache = new LinkedHashMap<>();
		resourceFile = new ClassPathResource("data/sample_text.txt");
	}

	public static WordFrequencyCache instance() {
		return InstanceHolder.INSTANCE;
	}

	public Long get(final String key) {
		return cache.get(key.toLowerCase());
	}
	
	public Stream<Entry<String, Long>> getStreamOfN(Integer n) {
		return cache.entrySet().stream().limit(n);
	}

	@PostConstruct
	private void init() throws Exception {
		Map<String, Long> wordFrequencies = Files.lines(Paths.get(resourceFile.getURI()))
				.flatMap(Pattern.compile("\\s+")::splitAsStream)
				.map(w -> w.replaceAll("\\p{Punct}", ""))
				.collect(Collectors.toConcurrentMap(word -> word.toLowerCase(), word -> 1L, Long::sum))
				.entrySet()
				.stream()
				.sorted(Entry.<String, Long>comparingByValue().reversed())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		if (null != wordFrequencies) {
			if (cache != null || !cache.isEmpty()) {
				cache.clear();
			}
			cache.putAll(wordFrequencies);
		} else {
			throw new Exception("Could not find any coloumn mappings from database");
		}
	}
}
