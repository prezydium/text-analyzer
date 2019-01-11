package org.prezydium.textanalyzer.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.prezydium.textanalyzer.metrics.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzerActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef dataCollector;

    private final List<TextMetric> textMetrics;

    public static Props props(ActorRef dataCollector) {
        return Props.create(AnalyzerActor.class, () -> new AnalyzerActor(dataCollector));
    }

    public AnalyzerActor(ActorRef dataCollector) {
        this.dataCollector = dataCollector;
        this.textMetrics = createListOfMetrics();
    }

    @Override
    public void preStart() {
        log.info("Analyzer is starting");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    Map<String, BigDecimal> results = new HashMap<>();
                    for (TextMetric metric : textMetrics) {
                        results.put(metric.getMetricName(), metric.processText(s, results));
                    }
                    dataCollector.tell(new DataCollectorActor.AnalysisResults(results), getSelf());
                })
                .build();
    }

    private List<TextMetric> createListOfMetrics() {
        List<TextMetric> textMetrics = new ArrayList<>();
        textMetrics.add(new WordCount());
        textMetrics.add(new SentenceCount());
        textMetrics.add(new AverageWordsLength());
        textMetrics.add(new AverageWordsPerSentenceCount());
        return textMetrics;
    }
}
