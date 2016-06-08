package org.ameet.akka.actor;

import akka.actor.*;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import org.ameet.akka.message.Initiate;
import org.ameet.akka.message.Result;
import org.ameet.akka.message.StatusReport;
import org.ameet.akka.message.Work;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.gracefulStop;

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
//        this.listener = listener;
        this.isShut = isShut;
        List<Routee> routees = new ArrayList<Routee>();

        for (int i = 0; i < nrOfWorkers; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
        // watch and wait for Terminated message from listener
        context().watch(this.listener);
    }

    public void onReceive(Object message) {
        if (message instanceof Initiate) {
            for (int start = 0; start < nrOfMessages; start++) {
                router.route(new Work(start, nrOfElements), getSelf());
            }
        } else if (message instanceof Terminated) {
            System.out.println("---> Terminated message received");
            final Terminated t = (Terminated) message;
            if (t.getActor() == listener) {
                isListenerTerminated = true;
                System.out.println("Master down?" + isMasterTerminated);
                if (isMasterTerminated) {
                    System.out.println(">>> Shutting down the system from Master... Master Down?" +
                            isMasterTerminated);
                    getContext().system().terminate();
                }
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

                try {
//                    Future<Boolean> stopped =
//                            gracefulStop(listener, Duration.create(5, TimeUnit.SECONDS), PoisonPill.getInstance());
//                    Await.result(stopped, Duration.create(6, TimeUnit.SECONDS));
                    // the actor has been stopped
                    getContext().stop(getSelf());
                    isMasterTerminated = true;
                    if (isShut ) {
                        System.out.println(">>> Shutting down the system from Master... Listener Down?" +
                                isListenerTerminated);
                        getContext().system().terminate();
                    }
                } catch (Exception e) {
                    // the actor wasn't stopped within 5 seconds
                }
            }
        } else {
            unhandled(message);
        }
    }
}
