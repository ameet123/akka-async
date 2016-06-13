package org.ameet.akka.message;

import java.util.List;

/**
 * Created by achaub001c on 6/10/2016.
 * to get the final answer
 */
public class Answer {
    private List<String> quotes;

    public Answer(List<String> quotes) {

        this.quotes = quotes;
    }

    public List<String> getQuote() {
        return quotes;
    }
    //    private double pi;
//
//    public double getPi() {
//        return pi;
//    }
//
//    public Answer setPi(double pi) {
//        this.pi = pi;
//        return this;
//    }
}
