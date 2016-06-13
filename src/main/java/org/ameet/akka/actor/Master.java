package org.ameet.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import org.ameet.akka.message.Answer;
import org.ameet.akka.message.Initiate;
import org.ameet.akka.message.Result;
import org.ameet.akka.message.Work;
import org.ameet.app.QuoteService;
import org.ameet.app.model.TechQuote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by achaub001c on 6/6/2016.
 */
public class Master extends UntypedActor {
    private final long start = System.currentTimeMillis();
    private int nrOfWorkers;
    private double pi;
    private int nrOfResults = 0;
    private Router router;
    private double answer;
    private CountDownLatch latch;
    private String url;
    private List<TechQuote> quoteList;
    private QuoteService quoteService;

    public Master(int nrOfWorkers, String url, CountDownLatch latch, QuoteService quoteService) {
        this.latch = latch;
        this.quoteService = quoteService;
        this.url = url;
        this.nrOfWorkers = nrOfWorkers;
        quoteList = new ArrayList<>();

        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < nrOfWorkers; i++) {
            ActorRef r = getContext().actorOf(Props.create(Worker.class));
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
            for (int start = 0; start < nrOfWorkers; start++) {
                router.route(new Work(url, quoteService), getSelf());
            }
        } else if (message instanceof Result) {

            Result result = (Result) message;
            quoteList.add(result.getQuote());
            nrOfResults += 1;
            if (nrOfResults == nrOfWorkers) {
                System.out.println("Final result received in master and counting down latch.");
                latch.countDown();
            }
        } else if (message instanceof Answer) {
            System.out.println("Received command for answer, shutting self down. sayonara!");
            getSender().tell(new Answer(quoteList), getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }
}