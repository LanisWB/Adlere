package adlere.train;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import adlere.train.commun.CommunOperations;
import adlere.train.commun.Constants;
import adlere.train.customer.CustomerTaps;
import adlere.train.customer.CustomersSummaries;
import adlere.train.customer.Trip;
import adlere.train.exceptions.FunctionalException.JsonEntryNotFoundException;
import adlere.train.exceptions.FunctionalException.JsonEntryTypeException;
import adlere.train.exceptions.FunctionalException.MatchingZoneException;
import adlere.train.exceptions.FunctionalException.OddTapsException;
import adlere.train.exceptions.FunctionalException.TapsNotFoundException;
import adlere.train.exceptions.TechnicalException.FileAccessException;
import adlere.train.exceptions.TechnicalException.FileEmptyException;
import adlere.train.exceptions.TechnicalException.FileTypeException;

public class AppTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	public static JSONObject allTaps;

	@BeforeClass
	public static void createContent() {

		/* create a json with input taps */
		JSONObject tap1 = new JSONObject();
		tap1.put("unixTimestamp", 1579413600);
		tap1.put("customerId", 1);
		tap1.put("station", "C");

		JSONObject tap2 = new JSONObject();
		tap2.put("unixTimestamp", 1579415400);
		tap2.put("customerId", 1);
		tap2.put("station", "I");

		JSONObject tap3 = new JSONObject();
		tap3.put("unixTimestamp", 1579415440);
		tap3.put("customerId", 1);
		tap3.put("station", "D");

		JSONObject tap4 = new JSONObject();
		tap4.put("unixTimestamp", 1579415440);
		tap4.put("customerId", 1);
		tap4.put("station", "A");

		JSONObject tap21 = new JSONObject();
		tap21.put("unixTimestamp", 1579415400);
		tap21.put("customerId", 2);
		tap21.put("station", "C");

		JSONObject tap22 = new JSONObject();
		tap22.put("unixTimestamp", 1579415440);
		tap22.put("customerId", 2);
		tap22.put("station", "D");

		JSONArray taps = new JSONArray();
		taps.put(tap1);
		taps.put(tap2);
		taps.put(tap3);
		taps.put(tap4);
		taps.put(tap21);
		taps.put(tap22);
		allTaps = new JSONObject();
		allTaps.put("taps", taps);
	}

	@Test
	public void matchingZone() {
		MatchingZoneException matchingZoneException = assertThrows(MatchingZoneException.class,
				() -> Trip.createNewTrip("A", "Z", LocalDateTime.now()));
		assertEquals(Constants.MATCHING_ZONE_NOT_FOUND + "Z", matchingZoneException.getMessage());

		MatchingZoneException matchingZoneException2 = assertThrows(MatchingZoneException.class,
				() -> Trip.createNewTrip("X", "Z", LocalDateTime.now()));
		assertEquals(Constants.MATCHING_ZONE_NOT_FOUND + "X", matchingZoneException2.getMessage());
	}

	@Test
	public void testWorkflow() throws IOException, FileAccessException, FileEmptyException, TapsNotFoundException,
			JsonEntryNotFoundException, JsonEntryTypeException, OddTapsException, FileTypeException,
			MatchingZoneException {
		final File tempInputFile = tempFolder.newFile("inputSimple.txt");
		final File tempOutputFile = tempFolder.newFile("outputSimple.txt");
		boolean isWritten = CommunOperations.writeJsonInFile(tempInputFile.getAbsolutePath(), allTaps);
		assertTrue(isWritten);
		CustomerTaps customerTaps = CustomerTaps.createCustomersTapsFromFile(tempInputFile.getAbsolutePath()).get();
		CustomersSummaries customersSummeries = CustomersSummaries
				.createNewCustomersSummaries(tempOutputFile.getAbsolutePath()).get();
		Boolean bill = customersSummeries.doCalculateBills(customerTaps);
		assertTrue(bill);
		assertFalse(customersSummeries.getSummaries().isEmpty());
		assertFalse(customersSummeries.getPath().isEmpty());
		assertTrue(
				CommunOperations.doCreateOutputFile(customersSummeries.getSummaries(), customersSummeries.getPath()));
		assertTrue(tempOutputFile.length() > 0);
	}

}
