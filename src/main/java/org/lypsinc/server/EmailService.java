
package org.lypsinc.server;

import static spark.Spark.post;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A server set up using Spark (https://sparkjava.com) to count unique email
 * addresses in a list per Gmail account matching rules.
 * 
 * @author jarrett
 *
 */
public class EmailService {

	private static final int HTTP_BAD_REQUEST = 400;

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(EmailService.class);
		post("/emails", (request, response) -> {
			try {
				final ObjectMapper mapper = new ObjectMapper();
				@SuppressWarnings("unchecked")
				final List<String> emails = mapper.readValue(request.body(), List.class);
				logger.debug(String.format("The list is %s of size %d", emails, emails.size()));

				final EmailParser parser = new EmailParser();
				final Integer uniqueEmails = parser.count(emails);

				response.status(200);
				response.type("application/json");
				logger.debug("Number of emails found {}", uniqueEmails);
				return uniqueEmails;
			} catch (JsonParseException jpe) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});
	}
}