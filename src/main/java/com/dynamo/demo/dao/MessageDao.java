package com.dynamo.demo.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dynamo.demo.domain.Message;

/**
 * Message Data Access Object.
 * 
 * @author Pierre
 */
public class MessageDao {

	private final DynamoDBMapper mapper;

	public MessageDao(AmazonDynamoDB client) {
		this.mapper = new DynamoDBMapper(client);
	}

	/**
	 * Update message.
	 * 
	 * @param message
	 * @return message
	 */
	public Message put(Message message) {
		this.mapper.save(message);
		return message;
	}

	/**
	 * Returns message.
	 * 
	 * @param author, hash key
	 * @return message
	 */
	public Message get(String author) {
		return mapper.load(Message.class, author);
	}

	/**
	 * Returns message.
	 * 
	 * @param id,      hash key
	 * @param created, sort key
	 * @return message
	 */
	public Message get(String id, LocalDateTime created) {
		return mapper.load(Message.class, id, created);
	}

	/**
	 * Delete message.
	 * 
	 * @param message
	 */
	public void delete(Message message) {
		Message toDelete = new Message();
		toDelete.setAuthor(message.getAuthor());
		toDelete.setCreated(message.getCreated());
		this.mapper.delete(toDelete);
	}

	/**
	 * Returns all messages for author.
	 * 
	 * @param author
	 * @return list of messages
	 */
	public List<Message> getAllForAuthor(String author) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":author", new AttributeValue().withS(author));

		DynamoDBQueryExpression<Message> queryExpression = new DynamoDBQueryExpression<Message>()
				.withKeyConditionExpression("author = :author").withExpressionAttributeValues(eav);

		return this.mapper.query(Message.class, queryExpression);
	}

	/**
	 * Returns all messages for author and year.
	 * 
	 * @param author
	 * @param year
	 * @return list of messages
	 */
	public List<Message> getAllForAuthorAndYear(String author, String year) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":author", new AttributeValue().withS(author));
		eav.put(":year", new AttributeValue().withS(year));

		DynamoDBQueryExpression<Message> queryExpression = new DynamoDBQueryExpression<Message>()
				.withKeyConditionExpression("author = :author AND begins_with(created, :year)")
				.withExpressionAttributeValues(eav);

		return this.mapper.query(Message.class, queryExpression);
	}

	/**
	 * Returns all messages.
	 * 
	 * @return list of messages
	 */
	public List<Message> getAll() {
		return this.mapper.scan(Message.class, new DynamoDBScanExpression());
	}
}
