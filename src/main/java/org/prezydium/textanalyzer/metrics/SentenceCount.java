package org.prezydium.textanalyzer.metrics;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceCount implements TextMetric {

    public static String METRIC_NAME = "SentenceCount";

    private Pattern endSentencePattern = Pattern.compile("[.!;]+");

    public BigDecimal processText(String text, Map<String, BigDecimal> tempCache) {
        long count = 0L;
        Matcher endOfSentenceMatcher = endSentencePattern.matcher(text);
        while (endOfSentenceMatcher.find()){
            count++;
        }
        return new BigDecimal(count);
    }

    public String getMetricName() {
        return METRIC_NAME;
    }

}
