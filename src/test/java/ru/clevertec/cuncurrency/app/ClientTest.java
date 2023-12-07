package ru.clevertec.cuncurrency.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientTest {

    @Mock
    private Server server;
    @InjectMocks
    private Client client;
    private List<Integer> dataList;
    private final int dataListSize = 5;

    @BeforeEach
    void setUp() {
        dataList = new ArrayList<>();
        for (int i = 1; i <= dataListSize; i++) {
            dataList.add(i);
        }
        this.server = new Server();
        this.client = new Client(dataList, server);
    }

    @Test
    void testClientDataListShouldBeEmpty() {
        // when
        client.call();

        // then
        assertTrue(dataList.isEmpty());
    }

}