package com.messageBuffer_exer4;

import akka.actor.AbstractActorWithStash;

public class SleepWakeupServerActor extends AbstractActorWithStash {

    private boolean isSleeping = false;

    // Three types of messages
    // For the normal text
    public static final class TextMsg {
        public final String text;
        public TextMsg(String text) { // The constructor is “how to create this message object with its values”.
            this.text = text;
        }
    }

    // For SleepMsg / WakeupMsg, they don’t carry data (just signals), so don’t need fields.
    public static final class SleepMsg { }

    public static final class WakeupMsg{ }

    // createReceive() defines how this actor reacts to each message type:
    // 	•.match(TextMsg.class, this::onText) → if incoming message is TextMsg, call onText(msg).
    //	•.match(SleepMsg.class, this::onSleep) → for SleepMsg, call onSleep(msg).
    //	•.match(WakeupMsg.class, this::onWakeup) → for WakeupMsg, call onWakeup(msg).
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TextMsg.class, this::onText)
                .match(SleepMsg.class, this::onSleep)
                .match(WakeupMsg.class, this::onWakeup)
                .build();
    }

    // Handler methods: onX() methods = “what actually do when getting that message?”
    void onText(TextMsg msg) {
        if (isSleeping) {
             System.out.println("Stash message: " + msg.text);
             stash();  // store for later
        } else {
            System.out.println("Received message: " + msg.text);
            getSender().tell("Echo from server: " + msg.text, getSelf());  // send back now
        }
    }

    void onSleep(SleepMsg msg) {
        System.out.println("Sleeping");
        isSleeping = true;
    }

    void onWakeup(WakeupMsg msg) {
        System.out.println("Waking up");
        isSleeping = false;
        unstashAll();  // replay stored messages
    }
}
