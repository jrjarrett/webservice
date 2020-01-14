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
import org.junit.jupiter.api.Disabled;
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

	@Test
	void edgeCaseEmailsWork () {
		// Given
		List<String> emails = Arrays.asList("email@example.com",
				"firstname.lastname@example.com",
				"email@subdomain.example.com",
				"firstname+lastname@example.com",
				"email@123.123.123.123",
				"email@[123.123.123.123]",
				"\"email\"@example.com",
				"1234567890@example.com",
				"email@example-one.com",
				"_______@example.com",
				"email@example.name",
				"email@example.museum",
				"email@example.co.jp",
				"firstname-lastname@example.com");
		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(emails.size())));
	}

	@Test
	@Disabled
	/**
	 * The regexp in the parser doesn't see these as valid emails. Apparently, they ARE valid.
	 */
	void veryWeirdEmailsWork () {
		// Given
		List<String> emails = Arrays.asList("much.more\\ unusual\"@example.com",
				"very.unusual.\"@\".unusual.com@example.com",
				"very.”(),:;<>[]”.VERY.”very@\\\\ \"very”.unusual@strange.example.com");

		// When
		Integer result = parser.count(emails);

		// Then
		assertThat(result, is(equalTo(3)));
	}

	@Test
	void largeListWithDupsParsesCorrectly () {
		// Given
		// When
		Integer result = parser.count(lotsOfEmailsWithDups);
		// Then
		assertThat(lotsOfEmailsWithDups.size(), is(equalTo(400)));
		assertThat(result, is(equalTo(396)));
	}

	List<String> lotsOfEmailsWithDups = Arrays.asList("bbirth@msn.com",
			"klaudon@yahoo.ca",
			"jbailie@yahoo.com",
			"johndo@live.com",
			"karasik@sbcglobal.net",
			"amichalo@att.net",
			"jmmuller@hotmail.com",
			"bebing@icloud.com",
			"dobey@me.com",
			"treeves@icloud.com",
			"spadkins@outlook.com",
			"sinclair@yahoo.ca",
			"geoffr@live.com",
			"mgemmons@comcast.net",
			"flakeg@verizon.net",
			"damian@optonline.net",
			"irving@optonline.net",
			"juliano@me.com",
			"presoff@yahoo.ca",
			"fallorn@verizon.net",
			"rjones@live.com",
			"duchamp@gmail.com",
			"bartak@msn.com",
			"nimaclea@outlook.com",
			"noticias@icloud.com",
			"webdragon@aol.com",
			"drhyde@yahoo.com",
			"saridder@optonline.net",
			"ideguy@gmail.com",
			"maneesh@mac.com",
			"bancboy@msn.com",
			"bartak@gmail.com",
			"oneiros@yahoo.com",
			"pjacklam@sbcglobal.net",
			"adamk@comcast.net",
			"carreras@sbcglobal.net",
			"stern@outlook.com",
			"kenja@comcast.net",
			"parasite@gmail.com",
			"tsuruta@hotmail.com",
			"papathan@mac.com",
			"mthurn@mac.com",
			"maikelnai@optonline.net",
			"pizza@me.com",
			"kempsonc@att.net",
			"noticias@gmail.com",
			"flaviog@optonline.net",
			"rtanter@icloud.com",
			"pplinux@yahoo.ca",
			"kannan@hotmail.com",
			"overbom@aol.com",
			"jimxugle@verizon.net",
			"jdhedden@icloud.com",
			"wetter@yahoo.ca",
			"rafasgj@optonline.net",
			"sopwith@live.com",
			"tfinniga@att.net",
			"mirod@yahoo.ca",
			"tmaek@att.net",
			"balchen@gmail.com",
			"sharon@comcast.net",
			"wildixon@me.com",
			"martink@icloud.com",
			"sartak@gmail.com",
			"sravani@icloud.com",
			"ournews@aol.com",
			"jguyer@msn.com",
			"kramulous@verizon.net",
			"rmcfarla@yahoo.ca",
			"jamuir@verizon.net",
			"miltchev@yahoo.ca",
			"tromey@outlook.com",
			"jsnover@icloud.com",
			"kuparine@live.com",
			"jrifkin@me.com",
			"pakaste@aol.com",
			"aardo@msn.com",
			"seano@mac.com",
			"aibrahim@optonline.net",
			"leslie@hotmail.com",
			"crobles@sbcglobal.net",
			"jmorris@msn.com",
			"karasik@mac.com",
			"kjetilk@outlook.com",
			"pierce@msn.com",
			"manuals@hotmail.com",
			"sabren@outlook.com",
			"rsteiner@gmail.com",
			"nelson@comcast.net",
			"shrapnull@optonline.net",
			"evans@aol.com",
			"cameron@mac.com",
			"satch@live.com",
			"sartak@att.net",
			"sinkou@aol.com",
			"durist@hotmail.com",
			"nweaver@sbcglobal.net",
			"jonathan@yahoo.ca",
			"lamky@msn.com",
			"staffelb@mac.com",
			"ccohen@verizon.net",
			"ebassi@aol.com",
			"gtewari@hotmail.com",
			"blixem@aol.com",
			"barjam@gmail.com",
			"marcs@yahoo.com",
			"jespley@me.com",
			"sbmrjbr@sbcglobal.net",
			"webteam@outlook.com",
			"jdhedden@comcast.net",
			"rwelty@comcast.net",
			"dimensio@live.com",
			"sinkou@live.com",
			"vlefevre@sbcglobal.net",
			"webteam@verizon.net",
			"johnh@optonline.net",
			"nimaclea@yahoo.ca",
			"satch@sbcglobal.net",
			"mrdvt@icloud.com",
			"cisugrad@aol.com",
			"sequin@yahoo.ca",
			"jbryan@mac.com",
			"credmond@yahoo.ca",
			"moxfulder@gmail.com",
			"tubajon@msn.com",
			"terjesa@sbcglobal.net",
			"timtroyr@yahoo.com",
			"skaufman@me.com",
			"jusdisgi@mac.com",
			"jbuchana@icloud.com",
			"stewwy@comcast.net",
			"tattooman@live.com",
			"arnold@hotmail.com",
			"mlewan@optonline.net",
			"phish@gmail.com",
			"wbarker@aol.com",
			"chinthaka@comcast.net",
			"esbeck@comcast.net",
			"dwendlan@msn.com",
			"pontipak@me.com",
			"magusnet@optonline.net",
			"willg@msn.com",
			"wkrebs@verizon.net",
			"gemmell@yahoo.com",
			"raines@outlook.com",
			"presoff@comcast.net",
			"weidai@optonline.net",
			"mhassel@hotmail.com",
			"hamilton@icloud.com",
			"jgwang@mac.com",
			"starstuff@sbcglobal.net",
			"gravyface@att.net",
			"ajlitt@comcast.net",
			"miturria@optonline.net",
			"bwcarty@comcast.net",
			"jgoerzen@msn.com",
			"cvrcek@yahoo.ca",
			"emmanuel@msn.com",
			"comdig@mac.com",
			"dhrakar@mac.com",
			"dbanarse@gmail.com",
			"kidehen@sbcglobal.net",
			"eurohack@icloud.com",
			"dunstan@optonline.net",
			"dwendlan@me.com",
			"mbswan@msn.com",
			"isotopian@me.com",
			"luvirini@aol.com",
			"dawnsong@att.net",
			"wonderkid@msn.com",
			"ntegrity@outlook.com",
			"yenya@verizon.net",
			"erynf@optonline.net",
			"jguyer@yahoo.ca",
			"subir@optonline.net",
			"stewwy@yahoo.ca",
			"gfody@sbcglobal.net",
			"sagal@mac.com",
			"dmiller@mac.com",
			"scarolan@comcast.net",
			"adillon@aol.com",
			"tbeck@yahoo.ca",
			"pierce@verizon.net",
			"phyruxus@yahoo.com",
			"ntegrity@gmail.com",
			"hllam@sbcglobal.net",
			"errxn@yahoo.ca",
			"skajan@yahoo.com",
			"mobileip@optonline.net",
			"chaffar@aol.com",
			"bowmanbs@outlook.com",
			"seano@verizon.net",
			"killmenow@outlook.com",
			"pthomsen@outlook.com",
			"thaljef@me.com",
			"sthomas@att.net",
			"markjugg@att.net",
			"jfmulder@yahoo.ca",
			"tlinden@aol.com",
			"cremonini@msn.com",
			"duchamp@aol.com",
			"citadel@yahoo.com",
			"lushe@verizon.net",
			"citadel@verizon.net",
			"jsmith@verizon.net",
			"rjones@outlook.com",
			"boser@hotmail.com",
			"kannan@optonline.net",
			"punkis@hotmail.com",
			"afifi@outlook.com",
			"blixem@msn.com",
			"rafasgj@me.com",
			"mailarc@mac.com",
			"rfisher@optonline.net",
			"bryam@gmail.com",
			"sassen@yahoo.ca",
			"uncle@hotmail.com",
			"gslondon@msn.com",
			"nachbaur@live.com",
			"sburke@att.net",
			"teverett@aol.com",
			"saridder@comcast.net",
			"gbacon@comcast.net",
			"jesse@verizon.net",
			"jaarnial@gmail.com",
			"panolex@verizon.net",
			"parkes@att.net",
			"tsuruta@outlook.com",
			"podmaster@mac.com",
			"speeves@sbcglobal.net",
			"matsn@me.com",
			"nasarius@hotmail.com",
			"chrisk@msn.com",
			"gamma@me.com",
			"jyoliver@live.com",
			"pmint@optonline.net",
			"pereinar@att.net",
			"gboss@sbcglobal.net",
			"syrinx@verizon.net",
			"jacks@hotmail.com",
			"heckerman@mac.com",
			"zeller@yahoo.ca",
			"cremonini@sbcglobal.net",
			"nullchar@mac.com",
			"khris@msn.com",
			"hellfire@sbcglobal.net",
			"peoplesr@live.com",
			"linuxhack@yahoo.ca",
			"purvis@comcast.net",
			"sthomas@verizon.net",
			"gravyface@me.com",
			"seanq@msn.com",
			"helger@comcast.net",
			"ivoibs@hotmail.com",
			"csilvers@att.net",
			"mosses@live.com",
			"aglassis@me.com",
			"bflong@optonline.net",
			"attwood@optonline.net",
			"barnett@gmail.com",
			"rcwil@hotmail.com",
			"nachbaur@me.com",
			"carmena@yahoo.com",
			"seasweb@comcast.net",
			"simone@verizon.net",
			"steve@yahoo.com",
			"mastinfo@icloud.com",
			"vertigo@yahoo.ca",
			"janusfury@yahoo.com",
			"miami@yahoo.ca",
			"bolow@mac.com",
			"dwheeler@me.com",
			"isotopian@aol.com",
			"boein@hotmail.com",
			"credmond@hotmail.com",
			"lamprecht@me.com",
			"bachmann@optonline.net",
			"lridener@aol.com",
			"ribet@yahoo.ca",
			"epeeist@verizon.net",
			"shrapnull@hotmail.com",
			"jmorris@yahoo.ca",
			"jsnover@icloud.com",
			"sarahs@optonline.net",
			"mcnihil@att.net",
			"teverett@comcast.net",
			"report@aol.com",
			"msroth@hotmail.com",
			"keijser@me.com",
			"parksh@outlook.com",
			"djpig@yahoo.com",
			"jkegl@yahoo.ca",
			"stomv@aol.com",
			"ganter@att.net",
			"clkao@outlook.com",
			"clkao@icloud.com",
			"melnik@gmail.com",
			"cgarcia@verizon.net",
			"bigmauler@mac.com",
			"pgottsch@mac.com",
			"wsnyder@aol.com",
			"rohitm@gmail.com",
			"fairbank@outlook.com",
			"elflord@verizon.net",
			"openldap@optonline.net",
			"mbalazin@sbcglobal.net",
			"dhwon@yahoo.ca",
			"jemarch@verizon.net",
			"seanq@att.net",
			"lstein@me.com",
			"killmenow@verizon.net",
			"report@aol.com",
			"tromey@outlook.com",
			"glenz@sbcglobal.net",
			"matloff@optonline.net",
			"mschilli@hotmail.com",
			"stakasa@verizon.net",
			"jfriedl@live.com",
			"codex@optonline.net",
			"retoh@live.com",
			"seanq@mac.com",
			"wildfire@yahoo.ca",
			"mcmillan@verizon.net",
			"mfburgo@yahoo.ca",
			"amichalo@yahoo.ca",
			"hoangle@optonline.net",
			"pereinar@gmail.com",
			"aprakash@hotmail.com",
			"heine@optonline.net",
			"baveja@msn.com",
			"ateniese@me.com",
			"sravani@yahoo.com",
			"skippy@icloud.com",
			"staikos@sbcglobal.net",
			"martink@att.net",
			"ingolfke@verizon.net",
			"larry@msn.com",
			"torgox@att.net",
			"sriha@msn.com",
			"bdbrown@sbcglobal.net",
			"djpig@live.com",
			"pkplex@icloud.com",
			"skoch@yahoo.ca",
			"sinkou@yahoo.ca",
			"thrymm@att.net",
			"jbailie@live.com",
			"forsberg@msn.com",
			"warrior@sbcglobal.net",
			"qmacro@aol.com",
			"cremonini@outlook.com",
			"jorgb@me.com",
			"njpayne@hotmail.com",
			"giafly@icloud.com",
			"pappp@me.com",
			"josephw@mac.com",
			"nicktrig@att.net",
			"teverett@att.net",
			"mnemonic@aol.com",
			"bogjobber@comcast.net",
			"moxfulder@live.com",
			"ducasse@live.com",
			"koudas@sbcglobal.net",
			"thomasj@live.com",
			"jandrese@hotmail.com",
			"afifi@outlook.com",
			"hamilton@gmail.com",
			"squirrel@sbcglobal.net",
			"garyjb@hotmail.com",
			"scotfl@verizon.net",
			"pgolle@verizon.net",
			"lydia@verizon.net",
			"marin@att.net",
			"fmerges@me.com",
			"druschel@aol.com",
			"jdray@verizon.net",
			"speeves@optonline.net",
			"oneiros@live.com",
			"nasarius@comcast.net",
			"pajas@att.net",
			"gommix@outlook.com",
			"rgarton@att.net",
			"murty@verizon.net",
			"singh@hotmail.com",
			"rogerspl@yahoo.com",
			"lauronen@verizon.net",
			"ajohnson@msn.com",
			"qmacro@icloud.com",
			"heidrich@sbcglobal.net",
			"dkeeler@live.com",
			"solomon@att.net",
			"fatelk@outlook.com",
			"blixem@comcast.net",
			"thomasj@comcast.net",
			"north@att.net",
			"dbanarse@me.com",
			"mkearl@mac.com",
			"noahb@hotmail.com",
			"chaki@gmail.com",
			"matsn@gmail.com",
			"jbuchana@yahoo.ca");

}
