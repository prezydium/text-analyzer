package org.prezydium.textanalyzer.metrics;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AverageWordsPerSentenceCountTest {

    @Test
    public void shouldReturn5() {
        //given
        Map<String, BigDecimal> pastResults = new HashMap<String, BigDecimal>() {{
            put(WordCount.METRIC_NAME, new BigDecimal(15));
            put(SentenceCount.METRIC_NAME, new BigDecimal(3));
        }};
        AverageWordsPerSentenceCount averageWordsPerSentenceCount = new AverageWordsPerSentenceCount();
        //when
        BigDecimal result = averageWordsPerSentenceCount.processText("a a a a." +
                "a a a a a a a a." +
                "a a a.", pastResults);
        //then
        assertEquals(new BigDecimal(5).setScale(2, BigDecimal.ROUND_HALF_UP), result);
    }

    @Test
    public void shouldReturnResultWithEmptyCache() {
        //given
        Map<String, BigDecimal> pastResults = Collections.emptyMap();
        AverageWordsPerSentenceCount averageWordsPerSentenceCount = new AverageWordsPerSentenceCount();
        //when
        BigDecimal result = averageWordsPerSentenceCount.processText("a a a a." +
                "a a a a a a a." +
                "a a a. a. b.", pastResults);
        //then
        assertEquals(new BigDecimal(3.20).setScale(2, BigDecimal.ROUND_HALF_UP), result);
    }
}