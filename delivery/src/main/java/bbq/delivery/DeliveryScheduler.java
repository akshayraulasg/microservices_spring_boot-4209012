package bbq.delivery;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bbq.delivery.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryScheduler {

    private final DeliveryRepository deliveryRepository;

    private final KafkaTemplate kafkaTemplate;

    @Scheduled(fixedRateString = "PT10S")
    public void scheduleFixedRateTask() {
        log.info("Sending delivery updates at {}", LocalDateTime.now());
        deliveryRepository.getAll().forEach(this::process);
    }

    private void process(Delivery delivery) {
        // 1. Advance status
        delivery.nextStatus();

        // 2. Publish update to Topic
        kafkaTemplate.send("delivery_updates", delivery);
    }

}
