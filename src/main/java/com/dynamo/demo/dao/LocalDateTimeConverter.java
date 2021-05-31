package com.dynamo.demo.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

	private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public String convert(LocalDateTime object) {
		return object.format(formatter);
	}

	@Override
	public LocalDateTime unconvert(String object) {
		return LocalDateTime.parse(object, formatter);
	}

}
