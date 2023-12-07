package ru.clevertec.cuncurrency.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Getter;

public class Server {

    @Getter
    private final List<Integer> sharedResource = new ArrayList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Random random = new Random();
    private final Lock lock = new ReentrantLock();

    public Future<Response> sendRequest(Request request) {
        return executor.submit(() -> {
            try {
                Thread.sleep(random.nextInt(100, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Thread.currentThread().interrupt();
            }
            return processRequest(request);
        });
    }

    public Response processRequest(Request request) {
        lock.lock();
        try {
            sharedResource.add(request.getValue());
            return new Response(sharedResource.size());
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        executor.shutdown();
    }

}
