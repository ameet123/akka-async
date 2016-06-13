package org.ameet.akka.message;

import org.ameet.app.model.TechQuote;

/**
 * Created by achaub001c on 6/6/2016.
 * This is sent by each Worker to the Master describing
 * the result of their work.
 */
public class Result {

    private final TechQuote quote;

    public Result(TechQuote quote) {
        this.quote = quote;
    }

    public TechQuote getQuote() {
        return quote;
    }
}
