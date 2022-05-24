package learn.lty.mq;

import learn.lty.mq.producers.RocketMqReporter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Optional;

/**
 * RocketMqReporter Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>5�� 24, 2022</pre>
 */
public class RocketMqReporterTest {
    private String servers;
    private String topic;

    @Before
    public void before() throws Exception {
        this.servers = "10.109.4.163:9876";
        this.topic = "lty_test";
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: report(String servers, String topic, Optional<String> tags, Optional<String> keys, String message)
     */
    @Test
    public void testReport() throws Exception {
        String msg = "this is a message.";
        RocketMqReporter.report(servers, topic, Optional.empty(), Optional.empty(), msg);
    }

    /**
     * Method: reportInTransaction(String servers, String topic, Optional<String> tags, Optional<String> keys, String message)
     */
    @Test
    public void testReportInTransaction() throws Exception {
        String msg = "this is a message.";
        RocketMqReporter.reportInTransaction(servers, topic, Optional.empty(), Optional.empty(), msg);
    }


} 
