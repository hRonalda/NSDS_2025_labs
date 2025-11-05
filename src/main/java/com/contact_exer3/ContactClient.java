// exercise 3:
// Write an actor client that sends messages to the
// server actor and, in the case of a GetMsg, expects a
// reply and displays the content on the console.

package com.contact_exer3;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;


public class ContactClient extends AbstractActor {

    public static void main(String[] args) {
        // 1. Create ActorSystem
        final ActorSystem system = ActorSystem.create("ContactSystem");

        // 2. Create server and client actors
        final ActorRef server = system.actorOf(Props.create(ContactServerActor.class), "server");
        final ActorRef client = system.actorOf(Props.create(ContactClient.class), "client");

        // 3. Add some contacts
        server.tell(new ContactServerActor.PutMsg("Alice", "alice@gmail.com"), client);
        server.tell(new ContactServerActor.PutMsg("Bob", "bob@gmail.com"), client);

        // 4. Query the contacts
        server.tell(new ContactServerActor.GetMsg("Alice"), client);
        server.tell(new ContactServerActor.GetMsg("Bob"), client);
        server.tell(new ContactServerActor.GetMsg("Cici"), client);

        // 5. Wait for all messages to be sent and received
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        system.terminate();
    }

    // Actor behavior
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ContactServerActor.GetEmailResult.class, this::onGetEmailResult)
                .build();
    }

    // Handle reply from server
    void onGetEmailResult(ContactServerActor.GetEmailResult msg) {
        System.out.println("Client received email: " + msg.email);
    }
}