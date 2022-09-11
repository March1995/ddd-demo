package ltd.colingting.external.service.impl;

import ltd.colingting.domain.domain.types.AuditMessage;
import ltd.colingting.domain.messaging.AuditMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 丁浩
 * @date 2022年08月19日 14:18
 */
@Service
public class AuditMessageProducerImpl implements AuditMessageProducer {

    private static final String TOPIC_AUDIT_LOG = "TOPIC_AUDIT_LOG";



    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AuditMessageProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(AuditMessage message) {
        String messageBody = message.serialize();
        kafkaTemplate.send(TOPIC_AUDIT_LOG, messageBody);

//        return SendResult.success();
    }
}
