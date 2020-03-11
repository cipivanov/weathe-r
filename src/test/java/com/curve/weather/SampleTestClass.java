package com.curve.weather;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class SampleTestClass {

    @Test
    public void shouldPass() {
        assertThat(SampleClass.returnFourtyTwo()).isEqualTo(42);
    }
}
