package org.ameet.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.ameet.akka.actor.Listener;
import org.ameet.akka.actor.Master;
import org.ameet.akka.message.Initiate;
import org.springframework.stereotype.Component;

/**
 * Created by achaub001c on 6/7/2016.
 * This component creates the Akka system and performs the requested processing
 * The system creation and teardown as well as actor creation can be expensive.
 * so we will try to save them and just wipe out the slate on listener to
 * restart the calculation
 */
@Component
public class AkkaProcessor {
    private ActorSystem actorSystem;
    private ActorRef master;
    private ActorRef listener;
    private int nrOfWorkers = 4;
    private int nrOfMessages = 10000;
    private int nrOfElements = 10000;

    public AkkaProcessor() {
        // Create an Akka system
        actorSystem = ActorSystem.create("PiSystem");
    }

    /**
     * perform the actual processing
     *
     * @param nrOfWorkers
     * @param nrOfElements
     * @param nrOfMessages
     */
    public void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
        // create the result listener, which will print the result and shutdown the system
        final ActorRef listener = actorSystem.actorOf(Props.create(Listener.class), "listener:" + System
                .currentTimeMillis());

        // create the master
        ActorRef master = actorSystem.actorOf(Props.create(Master.class, nrOfWorkers, nrOfMessages, nrOfElements,
                listener), "master" + System.currentTimeMillis());
        // start calculation
        master.tell(new Initiate(), ActorRef.noSender());
    }
}
