package org.glassfish.jms2lab;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class Jms2Test {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/MyConnectionFactory")
    private ConnectionFactory myConnectionFactory;
    
    @Resource(lookup = "java:app/jms/MyQueue")
    private Queue myQueue;    

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "jms2lab.war")
                .addAsWebInfResource("web.xml", "web.xml");
    }

    @Test
    @InSequence(1)
    public void testSendMessage() {
        assertNotNull(myConnectionFactory);        
        assertNotNull(myQueue);
    }
}
