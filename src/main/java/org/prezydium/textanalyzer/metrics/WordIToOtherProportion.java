package org.prezydium.textanalyzer.metrics;

import org.prezydium.textanalyzer.util.DividorUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordIToOtherProportion implements TextMetric {

    public final static String METRIC_NAME = "WorldIToOtherWords";

    private Pattern wordIPattern = Pattern.compile("I['\\s]");


    public BigDecimal processText(String text, Map<String, BigDecimal> tempCache) {
        Matcher wordIMatcher = wordIPattern.matcher(text);
        long count = 0L;
        while (wordIMatcher.find()) {
            count++;
        }
        BigDecimal wordCount = tempCache.get(WordCount.METRIC_NAME);
        if (wordCount == null){
           wordCount = new WordCount().processText(text, Collections.emptyMap());
        }
        return new BigDecimal(count).divide(wordCount, 3, RoundingMode.HALF_UP);
    }

    public String getMetricName() {
        return METRIC_NAME;
    }
}
