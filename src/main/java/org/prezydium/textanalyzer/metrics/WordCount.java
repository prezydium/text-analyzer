package org.prezydium.textanalyzer.metrics;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCount implements TextMetric {

    public static String METRIC_NAME = "WordCount";

    private Pattern wordPattern = Pattern.compile("\\S+");

    public BigDecimal processText(String text, Map<String, BigDecimal> tempCache) {
        Matcher wordMatcher = wordPattern.matcher(text);
        long count = 0L;
        while (wordMatcher.find()){
            count++;
        }
        return new BigDecimal(count);
    }

    public String getMetricName() {
        return METRIC_NAME;
    }
}
