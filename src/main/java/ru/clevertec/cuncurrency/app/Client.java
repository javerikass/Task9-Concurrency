package ru.clevertec.cuncurrency.app;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Getter;

public class Client implements Callable<Integer> {

    private final List<Integer> dataList;
    @Getter
    private AtomicInteger accumulator;
    private final Lock lock;
    private final Server server;
    private final Random random;

    public Client(List<Integer> dataList, Server server) {
        this.dataList = dataList;
        this.server = server;
        this.random = new Random();
        this.lock = new ReentrantLock();
        this.accumulator = new AtomicInteger(0);
    }

    @Override
    public Integer call() {
        while (!dataList.isEmpty()) {
            lock.lock();
            int value = dataList.remove(random.nextInt(dataList.size()));
            try {
                Thread.sleep(random.nextInt(100, 500));
                Future<Response> future = server.sendRequest(new Request(value));
                accumulate(future);
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return accumulator.get();
    }

    private void accumulate(Future<Response> result)
        throws ExecutionException, InterruptedException {
        int responseSize = result.get().getValue();
        accumulator.addAndGet(responseSize);
    }

}
