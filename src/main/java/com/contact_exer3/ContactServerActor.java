// Write a server actor that manages a contact list that associates
//email addresses to names

// The server actor processes two types of messages
// Define two Message types:
// 1. PutMsg: to add a new contact in the list
// 2. GetMsg: to read the address of a contact given its name


package com.contact_exer3;

import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class ContactServerActor extends AbstractActor {

    // One structure to store name-email info
    private final Map<String, String> contact = new HashMap<>();

    // Add(Client → Server): requests to store a new contact
    public static final class PutMsg {
        public final String name;
        public final String email;

        public PutMsg(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    // Request(Client → Server): ask for an email by name
    public static final class GetMsg {
        public final String name;

        public GetMsg(String name) {
            this.name = name;
        }
    }

    // Response(Server → Client): send back the email found
    public static final class GetEmailResult {
        public final String email;

        public GetEmailResult(String email) {
            this.email = email;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PutMsg.class, this::onPutMsg)
                .match(GetMsg.class, this::onGetMsg)
                .build();
    }

    void onPutMsg(PutMsg msg) {
        contact.put(msg.name, msg.email);
//        System.out.println("Added the new contact.");
    }

    void onGetMsg(GetMsg msg) {
        String email = contact.get(msg.name);

        // send the result back to whoever sent the GetMsg
        getSender().tell(new GetEmailResult(email), getSelf());

//        System.out.println("Read from the contact.");
    }
}
