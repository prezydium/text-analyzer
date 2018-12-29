package org.prezydium.textanalyzer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.prezydium.textanalyzer.actor.AnalyzerOverlordActor;

public class App {


    public static void main(String[] args) {
        if (args.length < 1){
            System.exit(0);
        }
        ActorSystem actorSystem = ActorSystem.create("text-analyzer-system");
        ActorRef overlordActor = actorSystem.actorOf(AnalyzerOverlordActor.props(), "overlord");
        overlordActor.tell(new AnalyzerOverlordActor.StartAnalyze(args[0]), ActorRef.noSender());


    }

}
