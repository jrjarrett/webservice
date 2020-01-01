/**
 * 
 */
package org.lypsinc.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the EmailParser class.
 * 
 * @author jarrett
 *
 */
class EmailParserTest {

	EmailParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		parser = new EmailParser();
	}

	/**
	 * An empty list of email addresses should return zero.
	 */
	@Test
	void emptyListReturnsZeroAsExpected() {
		// Given
		List<String> empty = new ArrayList<>(0);

		// When
		Integer result = parser.count(empty);

		// Then
		assertThat(result, is(equalTo(0)));

	}

	/**
	 * One email address returns 1.
	 */
	@Test
	void oneEmailAddressReturnsOneAsExpected() {
		// Given
		List<String> emails = Arrays.asList("a@b.com");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(1)));

	}

	/**
	 * Two different email addresses will return 2.
	 */
	@Test
	void distinctEmailsAreCounted() {
		// Given
		List<String> emails = Arrays.asList("bar@zaz.com", "foo@arg.org");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(2)));

	}

	/**
	 * Three Gmail-equivalent addresses will return 1
	 */
	@Test
	void gmailEquivalentAddressMapToTheSameAddress() {
		// Given
		List<String> emails = Arrays.asList("test.email@gmail.com", "test.email+spam@gmail.com", "testemail@gmail.com");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(1)));

	}

	@Test
	void addressesTheSameButHostsDifferentAreDifferent() {
		// Given
		List<String> emails = Arrays.asList("test.email@gmail.com", "test.email+spam@lypsinc.org",
				"testemail@gmail.com");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(2)));

	}

	@Test
	void badEmailsDoNotCount() {
		// Given
		List<String> emails = Arrays.asList("test.email", "jarrett@lypsinc.org", "@gmail.com");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(1)));

	}
}
