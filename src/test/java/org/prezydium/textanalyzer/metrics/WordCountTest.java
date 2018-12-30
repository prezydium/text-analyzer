package org.prezydium.textanalyzer.metrics;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.*;

public class WordCountTest {

    @Test
    public void shouldFind10Words(){
        //given
        WordCount wordCount = new WordCount();
        String text = "This is a serious test. Find 10 words! I'm serious";
        //when
        BigDecimal result = wordCount.processText(text, Collections.emptyMap());
        //then
        assertEquals(new BigDecimal(10), result);
    }


}