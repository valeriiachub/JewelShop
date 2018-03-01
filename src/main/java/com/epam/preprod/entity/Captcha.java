package com.epam.preprod.entity;

import java.util.Date;

public class Captcha {
    private int captchaId;

    private Date creationDate;

    private String captchaValue;

    public Captcha(int captchaId, String captchaValue, Date creationDate) {
        this.captchaId = captchaId;
        this.captchaValue = captchaValue;
        this.creationDate = creationDate;
    }

    public int getCaptchaId() {
        return captchaId;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Captcha captcha = (Captcha) o;

        if (captchaId != captcha.captchaId) return false;
        if (!creationDate.equals(captcha.creationDate)) return false;
        return captchaValue.equals(captcha.captchaValue);
    }

    @Override
    public int hashCode() {
        int result = captchaId;
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + captchaValue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "captchaId=" + captchaId +
                ", captchaValue='" + captchaValue + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
