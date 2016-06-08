package org.ameet.akka.message;


import scala.concurrent.duration.Duration;

/**
 * Created by achaub001c on 6/6/2016.
 * Sent by Master to the Listener actor, reporting
 * the final status of all the work
 */
public class StatusReport {
    private final double pi;
    private final Duration duration;

    public StatusReport(double pi, Duration duration) {
        this.pi = pi;
        this.duration = duration;
    }

    public double getPi() {
        return pi;
    }

    public Duration getDuration() {
        return duration;
    }
}
