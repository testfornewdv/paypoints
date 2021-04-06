package org.example;

import java.util.Date;

public class PayPoint {
    public static final int LIMIT_CLIENTS_IN_QUEUE = 20;

    private long timeToProcessPerWorkUnitMs = 0;

    private double amountOfWorkToProcess = 0;
    private long processingStartedAtMs = 0;

    public PayPoint(int throughputWorkUnitsPerHour) {
        if (throughputWorkUnitsPerHour <= 0) throw new IllegalArgumentException("'throughput' should be positive value");
        this.timeToProcessPerWorkUnitMs = (3_600 * 1_000 / throughputWorkUnitsPerHour);
    }

    public void addWorkUnitToQueue() {
        long now = new Date().getTime();
        recalcProcessingState(now);
        if (amountOfWorkToProcess >= LIMIT_CLIENTS_IN_QUEUE)
            throw new IllegalArgumentException("queue limit reached");
        if (amountOfWorkToProcess == 0)
            processingStartedAtMs = now;
        amountOfWorkToProcess += 1;
    }

    public void removeWorkUnitFromQueue() {
        if (amountOfWorkToProcess > 1)
            amountOfWorkToProcess -= 1;
    }

    public double getEstimatedTimeToFinishProcessingNextUnitMs() {
        return getTimeToProcessCurrentAmountOfWorkMs() + timeToProcessPerWorkUnitMs;
    }

    public double getTimeToProcessCurrentAmountOfWorkMs() {
        long now = new Date().getTime();
        recalcProcessingState(now);
        return amountOfWorkToProcess * timeToProcessPerWorkUnitMs;
    }

    private void recalcProcessingState(long now) {
        if (amountOfWorkToProcess == 0)
            return;
        if ((now - processingStartedAtMs) > amountOfWorkToProcess * timeToProcessPerWorkUnitMs) {
            amountOfWorkToProcess = 0;
            return;
        }
        amountOfWorkToProcess -= (double) (now - processingStartedAtMs) / timeToProcessPerWorkUnitMs;
    }
}
