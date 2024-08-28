package org.personal.registration_service.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface KafkaChannels {

	String CHECK_REGISTRATION_APPROVE = "checkRegistrationApprove";
	String TOILET_REGISTRATION_INFORMATION = "toiletRegistrationInformation";
	String OUTPUT_LOCATION = "toiletRegistrationLocation";

	@Input(CHECK_REGISTRATION_APPROVE)
	SubscribableChannel checkRegistrationApprove();

	@Output(TOILET_REGISTRATION_INFORMATION)
	MessageChannel toiletRegistrationInformation();

	@Output(OUTPUT_LOCATION)
	MessageChannel toiletRegistrationLocation();
}
