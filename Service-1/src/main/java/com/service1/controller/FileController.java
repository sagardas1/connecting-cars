package com.service1.controller;

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

import com.service1.dto.UserDetails;
import com.service1.service.UserDetailService;

@RestController
public class FileController {

	@Autowired
	UserDetailService userDetailService;

	@GetMapping("/test")
	public String test() {
		return "Request coming in Service1";
	}

	@PostMapping(value = "/save", headers = "Accept=application/json")
	public String saveUserDetails(@RequestBody UserDetails user, @RequestHeader("fileType") String fileType,
			@RequestHeader(value = "file", required = false) String file) {
		userDetailService.saveUserDetails(user, fileType, file);

		return "not";

	}

	@GetMapping("/readFile")
	public Map<String, Object> readFile(@RequestParam(value = "file") String file,
			@RequestHeader(value = "fileType") String fileType) throws IOException, JAXBException {
		return userDetailService.read(file, fileType);

	}

	@PostMapping(value = "/getval", headers = "Accept=application/json")
	public String getval(@RequestBody UserDetails details, @RequestParam(value = "name") String name) {
		return "done";
	}
}
