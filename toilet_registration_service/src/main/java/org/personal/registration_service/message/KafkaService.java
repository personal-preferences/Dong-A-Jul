package org.personal.registration_service.message;

import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.KafkaRegistException;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletInformation;
import org.personal.registration_service.request.ToiletLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@EnableBinding(KafkaChannels.class)
public class KafkaService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

	private final ToiletRegistRepository toiletRegistRepository;
	private final MessageChannel toiletRegistrationLocation;

	@Autowired
	public KafkaService(ToiletRegistRepository toiletRegistRepository, KafkaChannels channels) {
		this.toiletRegistRepository = toiletRegistRepository;
		this.toiletRegistrationLocation = channels.toiletRegistrationLocation();
	}

	public void sendToiletLocation(ToiletLocation toiletLocation) {
		Message<ToiletLocation> message = MessageBuilder.withPayload(toiletLocation).build();
		boolean sent = toiletRegistrationLocation.send(message);

		if (sent) {
			logger.info("화장실 등록 전송 성공: {}", toiletLocation);
		} else {
			logger.error("화장실 등록 전송 실패: {}", toiletLocation);
			throw new KafkaRegistException(ToiletRegistErrorResult.KAFKA_SEND_FAILED);
		}
	}

	@StreamListener(KafkaChannels.CHECK_REGISTRATION_APPROVE)
	@SendTo(KafkaChannels.TOILET_REGISTRATION_INFORMATION)
	public ToiletInformation sendToiletInformation(Long toiletRegistId, Long toiletLocationId) {
		ToiletRegist toiletRegist = toiletRegistRepository.findByToiletRegistId(toiletRegistId)
			.orElseThrow(() -> new KafkaRegistException(ToiletRegistErrorResult.KAFKA_ENTITY_NOT_FOUND));

		return ToiletInformation.of(toiletLocationId, toiletRegist);
	}
}
