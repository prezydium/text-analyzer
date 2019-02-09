package org.prezydium.textanalyzer.metrics;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import static org.junit.Assert.*;

public class WordIToOtherProportionTest {

    private WordIToOtherProportion wordIToOtherProportion;

    @Before
    public void setUp(){
        wordIToOtherProportion = new WordIToOtherProportion();
    }

    @Test
    public void shouldReturn0_182(){
        //given
        String text = "I'm good don't worry. I will do something. Random: DfsfIfsfsIIIfsfs, ok?";
        BigDecimal expected = new BigDecimal(0.182).setScale(3, RoundingMode.HALF_UP);
        //when
        BigDecimal result = wordIToOtherProportion.processText(text, Collections.emptyMap());
        //then
        assertEquals(expected, result);
    }

}