package com.amisoft.axon.poc.demo.transfer;

import com.amisoft.axon.poc.demo.coreapi.MoneyTransferRequestedEvent;
import com.amisoft.axon.poc.demo.coreapi.WithdrawMoneyCommand;
import org.axonframework.test.saga.AnnotatedSagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTransferSagaTest {


    private AnnotatedSagaTestFixture  fixture;

    @Before
    public void setUp() throws Exception {

        fixture =  new AnnotatedSagaTestFixture<>(MoneyTransferSaga.class);
    }

    @Test
    public void testMoneyTransferRequest() throws Exception {

        fixture.givenNoPriorActivity()
                .whenPublishingA(new MoneyTransferRequestedEvent("tf1","acct1","acct2",100 ))
                .expectActiveSagas(1)
                .expectDispatchedCommandsEqualTo(new WithdrawMoneyCommand("acct1", 100));
    }

}