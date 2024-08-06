package org.personal.registration_service.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
class ToiletRegistControllerTests {

	@InjectMocks
	private ToiletRegistController target;

	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init(){
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void mockMvc가Null이아님() throws Exception {
		assertThat(target).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

}