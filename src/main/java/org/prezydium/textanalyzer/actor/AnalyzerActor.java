package org.prezydium.textanalyzer.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class AnalyzerActor extends AbstractActor {

    private final ActorRef dataCollector;

    public static Props props(ActorRef dataCollector){
        return Props.create(AnalyzerActor.class, () -> new AnalyzerActor(dataCollector));
    }

    public AnalyzerActor(ActorRef dataCollector){
        this.dataCollector = dataCollector;
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
