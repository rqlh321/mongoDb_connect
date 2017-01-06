package main.java;

import main.java.model.Message;
import main.java.model.Messages;
import org.mongodb.morphia.query.Query;

/**
 * Created by sic on 28.12.2016.
 */
public class DaoChat {

    public static void postMessage(Message message) {
        MongoDb.getInstance().save(message);
    }

    public static Messages getMessages() {
        Messages messages = new Messages();
        Query<Message> query = MongoDb.getInstance().createQuery(Message.class);
        messages.setMessages(query.asList());
        return messages;
    }
}
