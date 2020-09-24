package com.service2.controller;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service2.service.UserService2;
import com.service2.utils.DataConversion;

@RestController
public class FileController {
	@Autowired
	DataConversion dataConversion;

	@Autowired
	UserService2 userService2;

	@PostMapping("/writeFile")
	public String test(@RequestBody Map<String, Object> data, @RequestHeader(value = "file") String file,
			@RequestHeader(value = "fileType") String fileType) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		String json = objectMapper.writeValueAsString(data);

		userService2.consume(json);
		return "success";
		// return dataConversion.convertJSONToCSV(data);
	}
	

	@GetMapping("/readFile")
	public String readFile(@RequestParam(value = "file") String file,
			@RequestHeader(value = "fileType") String fileType) throws IOException, JAXBException {
		return userService2.readFile(file, fileType);

	}

}
