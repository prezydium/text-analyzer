package org.prezydium.textanalyzer.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class AnalyzerOverlordActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef dataCollector;

    public AnalyzerOverlordActor() {
        this.dataCollector = getContext().actorOf(DataCollectorActor.props(), "data-collector");
    }

    public static Props props() {
        return Props.create(AnalyzerOverlordActor.class, () -> new AnalyzerOverlordActor());
    }

    public static class StartAnalyze {

        private final String fileName;

        public StartAnalyze(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    @Override
    public void preStart() throws Exception {
        log.info("Starting main actor");
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(StartAnalyze.class, start -> {
                    sendPartOfTextToProcess(start.getFileName());
                })
                .build();
    }

    private void sendPartOfTextToProcess(String fileName){
        int lineCount = 0;
        try {
            try (FileInputStream inputStream = new FileInputStream(fileName);
                 Scanner sc = new Scanner(inputStream, "UTF-8")) {
                do {
                    StringBuilder sb = new StringBuilder();
                    while (sc.hasNextLine() && lineCount < 1000) {
                        sb.append(sc.nextLine() + "\n");
                        lineCount++;
                    }
                    lineCount = 0;
                    getContext()
                            .actorOf(AnalyzerActor.props(dataCollector))
                            .tell(sb.toString(), getSelf());
                } while (sc.hasNextLine());
            }
        } catch (IOException e) {
            log.error("Error reading file");
        }

    }
}
