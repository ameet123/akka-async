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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

    public Master(final int nrOfWorkers, int nrOfMessages, int nrOfElements, ActorRef listener) {
        this.nrOfMessages = nrOfMessages;
        this.nrOfElements = nrOfElements;
        this.listener = listener;
        List<Routee> routees = new ArrayList<Routee>();

        for (int i = 0; i < nrOfWorkers; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

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
                // Send the result to the listener
                Duration duration = Duration.ofMillis((System.currentTimeMillis() - start));
                listener.tell(new StatusReport(pi, duration), getSelf());
                // Stops this actor and all its supervised children
                getContext().stop(getSelf());
                getContext().watch(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}
