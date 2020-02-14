package adlere.train.commun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import adlere.train.customer.Bill;
import adlere.train.customer.Trip;
import adlere.train.exceptions.FunctionalException;
import adlere.train.exceptions.FunctionalException.JsonEntryNotFoundException;
import adlere.train.exceptions.FunctionalException.JsonEntryTypeException;
import adlere.train.exceptions.TechnicalException;
import adlere.train.exceptions.TechnicalException.FileAccessException;
import adlere.train.exceptions.TechnicalException.FileEmptyException;
import adlere.train.exceptions.TechnicalException.FileTypeException;

public class CommunOperations {

	/************** Input File *******************/
	
	public static String readFileNotEmpty(String path) throws FileAccessException, FileEmptyException, IOException, FileTypeException {
		File file = new File(path);
		if (!file.canRead()) {
			throw new TechnicalException.FileAccessException();
		}
		if(file.isDirectory()) {
			throw new TechnicalException.FileTypeException();
		}
		String contents = new String(Files.readAllBytes(Paths.get(path)));
		if (StringUtils.isBlank(contents)) {
			throw new TechnicalException.FileEmptyException();
		}
		return contents;

	}

	public static boolean checkJsonFormat(String contents) throws JsonParseException, IOException {
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(contents);
		while (!parser.isClosed()) {
			parser.nextToken();
		}
		return true;
	}

	public static int getInteger(JSONObject obj, String key) throws JsonEntryNotFoundException, JsonEntryTypeException {
		if (!obj.has(key)) {
			throw new FunctionalException.JsonEntryNotFoundException(key);
		}
		if (!(obj.get(key) instanceof Integer)) {
			throw new FunctionalException.JsonEntryTypeException(key);
		}

		return obj.getInt(key);
	}

	public static String getStringValue(JSONObject obj, String key)
			throws JsonEntryNotFoundException, JsonEntryTypeException {
		if (!obj.has(key)) {
			throw new FunctionalException.JsonEntryNotFoundException(key);
		}
		if (!(obj.get(key) instanceof String)) {
			throw new FunctionalException.JsonEntryTypeException(key);
		}

		return obj.getString(key);
	}

	public static LocalDateTime getDateFromInt(int unixTimestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneId.systemDefault());
	}
	
	/************** Output File *******************/

	public static boolean checkFile(String path) throws FileAccessException, IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs(); // Will create parent directories if not exists
			file.createNewFile(); // Will create file if not exists
		} else if (!file.canWrite()) {
			throw new TechnicalException.FileAccessException();
		}

		return true;

	}
	
	public static boolean doCreateOutputFile(List<Bill> summaries , String path) throws IOException {
		JSONObject output = new JSONObject();
		JSONArray customerSummaries = new JSONArray();
		for (Bill bill : summaries) {
			JSONObject customer = new JSONObject();
			JSONArray trips = new JSONArray();
			customer.put(Constants.CUSTOMER_ID_KEY, bill.getCustomerId());
			customer.put(Constants.TOTAL_COST_IN_CENTS_KEY, bill.getTotalCostInCents());
			for (Trip trip : bill.getTrips()) {
				trips.put(createJsonTrip(trip));
			}

			customer.put(Constants.TRIPS_KEY, trips);
			customerSummaries.put(customer);
		}
		output.put(Constants.CUSTOMER_SUMMARIES_KEY, customerSummaries);
		writeJsonInFile(path, output);
		return true;
	}

	public static JSONObject createJsonTrip(Trip trip) {
		JSONObject jsonTrip = new JSONObject();
		jsonTrip.put(Constants.STATION_START_KEY, trip.getStationStart());
		jsonTrip.put(Constants.STATION_END_KEY, trip.getStationEnd());
		jsonTrip.put(Constants.STARTED_JOURNEY_AT_KEY,
				trip.getStartedJourneyAt().atZone(ZoneId.systemDefault()).toEpochSecond());
		jsonTrip.put(Constants.COST_IN_CENTS_KEY, trip.getCostInCents());
		jsonTrip.put(Constants.ZONE_FROM_KEY, trip.getZoneFrom().getName());
		jsonTrip.put(Constants.ZONE_TO_KEY, trip.getZoneTo().getName());
		return jsonTrip;
	}

	public static boolean writeJsonInFile(String path, JSONObject output) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
		output.write(writer, 2, 0);
		writer.close();
		return true;
	}

}
