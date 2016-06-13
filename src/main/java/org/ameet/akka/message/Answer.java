package org.ameet.akka.message;

import org.ameet.app.model.TechQuote;

import java.util.List;

/**
 * Created by achaub001c on 6/10/2016.
 * to get the final answer
 */
public class Answer {
    private List<TechQuote> quotes;

    public Answer(List<TechQuote> quotes) {
        this.quotes = quotes;
    }

    public Answer() {
    }

    public List<TechQuote> getQuote() {
        return quotes;
    }
}
