package org.ameet.akka.actor;

import akka.actor.UntypedActor;
import org.ameet.akka.message.Result;
import org.ameet.akka.message.Work;

/**
 * Created by achaub001c on 6/6/2016.
 */
public class Worker extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Work) {
            Work work = (Work) message;
            double result = calculatePiFor(work.getStart(), work.getNrOfElements());
            getSender().tell(new Result(result), getSelf());
        } else {
            unhandled(message);
        }
    }

    private double calculatePiFor(int start, int nrOfElements) {
        double acc = 0.0;
        for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
            acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
        }
        return acc;
    }
}
