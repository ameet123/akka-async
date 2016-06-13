package org.ameet.akka.message;

import org.ameet.app.QuoteService;

/**
 * Created by achaub001c on 6/6/2016.
 * This is sent by Master to the Workers
 */
public class Work {
    private final String url;
    private QuoteService quoteService;

    public Work(String url, QuoteService quoteService) {
        this.quoteService = quoteService;

        this.url = url;
    }

    public QuoteService getQuoteService() {
        return quoteService;
    }

    public String getUrl() {
        return url;
    }
}
