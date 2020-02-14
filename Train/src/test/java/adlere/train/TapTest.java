package adlere.train;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import adlere.train.commun.CommunOperations;
import adlere.train.commun.Constants;
import adlere.train.customer.CustomerTaps;
import adlere.train.customer.Tap;
import adlere.train.exceptions.BaseException;
import adlere.train.exceptions.FunctionalException.OddTapsException;
import adlere.train.exceptions.FunctionalException.TapsNotFoundException;

public class TapTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	public static JSONObject emptyTaps;
	
	public static JSONObject oddTaps;
	
	@BeforeClass
	public static void createContent() {
		/* create a json with no taps */
		emptyTaps = new JSONObject();
		emptyTaps.put("taps", new JSONArray());
		
		/* create a json with odd taps */
		JSONObject tap1 = new JSONObject();
		tap1.put("unixTimestamp", 1579413600);
		tap1.put("customerId", 1);
		tap1.put("station", "A");
		
		JSONObject tap2 = new JSONObject();
		tap2.put("unixTimestamp", 1579415400);
		tap2.put("customerId", 1);
		tap2.put("station", "C");
		
		JSONObject tap3 = new JSONObject();
		tap3.put("unixTimestamp", 1579415440);
		tap3.put("customerId", 1);
		tap3.put("station", "D");
		
		
		JSONArray taps = new JSONArray();
		taps.put(tap1);
		taps.put(tap2);
		taps.put(tap3);
		
		oddTaps = new JSONObject();
		oddTaps.put("taps", taps);
	}

	@Test
	public void testTapClass() throws BaseException {
		Instant instant = Instant.now();
		Tap tap = Tap.createTap("A", (int) instant.getEpochSecond()).get();
		LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).withNano(0);
		assertEquals(time, tap.getDate());
		assertEquals("Tap [station=A, date=" + time + "]", tap.toString());
	}

	@Test
	public void noTaps() throws TapsNotFoundException, IOException {
		final File tempFile = tempFolder.newFile("inputFileNoTaps.txt");
		boolean isWritten = CommunOperations.writeJsonInFile(tempFile.getAbsolutePath(), emptyTaps);
		assertTrue(isWritten);
		TapsNotFoundException exception = assertThrows(TapsNotFoundException.class,
				() -> CustomerTaps.createCustomersTapsFromFile(tempFile.getAbsolutePath()));
		assertEquals(Constants.TAPS_NOT_FOUND, exception.getMessage());
	}


	@Test
	public void oddTaps() throws OddTapsException, IOException {
		final File tempFile = tempFolder.newFile("inputFileOdd.txt");
		boolean isWritten = CommunOperations.writeJsonInFile(tempFile.getAbsolutePath(), oddTaps);
		assertTrue(isWritten);
		OddTapsException exception = assertThrows(OddTapsException.class, () -> CustomerTaps.createCustomersTapsFromFile(tempFile.getAbsolutePath()));
		assertEquals(Constants.TAPS_ARE_ODD+1, exception.getMessage());
	}
}
