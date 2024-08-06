package com.darkjeff.booking.code;

public enum CodeType {
    NUMBERS_ONLY("^[0-9]*$"),
    CHARS_ONLY("^[A-Z]*$"),
    MIXED("^[0-9A-Z]*$");

    private String pattern;

    CodeType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

}
