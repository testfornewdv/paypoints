package org.example;

import java.util.*;

public class Shop {

    private final static int PAYPOINT_1_THROUGHPUT_CLIENTS_PER_HOUR = 10;
    private final static int PAYPOINT_2_THROUGHPUT_CLIENTS_PER_HOUR = 13;
    private final static int PAYPOINT_3_THROUGHPUT_CLIENTS_PER_HOUR = 15;
    private final static int PAYPOINT_4_THROUGHPUT_CLIENTS_PER_HOUR = 17;

    private final Map<PayPoint, Integer> payPoints = new HashMap<>();

    public Shop() {
        payPoints.put(new PayPoint(PAYPOINT_1_THROUGHPUT_CLIENTS_PER_HOUR), 1);
        payPoints.put(new PayPoint(PAYPOINT_2_THROUGHPUT_CLIENTS_PER_HOUR), 2);
        payPoints.put(new PayPoint(PAYPOINT_3_THROUGHPUT_CLIENTS_PER_HOUR), 3);
        payPoints.put(new PayPoint(PAYPOINT_4_THROUGHPUT_CLIENTS_PER_HOUR), 4);
    }

    public int processEvent(Character event) {
        switch (event) {
            case 'A':
                return appendPurchaserToQueue();
            case '1':
            case '2':
            case '3':
            case '4':
                return removePurchaserFromQueue(Integer.parseInt(event.toString()));
            default:
                throw new IllegalArgumentException("unknown command");
        }
    }

    private int appendPurchaserToQueue() {
        PayPoint payPoint = payPoints.keySet()
                .parallelStream()
                .min(Comparator.comparingDouble(PayPoint::getEstimatedTimeToFinishProcessingNextUnitMs))
                .orElseThrow();
        payPoint.addWorkUnitToQueue();
        return payPoints.get(payPoint);
    }

    private int removePurchaserFromQueue(int queueNum) {
        PayPoint payPoint = payPoints.entrySet().parallelStream()
                .filter(pp -> pp.getValue() == queueNum)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow();
        payPoint.removeWorkUnitFromQueue();
        return payPoints.get(payPoint);
    }
}
