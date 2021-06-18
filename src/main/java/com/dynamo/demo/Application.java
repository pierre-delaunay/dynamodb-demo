package com.dynamo.demo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.dynamo.demo.dao.MessageDao;
import com.dynamo.demo.dao.UserDao;
import com.dynamo.demo.domain.Message;
import com.dynamo.demo.domain.User;

/**
 * Application Main Class.
 * 
 * @author Pierre
 *
 */
public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

	public static void main(String[] args) {
		// userCrudOperations();
		messageCrudOperations();
	}

	private static void userCrudOperations() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_3).build();
		UserDao userDao = new UserDao(client);

		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("example@johndoe.com");

		// Save the item
		userDao.put(user);

		// Fetch the item
		User retrieved = userDao.get(user.getId());
		LOGGER.info("User retrieved : ");
		LOGGER.info(retrieved.toString());

		// Update existing item
		retrieved.setFirstName("Nick");
		userDao.put(retrieved);

		// Read the updated item
		User updatedUser = userDao.get(user.getId());
		LOGGER.info("Retrieved the previously updated item : ");
		LOGGER.info(updatedUser.toString());

		// Delete the item
		userDao.delete(updatedUser);

		// Try to retrieve the deleted item
		User deletedUser = userDao.get(user.getId());
		if (deletedUser == null) {
			LOGGER.info("Done - Sample user deleted");
		}
	}

	private static void messageCrudOperations() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_3).build();
		MessageDao messageDao = new MessageDao(client);

		Message message = new Message();
		message.setAuthor("john.doe");
		message.setContent("myNewContent");
		message.setSeverity("high");
		message.setCreated(LocalDateTime.of(2020, 06, 30, 20, 10)); // LocalDateTime.now()

		// Save the message
		messageDao.put(message);

		// Fetch the item
		Message retrieved = messageDao.get(message.getAuthor(), message.getCreated());
		LOGGER.info("Message retrieved : ");
		LOGGER.info(retrieved.toString());

		List<Message> messages = messageDao.getAllForAuthorAndYear("john.doe", "2020");
		LOGGER.info(messages.size() + " messages retrieved");
		messages.forEach(m -> {
			LOGGER.info(m.toString());
		});

		List<Message> messagesBySeverity = messageDao.getAllForAuthorAndSeverity("john.doe", "high");
		LOGGER.info(messagesBySeverity.size() + " messages retrieved");
		messagesBySeverity.forEach(m -> {
			LOGGER.info(m.toString());
		});
	}

}
