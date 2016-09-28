package org.glassfish.jms2lab;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java:app/jms/MyQueue2")})
public class MyMessageListener implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(
            MyMessageListener.class.getName());

    private static String messageText = null;

    public static String getMessageText() {
        return messageText;
    }

    @Override
    public void onMessage(Message message) {
        try {
            messageText = message.getBody(String.class);
        } catch (JMSException e) {
            LOGGER.log(Level.SEVERE, "Error procesing JMS message", e);
        }
    }
}
