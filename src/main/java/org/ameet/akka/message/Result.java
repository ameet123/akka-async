package org.ameet.akka.message;

/**
 * Created by achaub001c on 6/6/2016.
 * This is sent by each Worker to the Master describing
 * the result of their work.
 */
public class Result {
    private final double value;

    public Result(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
