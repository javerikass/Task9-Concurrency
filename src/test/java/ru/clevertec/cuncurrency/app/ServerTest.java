package ru.clevertec.cuncurrency.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerTest {

    private Client client;
    private List<Integer> dataList;
    private Server server;
    private final int bound = 10;

    @BeforeEach
    void setUp() {
        dataList = new ArrayList<>();
        for (int i = 1; i <= bound; i++) {
            dataList.add(i);
        }
        this.server = new Server();
        this.client = new Client(dataList, server);
    }

    @Test
    void testServerShouldHaveEqualsSizeClientAndServer() {
        // when
        client.call();
        int actual = server.getSharedResource().size();

        // then
        assertEquals(bound, actual);
    }

    @Test
    void testServerShouldContainsTheSameItems() {
        // when
        client.call();
        List<Integer> sharedResource = server.getSharedResource();

        // then
        for (int i = 1; i <= bound; i++) {
            assertTrue(sharedResource.contains(i));
        }
    }

}