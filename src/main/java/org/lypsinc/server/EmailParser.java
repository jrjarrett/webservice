package org.lypsinc.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Map<String, Integer> addresses = new HashMap<>();

		for (String address : emailAddresses) {
			final String[] addressAndHost = address.split("@");
			// If we don't have a valid email address (no "@"), we won't count this one as
			// anything.
			if (addressAndHost.length != 2 || StringUtils.isEmpty(addressAndHost[0])
					|| StringUtils.isEmpty(addressAndHost[1])) {
				LOG.error("Not a valid email address: {}", address);
				continue;
			}

			LOG.debug("address is {} host is {}", addressAndHost[0], addressAndHost[1]);
			// Per the Gmail spec, periods are ignored and anything to the the right of a
			// plus sign is ignored.
			final String gmailValidAddress = addressAndHost[0].replace(".", "").split("\\+")[0] + "@"
					+ addressAndHost[1];
			LOG.debug("The gmail address is {}", gmailValidAddress);

			addresses.compute(gmailValidAddress, (k, v) -> (v == null) ? 1 : v + 1);
		}
		return addresses.size();

	}

}
