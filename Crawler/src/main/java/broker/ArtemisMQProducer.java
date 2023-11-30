package broker;

import javax.jms.*;
import javax.naming.InitialContext;

public class ArtemisMQProducer implements StringProducer {

    private final Session session;
    private final MessageProducer producer;

    public ArtemisMQProducer() {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) ic.lookup("ConnectionFactory");
            Queue orderQueue = (Queue) ic.lookup("queues/FileQueue");
            Connection connection = cf.createConnection();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.producer = session.createProducer(orderQueue);
            connection.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void produce(String s) {
        try {
            TextMessage message = session.createTextMessage(s);
            producer.send(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
