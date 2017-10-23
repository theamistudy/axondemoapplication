package com.amisoft.axon.poc.demo.account;

import com.amisoft.axon.poc.demo.coreapi.AccountCreatedEvent;
import com.amisoft.axon.poc.demo.coreapi.CreateAccountCommand;
import com.amisoft.axon.poc.demo.coreapi.MoneyWithdrawnEvent;
import com.amisoft.axon.poc.demo.coreapi.WithdrawMoneyCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@NoArgsConstructor
public class Account {

    @AggregateIdentifier
    private String accountId;

    private int balance;
    private int overdraftLimit;


    @CommandHandler
    public Account(CreateAccountCommand command){

        apply(new AccountCreatedEvent(command.getAccountId(),command.getOverdraftLimit()));

    }


    @CommandHandler
    public void handle(WithdrawMoneyCommand command) throws OverdraftLimitExceededException{
        
        if(balance + overdraftLimit >= command.getAmount()){

            apply(new MoneyWithdrawnEvent(command.getAccountId(),command.getAmount(), balance - command.getAmount()));
        } else{

            throw new OverdraftLimitExceededException();
        }

    }


    @EventSourcingHandler
    public void on(AccountCreatedEvent event){

        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverdraftLimit();
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event){

        this.balance = event.getBalance();

    }

}
