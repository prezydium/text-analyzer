package org.prezydium.textanalyzer.metrics;

import java.math.BigDecimal;
import java.util.Map;

public interface TextMetric {

    BigDecimal processText(String text, Map<String, BigDecimal> tempCache);

    String getMetricName();
}
