package org.prezydium.textanalyzer.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.prezydium.textanalyzer.metrics.SentenceCount;
import org.prezydium.textanalyzer.metrics.WordCount;
import org.prezydium.textanalyzer.util.DividorUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCollectorActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private Map<String, BigDecimal> summedResults = new HashMap<>();

    private List<String> metricsToBeSummedUp = Arrays.asList(SentenceCount.METRIC_NAME, WordCount.METRIC_NAME);

    private int numberOfAnalyzerActors;

    private int receivedMessages;

    public static Props props() {
        return Props.create(DataCollectorActor.class, DataCollectorActor::new);
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
                    analysisResults.resultsMap.forEach((k, v) ->
                    {
                        BigDecimal metricResult = summedResults.getOrDefault(k, new BigDecimal(0));
                        if (metricsToBeSummedUp.contains(k)) {
                            metricResult = metricResult.add(v);
                        } else {
                            metricResult = DividorUtil.average(metricResult, v);
                        }
                        summedResults.put(k, metricResult);
                        log.info(k + " " + metricResult);
                        receivedMessages++;
                        if (receivedMessages >= numberOfAnalyzerActors && numberOfAnalyzerActors > 0){
                            printResultsToFile(summedResults);
                        }
                    });
                })
                .match(Integer.class, x -> numberOfAnalyzerActors = x
                )
                .build();
    }

    private void printResultsToFile(Map<String, BigDecimal> results) {
        StringBuilder sb = new StringBuilder();
        results.forEach((k, v) -> sb
                .append(k)
                .append(": ")
                .append(v)
                .append("\n"));
        try (PrintStream writer = new PrintStream(new FileOutputStream("results.txt"))) {
            writer.append(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
