package org.prezydium.textanalyzer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DividorUtil {

    public static BigDecimal average(BigDecimal a, BigDecimal b){
        BigDecimal temp = a.add(b);
        return temp.divide(new BigDecimal(2), 3, RoundingMode.HALF_UP);
    }

}
