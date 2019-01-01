package org.prezydium.textanalyzer.metrics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AverageWordsLength implements TextMetric {

    public static String METRIC_NAME = "AverageWordsLength";

    private final Pattern wordPattern = Pattern.compile("[\\p{L}0-9']+");

    public BigDecimal processText(String text, Map<String, BigDecimal> tempCache) {
        Matcher wordMatcher = wordPattern.matcher(text);
        List<Integer> listOfWordsLenghts = new ArrayList<>();
        while (wordMatcher.find()){
            String word = wordMatcher.group();
            listOfWordsLenghts.add(word.length());
        }
        double average = listOfWordsLenghts.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
        return new BigDecimal(average).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getMetricName() {
        return METRIC_NAME;
    }
}
