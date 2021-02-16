
package igt.test.automation.util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Utility to handle the JMS messaging.
 *
 */

public class MessageSender {
    /**
     * jmsTemplate.
     */
    private JmsTemplate jmsTemplate;

    /**
     * Destination queue.
     */
    private Destination destination;

    /**
     * The Log4j logger.
     */
    private static final Logger LOG = LogManager
        .getLogger(MessageSender.class);

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(final Destination destination) {
        this.destination = destination;
    }

    /**
     * method to sendMessage to the queue configured.
     *
     * @param message the String message you want to send to the queue.
     */
    public void sendMessage(final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override public Message createMessage(final Session arg0) throws JMSException {
                return arg0.createTextMessage(message);
            }
        });
    }

    /**
     * method to sendMessage to the queue configured.
     *
     * @param inputFile the inputFile you want to send to the queue.
     * @throws IOException - this method throws IOException.
     */
    public void sendMessage(final File inputFile) throws IOException {
        jmsTemplate.send(destination, arg0 -> {
            try {
                String message = FileUtils.readFileToString(inputFile, Charset.defaultCharset());
                return arg0.createTextMessage(message);
            } catch (IOException e) {
                LOG.error(e);
            }
            return null;
        });
    }

}
