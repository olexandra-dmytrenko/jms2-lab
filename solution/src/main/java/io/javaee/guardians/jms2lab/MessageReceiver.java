package io.javaee.guardians.jms2lab;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

@ApplicationScoped
public class MessageReceiver {

    @Inject
    private JMSContext jmsContext;

    public String receiveStringMessage(Destination destination) {
        return jmsContext.createConsumer(destination).receiveBody(String.class);
    }
}
