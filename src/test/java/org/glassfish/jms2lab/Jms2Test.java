package org.glassfish.jms2lab;

import java.util.Date;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.Queue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class Jms2Test {

    @Inject
    private MessageReceiver messageReceiver;

    @Resource(lookup = "java:app/jms/MyConnectionFactory")
    private ConnectionFactory myConnectionFactory;

    @Resource(lookup = "java:app/jms/MyQueue")
    private Queue myQueue;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "jms2lab.war")
                .addClass(MessageReceiver.class)
                .addAsWebInfResource("web.xml", "web.xml");
    }

    @Test
    @InSequence(1)
    public void testSendStringMessage() {
        // We are using a try with resources here, but in a Java EE
        // environment we can directly inject a managed JMS context.
        try (JMSContext jmsContext = myConnectionFactory.createContext()) {
            jmsContext.createProducer().send(myQueue, "message1");
        }

        assertEquals("message1", messageReceiver.receiveStringMessage());
    }

    @Test
    @InSequence(2)
    public void testReceiveStringMessage() {
        // We are using a try with resources here, but in a Java EE
        // environment we can directly inject a managed JMS context.
        try (JMSContext jmsContext = myConnectionFactory.createContext()) {
            jmsContext.createProducer().send(myQueue, "message2");
            assertEquals("message2",
                    jmsContext.createConsumer(myQueue).receiveBody(String.class));
        }
    }

    @Test
    @InSequence(3)
    public void testSendObjectMessage() {
        Date now = new Date();

        // We are using a try with resources here, but in a Java EE
        // environment we can directly inject a managed JMS context.
        try (JMSContext jmsContext = myConnectionFactory.createContext()) {
            jmsContext.createProducer().send(myQueue, now);
            assertEquals(now,
                    jmsContext.createConsumer(myQueue).receiveBody(Date.class));
        }

    }

    @Test
    @InSequence(4)
    public void testSendEfficientMessage() {
        // We are using a try with resources here, but in a Java EE
        // environment we can directly inject a managed JMS context.
        try (JMSContext jmsContext = myConnectionFactory.createContext()) {
            jmsContext.createProducer()
                    .setDeliveryMode(DeliveryMode.NON_PERSISTENT)
                    .setDisableMessageID(true)
                    .setDisableMessageTimestamp(true)
                    .setPriority(0)
                    .setTimeToLive(1000)
                    .send(myQueue, new byte[]{0x4f, 0x03});
            assertArrayEquals(new byte[]{0x4f, 0x03},
                    jmsContext.createConsumer(myQueue).receiveBody(byte[].class));
        }
    }
}
