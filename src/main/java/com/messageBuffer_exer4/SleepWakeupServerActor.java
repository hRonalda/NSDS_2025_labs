package com.messageBuffer_exer4;

import akka.actor.AbstractActorWithStash;

public class SleepWakeupServerActor extends AbstractActorWithStash {

    private boolean isSleeping = false;

    // Three types of messages
    public static final class TextMsg {
        public final String text;
        public TextMsg(String text) {
            this.text = text;
        }
    }

    // don’t carry data (just signals), so don’t need fields.
    public static final class SleepMsg { }

    public static final class WakeupMsg{ }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TextMsg.class, this::onText)
                .match(SleepMsg.class, this::onSleep)
                .match(WakeupMsg.class, this::onWakeup)
                .build();
    }


    void onText(TextMsg msg) {
        if (isSleeping) {
             System.out.println("Stash message: " + msg.text);
             stash();
        } else {
            System.out.println("Received message: " + msg.text);
            getSender().tell("Echo from server: " + msg.text, getSelf());
        }
    }

    void onSleep(SleepMsg msg) {
        System.out.println("Sleeping");
        isSleeping = true;
    }

    void onWakeup(WakeupMsg msg) {
        System.out.println("Waking up");
        isSleeping = false;
        unstashAll();
    }
}
