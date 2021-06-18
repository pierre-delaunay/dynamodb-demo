package com.dynamo.demo.domain;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.dynamo.demo.dao.LocalDateTimeConverter;

/**
 * Message Domain Class.
 * 
 * @author Pierre
 *
 */
@DynamoDBTable(tableName = "MessageStore")
public class Message {

	private String content;
	private LocalDateTime created;
	private String author;
	private String severity;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@DynamoDBRangeKey
	@DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@DynamoDBHashKey
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", created=" + created + ", author=" + author + "]";
	}

}
