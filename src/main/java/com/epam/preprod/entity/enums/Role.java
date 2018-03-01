package com.epam.preprod.entity.enums;

public enum Role {

    UNKNOWN(0), USER(1), ADMIN(2);

    private int numericValue;

    Role(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
