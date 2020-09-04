package com.service2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service2.utils.DataConversion;
import com.service2.utils.EncryptDycryptConfig;

@Service
public class UserService2 {
	@Autowired
	EncryptDycryptConfig encryptDycryptConfig;

	@Autowired
	DataConversion dataConversion;

	private static final String TOPIC = "READDATA";

	@Autowired
	private KafkaTemplate<String, String> kafkaTEmplate;

	@KafkaListener(topics = "test", groupId = "group_id")
	public void consume(String encryptedData) throws IOException {

		@SuppressWarnings("unused")
		String dycryptedData = encryptDycryptConfig.decrypt(encryptedData);

		@SuppressWarnings("unchecked")
		HashMap<String, Object> results = new ObjectMapper().readValue(encryptedData, HashMap.class);

		String fileType = (String) results.get("fileType");
		results.remove("fileType");
		// Map<String, Object> data=(Map<String, Object>) user;

		if (fileType.toLowerCase().equals("csv")) {
			dataConversion.convertJSONToCSV(results);
		} else {
			dataConversion.convertJSONToXML(results);
		}

	}

	public String readFile(String file, String fileType) throws IOException, JAXBException {
		Map<String, Object> map = null;
		if (fileType.toLowerCase().equals("csv")) {

			map = dataConversion.covertCsvtoObj(file);
		} else {
			map = dataConversion.covertXMLtoObj(file);
		}

		map.toString();
		String en = encryptDycryptConfig.encrypt(map.toString());
		System.out.println(en);
		sendMessage(en);
		return en;
	}

	public void sendMessage(String message) {
		kafkaTEmplate.send(TOPIC, message);
	}
}
