package org.ameet.akka.actor;

import akka.actor.UntypedActor;
import org.ameet.akka.message.Answer;
import org.ameet.akka.message.StatusReport;

/**
 * Created by achaub001c on 6/7/2016.
 */
public class Listener extends UntypedActor {
    private double answer;
    /**
     * this is where the system is shut down based on a flag.
     *
     * @param message
     */
    public void onReceive(Object message) {
        if (message instanceof StatusReport) {
            StatusReport approximation = (StatusReport) message;
            answer = approximation.getPi();
        } else if (message instanceof Answer) {
            System.out.println(">>> Providing an answer and stopping");
            getSender().tell(new Answer().setPi(answer), getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }
}