package io.javaee.guardians.jms2lab;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class Jms2Test {

    @Inject
    private MessageReceiver messageReceiver;

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/MyQueue")
    private Queue myQueue;

    @Resource(lookup = "java:app/jms/MyQueue2")
    private Queue myQueue2;

    @Resource(lookup = "java:app/jms/RequestQueue")
    private Queue requestQueue;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "jms2lab.war")
                .addClass(MessageReceiver.class)
                .addClass(MyMessageListener.class)
                .addClass(RequestResponseListener.class)
                .addAsWebInfResource("web.xml", "web.xml");
    }

    @Test
    @InSequence(1)
    public void testSendStringMessage() {
        jmsContext.createProducer().send(myQueue, "message1");

        assertEquals("message1", messageReceiver.receiveStringMessage(myQueue));
    }

    @Test
    @InSequence(2)
    public void testReceiveStringMessage() {
        jmsContext.createProducer().send(myQueue, "message2");
        assertEquals("message2",
                jmsContext.createConsumer(myQueue).receiveBody(String.class));
    }

    @Test
    @InSequence(3)
    public void testSendObjectMessage() {
        Date now = new Date();
        jmsContext.createProducer().send(myQueue, now);
        assertEquals(now,
                jmsContext.createConsumer(myQueue).receiveBody(Date.class));
    }

    @Test
    @InSequence(4)
    public void testSendEfficientMessage() {
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

    @Test
    @InSequence(5)
    public void testSendMessageWithProperties() throws JMSException {
        jmsContext.createProducer()
                .setDisableMessageID(true)
                .setDisableMessageTimestamp(true)
                .setPriority(9)
                .setProperty("foo", "bar")
                .setProperty("foo2", "bar2")
                .send(myQueue, "message5");

        Message message = jmsContext.createConsumer(myQueue).receive();

        assertEquals(1, message.getIntProperty("JMSXDeliveryCount"));
        assertEquals("bar", message.getStringProperty("foo"));
        assertEquals("bar2", message.getStringProperty("foo2"));
        assertEquals("message5", message.getBody(String.class));
    }

    @Test
    @InSequence(6)
    public void testDeliveryDelay() throws InterruptedException, JMSException {
        long now = System.currentTimeMillis();
        jmsContext.createProducer()
                .setDeliveryDelay(5000)
                .send(myQueue, "message6");

        Message message = jmsContext.createConsumer(myQueue).receive();
        assertEquals("message6", message.getBody(String.class));
        assertTrue(message.getJMSDeliveryTime() >= (now + 5000));
    }

    @Test
    @InSequence(7)
    public void testMessageListener() throws InterruptedException {
        jmsContext.createProducer().send(myQueue2, "message7");
        Thread.sleep(1000);
        assertEquals("message7", MyMessageListener.getMessageText());
    }

    @Test
    @InSequence(8)
    public void testRequestResponse() throws JMSException {
        Queue responseQueue = jmsContext.createTemporaryQueue();
        Message request = jmsContext.createTextMessage("request");
        jmsContext.createProducer()
                .setJMSReplyTo(responseQueue)
                .send(requestQueue, request);

        Message response = jmsContext.createConsumer(responseQueue).receive();

        assertEquals("request", response.getBody(Map.class).get("request"));
        assertEquals("response", response.getBody(Map.class).get("response"));
        assertEquals(request.getJMSMessageID(), response.getJMSCorrelationID());
    }
}
