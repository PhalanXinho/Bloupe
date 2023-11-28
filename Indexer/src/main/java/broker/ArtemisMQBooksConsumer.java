package broker;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ArtemisMQBooksConsumer implements BooksConsumer {

    private final MessageConsumer consumer;

    public ArtemisMQBooksConsumer() {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory)ic.lookup("ConnectionFactory");
            Queue orderQueue = (Queue)ic.lookup("queues/FileQueue");
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(orderQueue);
            connection.start();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String consume() {
        try {
            TextMessage textMessage = (TextMessage)consumer.receive();
            return textMessage.getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
