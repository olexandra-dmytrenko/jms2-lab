package org.glassfish.jms2lab;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * In the couse of the lab, you will be completing the unit tests below using
 * the comments provided. The tests are designed to be progressively more
 * challenging. There is no need to finish all the tests in the time that we
 * have. You can always finish it on your own or simply look at the solutions
 * provided. In fact, feel free to look at the solution at any time if
 * absolutely stuck. To get the most out of the lab though, it's best to try
 * to finish the tests on your own.
 *
 * The tests use Arquillian to run your code against a running
 * WildFly instance. The instance is assumed to be running on localhost,
 * ports 8080/9990. For the purposes of this lab, you won't need to know much
 * about Arquillian - everything has already been setup for you. Once you
 * have WildFly running, there's no need to stop/start it during the lab.
 */
@RunWith(Arquillian.class)
public class Jms2Test {

    @Inject
    private MessageReceiver messageReceiver;

    @Inject
    private JMSContext jmsContext;

    // These are the three queues we will be using for our tests. They
    // have already been setup for you in the web.xml for the test.
    @Resource(lookup = "java:app/jms/MyQueue")
    private Queue myQueue;

    @Resource(lookup = "java:app/jms/MyQueue2")
    private Queue myQueue2;

    @Resource(lookup = "java:app/jms/RequestQueue")
    private Queue requestQueue;

    /*
     * This factory method defines the actual test artifacts we are deploying
     * to WildFly for testing. As you add test artifacts, make sure to
     * list them here, typically using the addClass method.
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "jms2lab.war")
                .addClass(MessageReceiver.class)
                .addClass(MyMessageListener.class)
                .addAsWebInfResource("web.xml", "web.xml");
    }

    /*
     * In this very simple test, you will be sending a simple string
     * message to the injected queue 'myQueue' using all the defaults.
     *
     * To complete the test, the assertion (commented out) utilizes a message
     * receiver utility that's been provided for you. In the next test, you
     * will not use this utility but write your own message receive code.
     *
     * Hint: You could borrow the code in the receiver utility for the next
     * test :-).
     */
    @Test
    @InSequence(1)
    public void testSendStringMessage() {
        // Write the code to send the message here.

        // assertEquals("message1",
        // messageReceiver.receiveStringMessage(myQueue));
    }

    /*
     * In this very simple test, you will be sending a simple string
     * message to the injected queue 'myQueue' using all the defaults and then
     * receiving it.
     */
    @Test
    @InSequence(2)
    public void testReceiveStringMessage() {
        // Write the code to send and then receive the message here.
        // Assert that you received the same message that you sent.
    }

    /*
     * In this test, you will be sending a serializable object message to the
     * injected queue 'myQueue' and then receiving it.
     */
    @Test
    @InSequence(3)
    public void testSendObjectMessage() {
        // Your code here.
    }

    /*
     * In this test, you will be sending a message to the injected queue
     * 'myQueue' and then receiving it. Try to make the message as efficient as
     * possible using message and producer level options. There isn't a right
     * or wrong answer here, so feel free to explore and be creative. Do
     * whatever you think will reduce overhead and system resource usage. Lost
     * messages are OK.
     */
    @Test
    @InSequence(4)
    public void testSendEfficientMessage() {
        // Your code here.
    }

    /*
     * In this test, you will send and receive a message with custom and
     * built-in/provider-supplied properties using the injected queue
     * 'myQueue'.
     *
     * Hint: In this case you will need to receive the actual JMS message as
     * opposed to just the body payload. For the send, there's still no need
     * to create a JMS message, although you could if you want.
     */
    @Test
    @InSequence(5)
    public void testSendMessageWithProperties() {
        // Your code here. Make sure to assert properties as well as the
        // payload.
    }

    /*
     * In this test, you will send and receive a message using a delivery
     * delay with the injected queue 'myQueue'. You'll have to be creative
     * in figuring out how to assert that the delay worked properly.
     *
     * Hint: Look at the information that you get with the JMS message that
     * the provider sets for you.
     */
    @Test
    @InSequence(6)
    public void testDeliveryDelay() {
        // Your code here.
    }

    /*
     * In this test, you will send a message that will be received by a
     * JMS MDB. You will use the injected queue 'myQueue2'. The MDB,
     * MyMessageListener, has already been written for you but you should take
     * time to explore it. In the next test, you'll need to write an MDB
     * yourself.
     *
     * Hint: Remember to account for the fact that the MDB is running in a
     * separate thread from the test.
     */
    @Test
    @InSequence(7)
    public void testMessageListener() {
        // Your code here.
        assertEquals("message7", MyMessageListener.getMessageText());
    }

    /*
     * In this test, you'll utilize the request/response paradigm in JMS. In
     * this paradigm, you send a message to a request queue and wait for a
     * response. When you send the request, you set a "reply to" queue that the
     * response will be sent to. This reply to queue is often a temporary queue
     * alive only for the session. When the recepient receives the request,
     * in addition to using the reply to queue to send a response, they must
     * also set the correlation ID. The correlation ID is set to the message
     * ID of the request. You must receive the response message and assert that
     * the request/response paradigm was implemented correctly. You must
     * implement the recepient as an MDB. Use the injected queue 'requestQueue'
     * as the request queue.
     */
    @Test
    @InSequence(8)
    public void testRequestResponse() throws JMSException {
        // Your code here.
    }
}
