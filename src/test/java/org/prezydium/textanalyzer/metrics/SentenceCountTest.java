package org.prezydium.textanalyzer.metrics;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.*;

public class SentenceCountTest {

    @Test
    public void shouldFind3Sentences(){
        //given
        String sentence = "This is a test. It contains" +
                "three sentences! Find them...";
        SentenceCount sentenceCount = new SentenceCount();
        //when
        BigDecimal result = sentenceCount.processText(sentence, Collections.emptyMap());
        //then
        assertEquals(new BigDecimal(3), result);
    }

    @Test
    public void shouldReturn0WhenTextIsEmpty(){
        //given
        String sentence = "";
        SentenceCount sentenceCount = new SentenceCount();
        //when
        BigDecimal result = sentenceCount.processText(sentence, Collections.emptyMap());
        //then
        assertEquals(new BigDecimal(0), result);
    }

}