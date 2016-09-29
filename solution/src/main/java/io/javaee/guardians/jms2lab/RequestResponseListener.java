package io.javaee.guardians.jms2lab;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java:app/jms/RequestQueue")})
public class RequestResponseListener implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(
            RequestResponseListener.class.getName());

    @Inject
    private JMSContext jmsContext;

    @Override
    public void onMessage(Message message) {
        try {
            Map response = new HashMap(2);
            response.put("request", message.getBody(String.class));
            response.put("response", "response");

            jmsContext.createProducer()
                    .setJMSCorrelationID(message.getJMSMessageID())
                    .send(message.getJMSReplyTo(), response);
        } catch (JMSException e) {
            LOGGER.log(Level.SEVERE, "Error procesing JMS message", e);
        }
    }
}
