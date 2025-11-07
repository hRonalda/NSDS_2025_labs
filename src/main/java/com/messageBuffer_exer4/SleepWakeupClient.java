package com.messageBuffer_exer4;

import akka.actor.*;

public class SleepWakeupClient extends AbstractActor {
    public static void main(String[] args) {
        // 1. Create ActorSystemc
        final ActorSystem system = ActorSystem.create("SleepWakeupClient");

        // 2. Create server actor and client actor
        final ActorRef server = system.actorOf(Props.create(SleepWakeupServerActor.class), "server");
        final ActorRef client = system.actorOf(Props.create(SleepWakeupClient.class), "client");

        // 3. test the messages
        server.tell(new SleepWakeupServerActor.TextMsg("Message 1"), client);
        server.tell(new SleepWakeupServerActor.TextMsg("Message 2"), client);
        server.tell(new SleepWakeupServerActor.SleepMsg(), client);
        server.tell(new SleepWakeupServerActor.TextMsg("Message 3"), client);
        server.tell(new SleepWakeupServerActor.TextMsg("Message 4"), client);
        server.tell(new SleepWakeupServerActor.WakeupMsg(), client);
        server.tell(new SleepWakeupServerActor.TextMsg("Message 5"), client);
        server.tell(new SleepWakeupServerActor.TextMsg("Message 6"), client);

        // keep system alive
        try {
            System.in.read();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        system.terminate();

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> System.out.println("client received reply: " + msg))
                .build();
    }




}
