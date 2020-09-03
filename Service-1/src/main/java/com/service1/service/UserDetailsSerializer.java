package com.service1.service;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.service1.dto.UserDetails;

public class UserDetailsSerializer implements Serializer<UserDetails> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] serialize(String topic, UserDetails data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
