package com.amisoft.axon.poc.demo.coreapi

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.axonframework.commandhandling.model.AggregateIdentifier


class RequestMoneyTransferCommand(@AggregateIdentifier val transferId : String, val sourceAccount : String, val targetAccount : String, val amount : Int);

class MoneyTransferRequestedEvent( val transferId : String,val sourceAccount : String, val targetAccount : String, val amount : Int);



class CompleteMoneyTransferCommand (@TargetAggregateIdentifier val transferId : String);
class MoneyTransferCompletedEvent( val transferId : String);



class CancelMoneyTransferCommand (@TargetAggregateIdentifier val transferId : String);
class MoneyTransferCancelledEvent( val transferId : String);

