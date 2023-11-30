package broker;

import datalakeuploader.GoogleCloudDataLakeBookUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class ArtemisMQProducer implements StringProducer {

    private final Session session;
    private final MessageProducer producer;

    Logger logger = LoggerFactory.getLogger(ArtemisMQProducer.class);


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
        logger.info("Producing new message \"" + s + "\" into the ActiveMQ...");

        try {
            TextMessage message = session.createTextMessage(s);
            producer.send(message);
            logger.info("Message successfully produced");

        } catch (JMSException e) {
            logger.info(e.getMessage());
            logger.info("There was an error while producing message");
            throw new RuntimeException(e);
        }
    }
}
