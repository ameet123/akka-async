package org.ameet.app.model;

/**
 * quote wrapper model entity
 */
public class QuoteResource {

    String type;
    Quote value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Quote getValue() {
        return value;
    }

    public void setValue(Quote value) {
        this.value = value;
    }
}