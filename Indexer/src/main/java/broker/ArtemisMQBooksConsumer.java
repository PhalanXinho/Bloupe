package broker;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ArtemisMQBooksConsumer implements BooksConsumer {

    private final MessageConsumer consumer;
    private final QueueBrowser browser;

    public ArtemisMQBooksConsumer() {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("ConnectionFactory");
            Queue fileQueue = (Queue) ic.lookup("queues/FileQueue");
            Connection connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            browser = session.createBrowser(fileQueue);
            consumer = session.createConsumer(fileQueue);

        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String consume() {
        try {
            if (browser.getEnumeration().hasMoreElements()) {
                Message message = (Message) browser.getEnumeration().nextElement();
                return ((TextMessage) message).getText();
            }
            Message message = consumer.receive();
            return ((TextMessage) message).getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
