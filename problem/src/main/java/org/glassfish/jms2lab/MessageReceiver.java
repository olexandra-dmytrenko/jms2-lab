package org.glassfish.jms2lab;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@ApplicationScoped
public class MessageReceiver {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/MyQueue")
    private Queue myQueue;

    public String receiveStringMessage() {
        return jmsContext.createConsumer(myQueue).receiveBody(String.class);
    }
}
