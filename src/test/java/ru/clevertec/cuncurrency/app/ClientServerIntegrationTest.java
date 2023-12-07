package ru.clevertec.cuncurrency.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientServerIntegrationTest {

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
    void testShouldReturnRightAccumulatorAmount() {
        // given
        int expected = (1 + bound) * (bound / 2);

        // when
        client.call();
        int actual = client.getAccumulator().get();

        // then
        assertEquals(actual, expected);
    }

}