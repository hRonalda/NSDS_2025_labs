package com.counter;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithStash;
import akka.actor.Props;

//public class CounterActor extends AbstractActor {

// exercise2: to use stash(), extend from AbstractActorWithStash
public class CounterActor extends AbstractActorWithStash {

	private int counter;

	public CounterActor() {
		this.counter = 0;
	}

    @Override
	public Receive createReceive() {
		return receiveBuilder()
                .match(SimpleMessage.class, this::onMessage)
                .build();
	}

// Exercise 1:
// method 1: use the flag
    void onMessage(SimpleMessage message) {
        if (message.increase) {
            ++counter;
            System.out.println("Counter increased to " + counter);
            unstashAll(); // release any stashed decrements
        } else {
            if (counter == 0) {
                stash(); // hold it for later
                System.out.println("Decrement stashed (counter = " + counter + ")");
            } else {
                --counter;
                System.out.println("Counter decreased to " + counter);
            }

        }
    }

//  method 2: Use two distinct message classes
//    	@Override
//	public Receive createReceive() {
//		return receiveBuilder()
//                .match(SimpleMessage.class, this::onIncrease)
//                .match(SimpleMessage.class, this::onDecrease)
//                .build();
//	}


//	void onIncrease(SimpleMessage msg) {
//		++counter;
//		System.out.println("Counter increased to " + counter);
//	}
//
//    void onDecrease(SimpleMessage msg) {
//        --counter;
//        System.out.println("Counter decreased to " + counter);
//    }



	static Props props() {
		return Props.create(CounterActor.class);
	}

}
