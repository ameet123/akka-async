package org.ameet.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import org.ameet.akka.message.Initiate;
import org.ameet.akka.message.Result;
import org.ameet.akka.message.StatusReport;
import org.ameet.akka.message.Work;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by achaub001c on 6/6/2016.
 */
public class Master extends UntypedActor {
    private final int nrOfMessages;
    private final int nrOfElements;
    private final long start = System.currentTimeMillis();
    private final ActorRef listener;
    private double pi;
    private int nrOfResults;
    private Router router;
    private boolean isShut;
    private boolean isListenerTerminated = false;
    private boolean isMasterTerminated = false;

    public Master(final int nrOfWorkers, int nrOfMessages, int nrOfElements, boolean isShut, ActorRef listener) {
        this.listener = getContext().system().actorOf(Props.create(Listener.class, isShut), "listener:" + System
                .nanoTime());
        this.nrOfMessages = nrOfMessages;
        this.nrOfElements = nrOfElements;
        List<Routee> routees = new ArrayList<Routee>();

        for (int i = 0; i < nrOfWorkers; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    /**
     * we don't need to watch over listener, since we could be dead before that Terminated message arrives
     *
     * @param message
     */
    public void onReceive(Object message) {
        if (message instanceof Initiate) {
            for (int start = 0; start < nrOfMessages; start++) {
                router.route(new Work(start, nrOfElements), getSelf());
            }
        } else if (message instanceof Result) {
            Result result = (Result) message;
            pi += result.getValue();
            nrOfResults += 1;
            if (nrOfResults == nrOfMessages) {
                System.out.println("Final result received in master...");
                // Send the result to the listener
                Duration duration = Duration.create((System.currentTimeMillis() - start), TimeUnit.MILLISECONDS);
                listener.tell(new StatusReport(pi, duration), getSelf());
                // Stops this actor and all its supervised children
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
