package com.alijabobija.Entity;

/**
 * Entity class for Word
 */
public class Word {
    private String value;

    public Word(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean equals(String wordVal) {
        return value.equalsIgnoreCase(wordVal);
    }

    /**
     * Method for comparing word with other word value.
     * Method will ignore upper/lower case.
     *
     * @param wordVal value of word
     * @return true - if value of this word contains provided value
     */
    public boolean like(String wordVal) {
        return value.toLowerCase().contains(wordVal.toLowerCase());
    }
}
