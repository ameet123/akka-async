package org.ameet.akka.actor;

import akka.actor.UntypedActor;
import org.ameet.akka.message.StatusReport;

/**
 * Created by achaub001c on 6/7/2016.
 */
public class Listener extends UntypedActor {
    private boolean isShut;

    /**
     * based on the boolean isShut flag, we will terminate the system.
     *
     * @param isShut
     */
    public Listener(boolean isShut) {
        this.isShut = isShut;
    }


    public void onReceive(Object message) {
        if (message instanceof StatusReport) {
            StatusReport approximation = (StatusReport) message;
            System.out.println(String.format("\n\tAkkaProcessor approximation: \t\t%s\n\tCalculation time: \t%s",
                    approximation.getPi(), approximation.getDuration()));
            getContext().stop(getSelf());
//            if (isShut) {
//                System.out.println(">>> Shutting down the system... Pi=" + approximation.getPi());
//                getContext().system().terminate();
//            }
        } else {
            unhandled(message);
        }
    }
}