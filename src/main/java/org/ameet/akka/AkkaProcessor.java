package org.ameet.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import org.ameet.akka.actor.Master;
import org.ameet.akka.message.Answer;
import org.ameet.akka.message.Initiate;
import org.ameet.app.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by achaub001c on 6/7/2016.
 * This component creates the Akka system and performs the requested processing
 * The system creation and teardown as well as actor creation can be expensive.
 * so we will try to save them and just wipe out the slate on listener to
 * restart the calculation
 */
@Component
public class AkkaProcessor {
    private int i = 1;
    @Autowired
    private QuoteService quoteService;

    private ActorSystem actorSystem;
    private ActorRef master;

    public AkkaProcessor() {
        // Create an Akka system
        actorSystem = ActorSystem.create("PiSystem");
    }

    public Answer calculate(final String url, final int nrOfWorkers)
            throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        // create the master
        ActorRef master = actorSystem.actorOf(Props.create(Master.class, nrOfWorkers, url,
                latch, quoteService), "master" + System.currentTimeMillis());
        // start calculation
        master.tell(new Initiate(i++), ActorRef.noSender());
        latch.await(5, TimeUnit.SECONDS);
        System.out.println("Latch released... asking for result");
        Answer ans = (Answer) Await.result(Patterns.ask(master, new Answer(), 2000), Duration.create(3000, TimeUnit
                .MILLISECONDS));
        return ans;
    }

    public void shutdown() throws TimeoutException, InterruptedException {
        System.out.println("\n\nKilling ##########");
        actorSystem.terminate();
        Await.ready(actorSystem.whenTerminated(), Duration.create(5, TimeUnit.SECONDS));
    }
}
