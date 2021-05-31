package com.dynamo.demo.dao;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.dynamo.demo.domain.User;

/**
 * User Data Access Object.
 * 
 * @author Pierre
 */
public class UserDao {

	private final DynamoDBMapper mapper;

	public UserDao(AmazonDynamoDB client) {
		this.mapper = new DynamoDBMapper(client);
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 * @return user
	 */
	public User put(User user) {
		this.mapper.save(user);
		return user;
	}

	/**
	 * Returns user.
	 * 
	 * @param id
	 * @return user
	 */
	public User get(String id) {
		return mapper.load(User.class, id);
	}

	/**
	 * Returns user using consistent read.
	 * 
	 * @param id
	 * @return user
	 */
	public User getWithConsistentRead(String id) {
		DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
				.withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT).build();
		return mapper.load(User.class, id, config);
	}

	/**
	 * Delete user.
	 * 
	 * @param user
	 */
	public void delete(User user) {
		User toDelete = new User();
		toDelete.setId(user.getId());
		this.mapper.delete(toDelete);
	}

	/**
	 * Returns all users.
	 * 
	 * @return list of users
	 */
	public List<User> getAll() {
		return this.mapper.scan(User.class, new DynamoDBScanExpression());
	}
}
