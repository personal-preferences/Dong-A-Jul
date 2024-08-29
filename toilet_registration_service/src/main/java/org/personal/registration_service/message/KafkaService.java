package org.personal.registration_service.message;

import java.util.UUID;

import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.KafkaRegistException;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletInformationRequest;
import org.personal.registration_service.request.ToiletLocationRequest;
import org.personal.registration_service.response.ToiletRegistKafkaAprroveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@EnableBinding(KafkaChannels.class)
public class KafkaService {

	private final String source = "registration-service";

	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

	private final ToiletRegistRepository toiletRegistRepository;
	private final MessageChannel toiletRegistrationLocation;
	private final MessageChannel toiletRegistrationInformation;


	@Autowired
	public KafkaService(ToiletRegistRepository toiletRegistRepository, KafkaChannels channels) {
		this.toiletRegistRepository = toiletRegistRepository;
		this.toiletRegistrationLocation = channels.toiletRegistrationLocation();
		this.toiletRegistrationInformation = channels.toiletRegistrationInformation();
	}

	public void sendToiletLocation(ToiletLocationRequest toiletLocationRequest) {

		Message<ToiletLocationRequest> message = MessageBuilder.withPayload(toiletLocationRequest)
			.setHeader("source", source)
			.build();
		boolean sent = toiletRegistrationLocation.send(message);

		if (sent) {
			logger.info("화장실 등록 전송 성공: {}", toiletLocationRequest);
		} else {
			logger.error("화장실 등록 전송 실패: {}", toiletLocationRequest);
			throw new KafkaRegistException(ToiletRegistErrorResult.KAFKA_SEND_FAILED);
		}
	}

	@StreamListener(KafkaChannels.CHECK_REGISTRATION_APPROVE)
	public void sendToiletInformation(
		@Payload ToiletRegistKafkaAprroveResponse response,
		@Header(value = "source", required = false) String responseSource,
		@Header(value = "id", required = false) String id) {

		logger.info("수신된 화장실 정보: {}, Source: {}, RequestId: {}", response, responseSource, id);

		Long toiletRegistId = response.toiletRegistId();
		Long toiletLocationId = response.toiletLocationId();

		ToiletRegist toiletRegist = toiletRegistRepository.findByToiletRegistId(toiletRegistId)
			.orElseThrow(() -> new KafkaRegistException(ToiletRegistErrorResult.KAFKA_ENTITY_NOT_FOUND));

		ToiletInformationRequest toiletInformationRequest = ToiletInformationRequest.of(toiletLocationId, toiletRegist);

		Message<ToiletInformationRequest> message = MessageBuilder.withPayload(toiletInformationRequest)
			.setHeader("source", source)
			.build();

		boolean sent = toiletRegistrationInformation.send(message);

		if (sent) {
			logger.info("화장실 정보 전송 성공: {}", toiletInformationRequest);
		} else {
			logger.error("화장실 정보 전송 실패: {}", toiletInformationRequest);
			throw new KafkaRegistException(ToiletRegistErrorResult.KAFKA_SEND_FAILED);
		}
	}
}
