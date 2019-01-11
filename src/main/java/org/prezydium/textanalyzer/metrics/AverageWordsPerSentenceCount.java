package org.prezydium.textanalyzer.metrics;

import java.math.BigDecimal;
import java.util.Map;

public class AverageWordsPerSentenceCount implements TextMetric {

    public static String METRIC_NAME = "AverageWordsPerSentence ";

    public BigDecimal processText(String text, Map<String, BigDecimal> tempCache) {
        BigDecimal sentenceCount = tempCache.get(SentenceCount.METRIC_NAME);
        if (sentenceCount == null) {
            sentenceCount = new SentenceCount().processText(text, tempCache);
        }
        BigDecimal wordCount = tempCache.get(WordCount.METRIC_NAME);
        if (wordCount == null) {
            wordCount = new WordCount().processText(text, tempCache);
        }
        return wordCount.divide(sentenceCount, 2, BigDecimal.ROUND_HALF_UP);
    }

    public String getMetricName() {
        return METRIC_NAME;
    }
}
