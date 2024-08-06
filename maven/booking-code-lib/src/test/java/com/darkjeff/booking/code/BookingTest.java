package com.darkjeff.booking.code;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest  {

    private final Booking booking = new Booking();

    @Test
    void shouldGenerateRandomCodeWithEightCharLong() {
        var code = booking.generate();
        assertThat(code).isNotNull().hasSize(8);
    }

    @Test
    void shouldGenerateRandomCodeWithLimitToInput() {
        var code = booking.generate(2);
        assertThat(code).isNotNull().hasSize(2);
    }

    @Test
    void shouldGenerateRandomCodeForInputTypeWithDefaultLimit() {
        var code = booking.generate(CodeType.CHARS_ONLY);
        assertThat(code).isNotNull().hasSize(8);
        assertThat(Pattern.compile("^[A-Z]*$").matcher(code).matches()).isTrue();
    }

    @Test
    void shouldGenerateRandomCodeForInputTypeWithInputLimit() {
        var code = booking.generate(2, CodeType.NUMBERS_ONLY);
        assertThat(code).isNotNull().hasSize(2);
        assertThat(Pattern.compile("^[0-9]*$").matcher(code).matches()).isTrue();
    }

}
