package com.counter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Counter {

	private static final int numThreads = 10;
	private static final int numMessages = 100;

	public static void main(String[] args) {

		final ActorSystem sys = ActorSystem.create("System");
		final ActorRef counter = sys.actorOf(CounterActor.props(), "counter");

		// Send messages from multiple threads in parallel
		final ExecutorService exec = Executors.newFixedThreadPool(numThreads);

        // Test for exercise 1: send increment and decrement messages
//		for (int i = 0; i < numMessages; i++) {
//            exec.submit(() -> counter.tell(new SimpleMessage(true), ActorRef.noSender()));  // increase +1
//            exec.submit(() -> counter.tell(new SimpleMessage(false), ActorRef.noSender())); // decrease -1
//        }

        // Test for exercise 2:
        counter.tell(new SimpleMessage(false), ActorRef.noSender()); // decrement
        counter.tell(new SimpleMessage(false), ActorRef.noSender()); // decrement
        counter.tell(new SimpleMessage(true), ActorRef.noSender());  // increment
        counter.tell(new SimpleMessage(false), ActorRef.noSender()); // decrement
        counter.tell(new SimpleMessage(true), ActorRef.noSender());  // increment
        counter.tell(new SimpleMessage(true), ActorRef.noSender());  // increment



		
		// Wait for all messages to be sent and received
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exec.shutdown();
		sys.terminate();

	}

}
