package org.prezydium.textanalyzer.metrics;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AverageWordsLengthTest {

    @Test
    public void shouldReturnAverageLettersCount() {
        //given
        AverageWordsLength averageWordsLength = new AverageWordsLength();
        String text = "- Hello there! " +
                "- General Kenobi.";
        //when
        BigDecimal result = averageWordsLength.processText(text, Collections.emptyMap());
        //then
        assertEquals(new BigDecimal(5.75), result);
    }

    @Test
    public void shouldCountLetters() {
        //given
        AverageWordsLength averageWordsLength = new AverageWordsLength();
        String text = "3 + 55 = 58. Exactly";
        BigDecimal result = averageWordsLength.processText(text, Collections.emptyMap());
        //then
        assertEquals(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP), result);
    }
}