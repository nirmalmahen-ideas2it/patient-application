package com.ideas2it.training.patient.publish;

import com.ideas2it.training.patient.config.RabbitMQConfig;
import com.ideas2it.training.patient.dto.PatientInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for publishing PatientInfo messages to RabbitMQ.
 *
 * <p>This class is responsible for sending patient information to the configured RabbitMQ
 * exchange and routing key. It uses the {@link RabbitTemplate} to handle the message
 * publishing process.</p>
 * <p>
 * Author: Alagu Nirmal Mahendran
 * CreatedOn: 2023-10-05
 */
@Service
@RequiredArgsConstructor
public class PatientInfoPublisher {

    /**
     * RabbitTemplate for interacting with RabbitMQ.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Publishes the given PatientInfo to RabbitMQ.
     *
     * <p>This method sends the provided {@link PatientInfo} object to the RabbitMQ
     * exchange and routing key defined in {@link RabbitMQConfig}.</p>
     *
     * @param patientInfo the PatientInfo object to publish
     */
    public void sendPatientInfo(PatientInfo patientInfo) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                patientInfo
        );
    }
}