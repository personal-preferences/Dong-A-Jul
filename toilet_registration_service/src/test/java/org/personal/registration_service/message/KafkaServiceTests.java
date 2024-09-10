package org.personal.registration_service.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.KafkaRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletLocationRequest;
import org.personal.registration_service.response.ToiletRegistKafkaAprroveResponse;
import org.springframework.messaging.MessageChannel;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class KafkaServiceTests {

	@Mock
	private ToiletRegistRepository toiletRegistRepository;

	@Mock
	private KafkaChannels kafkaChannels;

	@Mock
	private MessageChannel toiletRegistrationLocation;

	@Mock
	private MessageChannel toiletRegistrationInformation;

	@InjectMocks
	private KafkaService kafkaService;

	@BeforeEach
	void setUp() {
		when(kafkaChannels.toiletRegistrationLocation()).thenReturn(toiletRegistrationLocation);
		when(kafkaChannels.toiletRegistrationInformation()).thenReturn(toiletRegistrationInformation);
		kafkaService = new KafkaService(toiletRegistRepository, kafkaChannels);
	}

	@Test
	void sendToiletLocation_success() {
		ToiletLocationRequest request = new ToiletLocationRequest(1L, "Toilet A", "Description", "123 Street", 37.5665, 126.9780);
		when(toiletRegistrationLocation.send(any())).thenReturn(true);

		kafkaService.sendToiletLocation(request);

		verify(toiletRegistrationLocation, times(1)).send(any());
	}

	@Test
	void sendToiletLocation_failure() {
		ToiletLocationRequest request = new ToiletLocationRequest(1L, "Toilet B", "Description", "124 Street", 37.5665, 126.9781);
		when(toiletRegistrationLocation.send(any())).thenReturn(false);

		try {
			kafkaService.sendToiletLocation(request);
		} catch (KafkaRegistException e) {
			verify(toiletRegistrationLocation, times(1)).send(any());
		}
	}

	@Test
	void sendToiletInformation_success() {
		ToiletRegistKafkaAprroveResponse response = new ToiletRegistKafkaAprroveResponse(1L, 2L);
		ToiletRegist toiletRegist = new ToiletRegist();
		when(toiletRegistRepository.findByToiletRegistId(1L)).thenReturn(Optional.of(toiletRegist));
		when(toiletRegistrationInformation.send(any())).thenReturn(true);

		kafkaService.sendToiletInformation(response, "source", "id");

		verify(toiletRegistRepository, times(1)).findByToiletRegistId(1L);
		verify(toiletRegistrationInformation, times(1)).send(any());
	}

	@Test
	void sendToiletInformation_failure() {
		ToiletRegistKafkaAprroveResponse response = new ToiletRegistKafkaAprroveResponse(1L, 2L);
		ToiletRegist toiletRegist = new ToiletRegist();
		when(toiletRegistRepository.findByToiletRegistId(1L)).thenReturn(Optional.of(toiletRegist));
		when(toiletRegistrationInformation.send(any())).thenReturn(false);

		try {
			kafkaService.sendToiletInformation(response, "source", "id");
		} catch (KafkaRegistException e) {
			verify(toiletRegistRepository, times(1)).findByToiletRegistId(1L);
			verify(toiletRegistrationInformation, times(1)).send(any());
		}
	}

	@Test
	void sendToiletInformation_entityNotFound() {
		ToiletRegistKafkaAprroveResponse response = new ToiletRegistKafkaAprroveResponse(1L, 2L);
		when(toiletRegistRepository.findByToiletRegistId(1L)).thenReturn(Optional.empty());

		try {
			kafkaService.sendToiletInformation(response, "source", "id");
		} catch (KafkaRegistException e) {
			verify(toiletRegistRepository, times(1)).findByToiletRegistId(1L);
		}
	}
}
