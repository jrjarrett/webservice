package org.lypsinc.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.counting;

/**
 * A class that given a list of Strings that are email address, will count the
 * unique address the list contains.
 * 
 * "unique" email addresses means they will be delivered to the same account
 * using Gmail account matching. Specifically: Gmail will ignore the placement
 * of "." in the username. And it will ignore any portion of the username after
 * a "+".
 * 
 * @author jarrett
 *
 */
public class EmailParser {

	private static final Logger LOG = LoggerFactory.getLogger(EmailParser.class);

	public Integer count(List<String> emailAddresses) {

		// email matching pattern from https://www.regular-expressions.info/email.html, with some mods.
		// It's not perfect.
		// """The""" regex for validating emails is at http://www.ex-parrot.com/pdw/Mail-RFC822-Address.html
		// noooo.

		LOG.debug("The incoming list has {} elements.", emailAddresses.size());
		Map<String, Long> addresses = emailAddresses.stream()
				.filter(email -> email.matches("[\\[\\]\"A-Za-z0-9._%+-]+@[\\[\\]\"A-Za-z0-9.-]+\\.[\\[\\]\"A-Za-z0-9]{2,}"))
				.map(e -> {
					String[] addressHost = e.split("@");
					return Pair.of(addressHost[0], addressHost[1]);
				})
				.filter(Objects::nonNull)
				.map(ep -> Pair.of(ep.getLeft().replace(".", "").split("\\+")[0], ep.getRight()))
				.map(fm -> fm.getLeft() + "@" + fm.getRight())
				.collect(Collectors.groupingBy(Function.identity(),
						Collectors.counting()));

		LOG.debug("The result is " + addresses);

		return addresses.size();

	}

}
