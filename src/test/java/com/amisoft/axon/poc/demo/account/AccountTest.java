package com.amisoft.axon.poc.demo.account;

import com.amisoft.axon.poc.demo.coreapi.AccountCreatedEvent;
import com.amisoft.axon.poc.demo.coreapi.CreateAccountCommand;
import com.amisoft.axon.poc.demo.coreapi.MoneyWithdrawnEvent;
import com.amisoft.axon.poc.demo.coreapi.WithdrawMoneyCommand;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {

        fixture = Fixtures.newGivenWhenThenFixture(Account.class);
    }

    @Test
    public void testCreateAccount() throws Exception {

         fixture.givenNoPriorActivity().when(new CreateAccountCommand("1234", 1000))
                 .expectEvents(new AccountCreatedEvent("1234",1000));

    }



    @Test
    public void testWithdrawReasonableAmount(){

        fixture.given(new AccountCreatedEvent("1234",1000))
                .when(new WithdrawMoneyCommand("1234",600))
                .expectEvents(new MoneyWithdrawnEvent("1234",600,-600));

    }

    @Test
    public void testWithdrawAbsurdAmount(){

        fixture.given(new AccountCreatedEvent("1234",1000))
                .when(new WithdrawMoneyCommand("1234",1001))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);

    }

    @Test
    public void testWithdrawTwice(){

        fixture.given(new AccountCreatedEvent("1234",1000),
                      new MoneyWithdrawnEvent("1234", 999, -999))
                .when( new WithdrawMoneyCommand("1234", 2))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);


    }
}