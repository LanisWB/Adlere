package adlere.train.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import adlere.train.commun.CommunOperations;
import adlere.train.commun.Constants;
import adlere.train.exceptions.FunctionalException;
import adlere.train.exceptions.FunctionalException.JsonEntryNotFoundException;
import adlere.train.exceptions.FunctionalException.JsonEntryTypeException;
import adlere.train.exceptions.FunctionalException.OddTapsException;
import adlere.train.exceptions.FunctionalException.TapsNotFoundException;
import adlere.train.exceptions.TechnicalException.FileAccessException;
import adlere.train.exceptions.TechnicalException.FileEmptyException;
import adlere.train.exceptions.TechnicalException.FileTypeException;

public class CustomerTaps {

	private Map<Integer, List<Tap>> mapOfTaps;

	private CustomerTaps(String path) throws FileAccessException, FileEmptyException, TapsNotFoundException, JsonEntryNotFoundException, JsonEntryTypeException, IOException, OddTapsException, FileTypeException {
		mapOfTaps = new HashMap<Integer, List<Tap>>();

		createMapOfTaps(path);
	}

	public static Optional<CustomerTaps> createCustomersTapsFromFile(String path)
			throws FileAccessException, FileEmptyException, TapsNotFoundException, JsonEntryNotFoundException, JsonEntryTypeException, IOException, OddTapsException, FileTypeException {
		CustomerTaps customerTaps = new CustomerTaps(path);
		
		return Optional.of(customerTaps);

	}


	public void createMapOfTaps(String path)
			throws FileAccessException, FileEmptyException, TapsNotFoundException, JsonEntryNotFoundException, JsonEntryTypeException, IOException, OddTapsException, FileTypeException {
		/* read file content */
		String contents = CommunOperations.readFileNotEmpty(path);

		/* check that file conent are in json format */
		CommunOperations.checkJsonFormat(contents);

		/* get List of Taps from the input file */
		JSONArray jsonTaps = getJsonTapsFromString(contents);

		/* create and add taps to the map */
		for (int i = 0; i < jsonTaps.length(); i++) {
			doCompeteMap(jsonTaps.getJSONObject(i));
		}
		
		/* check and sort the map of taps */
		checkMapStructure();
	}

	public JSONArray getJsonTapsFromString(String contents) throws TapsNotFoundException {
		JSONObject file = new JSONObject(contents);
		JSONArray jsonTaps = file.getJSONArray(Constants.TAPS_KEY);
		if (jsonTaps == null || jsonTaps.isEmpty()) {
			throw new FunctionalException.TapsNotFoundException();
		}
		return jsonTaps;
	}

	public void doCompeteMap(JSONObject jsonTap) throws JsonEntryNotFoundException, JsonEntryTypeException {
		int customerId = CommunOperations.getInteger(jsonTap, Constants.CUSTOMER_ID_KEY);
		String station = CommunOperations.getStringValue(jsonTap, Constants.STATION_KEY);
		Integer unixTimestamp = CommunOperations.getInteger(jsonTap, Constants.UNIX_TIMESTAMP_KEY);

		Optional<Tap> tap = Tap.createTap(station, unixTimestamp);

		if (mapOfTaps.containsKey(customerId)) {
			mapOfTaps.get(customerId).add(tap.get());
		} else {
			List<Tap> taps = new ArrayList<Tap>();
			taps.add(tap.get());
			mapOfTaps.put(customerId, taps);
		}
		
	}

	public void checkMapStructure() throws OddTapsException {
		/* check all the map entries are even */
		Optional<Integer> entryKey = mapOfTaps.entrySet().stream().filter(entry -> entry.getValue().size() % 2 != 0)
				.map(Map.Entry::getKey).findFirst();

		if (entryKey.isPresent()) {
			throw new FunctionalException.OddTapsException(entryKey.get());
		}

		/* check all the map entries are sorted chronologically */
		mapOfTaps.values().forEach(v -> v.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate())));
	}

	public Map<Integer, List<Tap>> getMap() {
		return mapOfTaps;
	}

}
