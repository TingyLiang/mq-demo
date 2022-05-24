package learn.lty.mq.producers;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * @author liangty1
 */
@Slf4j
public class RocketMqReporter {
    public static void report(String servers, String topic, Optional<String> tags, Optional<String> keys, String message) {
        DefaultMQProducer producer = new DefaultMQProducer("dgp_sql_lineage_collector");
        log.info("Going to send message to servers:{}, topic:{},  message:{}", servers, topic, message);
        producer.setNamesrvAddr(servers);
        try {
            producer.start();
            Message msg = new Message(topic, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
            tags.ifPresent(msg::setTags);
            keys.ifPresent(msg::setKeys);
            producer.sendOneway(msg);
        } catch (MQClientException | UnsupportedEncodingException | RemotingException | InterruptedException e) {
            log.error("Error while sending message to rokectMq, servers:{}, topic:{}, reason:{}", servers, topic, e.getMessage());
        } finally {
            producer.shutdown();
        }
    }

    public static void reportInTransaction(String servers, String topic, Optional<String> tags, Optional<String> keys, String message) {
        TransactionMQProducer producer = new TransactionMQProducer("dgp_sql_lineage_collector");
        producer.setNamesrvAddr(servers);
        try {
            producer.start();
            Message msg = new Message(topic, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionListener transactionListener = new TransactionListenerImpl();
            producer.setTransactionListener(transactionListener);
            tags.ifPresent(msg::setTags);
            keys.ifPresent(msg::setKeys);
            log.info("send {}", msg);
            TransactionSendResult ret = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", ret);
        } catch (MQClientException | UnsupportedEncodingException e) {
            log.error("Error while sending message to rokectMq, servers:{}, topic:{}, reason:{}", servers, topic, e.getMessage());
        } finally {
            producer.shutdown();
        }
    }

}

@Slf4j
class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.debug(" exec" + message + o.toString());
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.debug(" check" + messageExt);
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}


class RocketMQAsyncSendCallback implements SendCallback {
    private static final Logger log = LoggerFactory.getLogger(RocketMQAsyncSendCallback.class);
    private String message;

    public RocketMQAsyncSendCallback(String message) {
        this.message = message;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        if (log.isDebugEnabled()) {
            log.debug("Message has been send successfully, result {}", sendResult);
        }
    }

    @Override
    public void onException(Throwable e) {
        log.error("Failed send message, content:{}, reason:{}", message, e.getMessage());

    }
}


