package com.service1.dto;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDetailsSerializer implements Serializer<UserDetails>{

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String topic, UserDetails data) {
		byte[] retVal = null;
		final ObjectMapper mapper = new ObjectMapper();

		try {
			retVal = mapper.writeValueAsString(data).getBytes();
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return retVal;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
