package org.ameet.akka.actor;

import akka.actor.UntypedActor;
import org.ameet.akka.message.StatusReport;

/**
 * Created by achaub001c on 6/7/2016.
 */
public class Listener extends UntypedActor {
    public void onReceive(Object message) {
        if (message instanceof StatusReport) {
            StatusReport approximation = (StatusReport) message;
            System.out.println(String.format("\n\tAkkaProcessor approximation: \t\t%s\n\tCalculation time: \t%s",
                    approximation.getPi(), approximation.getDuration()));
            getContext().stop(getSelf());
            getContext().watch(getSelf());
        } else {
            unhandled(message);
        }
    }
}