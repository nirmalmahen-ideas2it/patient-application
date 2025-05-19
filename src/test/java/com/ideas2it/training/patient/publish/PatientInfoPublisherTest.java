package com.ideas2it.training.patient.publish;

import com.ideas2it.training.patient.config.RabbitMQConfig;
import com.ideas2it.training.patient.dto.PatientInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PatientInfoPublisherTest {

    private PatientInfoPublisher patientInfoPublisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientInfoPublisher = new PatientInfoPublisher(rabbitTemplate);
    }

    @Test
    void testSendPatientInfoWithValidData() {
        // Arrange
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(1L);
        patientInfo.setFirstName("John");
        patientInfo.setLastName("Doe");

        // Act
        patientInfoPublisher.sendPatientInfo(patientInfo);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                patientInfo
        );
    }

    @Test
    void testSendPatientInfoWithNullData() {
        // Arrange
        PatientInfo patientInfo = null;
        // Act
        patientInfoPublisher.sendPatientInfo(patientInfo);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                patientInfo
        );
    }
}
