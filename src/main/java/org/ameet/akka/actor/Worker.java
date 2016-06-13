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
            getSender().tell(new Result(work.getQuoteService().getQuote(work.getUrl())), getSelf());
        } else {
            unhandled(message);
        }
    }
}
