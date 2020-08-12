package com.optus.codereview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

	private WordFrequencyCache cache;

	@Autowired
	public CounterService(WordFrequencyCache cache) {
		this.cache = cache;
	}

	public Map<String, Long> search(List<String> words) {
		Map<String, Long> result = new HashMap<>(words.size());
		for (String word : words) {
			Long count = cache.get(word);
			if (count != null) {
				result.put(word, count);
			} else {
				result.put(word, 0L);
			}
		}

		return result;
	}

	public String getTopNWords(Integer count) {
		return cache.getStreamOfN(count)
				.map(e -> new String(e.getKey() + "|" + e.getValue()))
				.collect(Collectors.joining("\n"));
	}
}
