package com.epam.preprod.entity.enums;


public enum Status {
    ACCEPTED(1), CONFIRMED(2), FORMED(3), SENT(4), COMPLETED(5), CANCELED(6);

    private int numericValue;

    Status(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
