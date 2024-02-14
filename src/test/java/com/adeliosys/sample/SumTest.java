package com.adeliosys.sample;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumTest {

    @ParameterizedTest
    @CsvSource({"3,4,7", "2,-2,0"}) // There are other source annotations such as @ValueSource, @MethodSource, etc
    public void sum(int a, int b, int result) { // Parameter mapping works fine with numbers, strings, enumerations, etc
        assertEquals(result, sum(a, b));
    }

    private int sum(int a, int b) {
        return a + b;
    }
}
