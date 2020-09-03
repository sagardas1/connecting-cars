package com.service1.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.service1.dto.UserDetails;
import com.service1.utils.EncryptDycryptConfig;

@Service
public class UserDetailService {

	private static final String TOPIC = "test";

	@Autowired
	private KafkaTemplate<String, String> kafkaTEmplate;

	@Autowired
	RestTemplate getRestTemplate;
	@Autowired
	EncryptDycryptConfig encryptDycryptConfig;

	public String saveUserDetails(UserDetails userDetails, String fileType, String file) {

		final JsonObject json = new JsonObject();

		json.addProperty("fileType", fileType);
		json.addProperty("file", file);
		json.addProperty("name", userDetails.getName());
		json.addProperty("dob", userDetails.getDob());
		json.addProperty("salary", userDetails.getSalary());
		json.addProperty("age", userDetails.getAge());

		String data = json.toString();

		String encryptedData = encryptDycryptConfig.encrypt(data);
		System.out.println(encryptedData);
		sendMessage(encryptedData);
		return "Created";
	}

	public void sendMessage(String message) {
		System.out.println("In kafka send message method");
		kafkaTEmplate.send(TOPIC, message);
	}

	public KafkaProducer<String, UserDetails> getKafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		return new KafkaProducer<String, UserDetails>(props, new StringSerializer(), new UserDetailsSerializer());
	}

	public void sendMessage(UserDetails user) {
		getKafkaProducer().send(new ProducerRecord<String, UserDetails>(TOPIC, user));

	}

	// from service2

	@KafkaListener(topics = "READDATA", groupId = "group_id")
	public Map<String, Object> consume(String data) throws JsonParseException, JsonMappingException, IOException {
		String dycryptedData = encryptDycryptConfig.decrypt(data);

		ObjectMapper mapper = new ObjectMapper();

		// convert JSON string to Map
		Map<String, Object> map = mapper.readValue(new Gson().toJson(dycryptedData), Map.class);

		return map;
	}

	public Map<String, Object> read(String file, String fileType)
			throws JsonParseException, JsonMappingException, IOException {

		String url = "http://localhost:8084/readFile?file=" + file;

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<>("{fileType:" + fileType + "}", headers);

		ResponseEntity<String> a = getRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		a.toString();

		String data = encryptDycryptConfig.decrypt(a.toString());
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(data, Map.class);

		return map;
	}

}
