package org.prezydium.textanalyzer.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.math.BigDecimal;
import java.util.Map;

public class DataCollectorActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props() {
        return Props.create(DataCollectorActor.class, () -> new DataCollectorActor());
    }

    public static class AnalysisResults {

        private final Map<String, BigDecimal> resultsMap;

        public AnalysisResults(Map<String, BigDecimal> resultsMap) {
            this.resultsMap = resultsMap;
        }

        public Map<String, BigDecimal> getResultsMap() {
            return resultsMap;
        }
    }

    @Override
    public void preStart() {
        log.info("Data collector started");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AnalysisResults.class, analysisResults -> {
                    //TODO
                    analysisResults.resultsMap.forEach((k, v) -> log.info(k + " " + v));
                })
                .build();
    }
}
