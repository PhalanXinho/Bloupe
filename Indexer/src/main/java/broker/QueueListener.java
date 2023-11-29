package broker;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage)message;
        try {
            System.out.println("Received message, starting indexing file: " + textMessage.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        //TODO
        //indexer.index(message);

    }
}
