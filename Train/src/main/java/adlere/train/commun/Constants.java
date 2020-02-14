package adlere.train.commun;

public final class Constants {

	/*****************Exceptions messages************************/
	public static final String FILE_TYPE_ERROR = "The given path is for a directory!";

	public static final String FILE_PROTECTED_OR_NOT_FOUND_ERROR = "Either the file doesn't exist or is protected!";

	public static final String FILE_EMPTY_ERROR = "File is empty!";
	
	public static final String JSON_ENTRY_NOT_FOUND = "Please check your input file, can not find the key: ";
	
	public static final String JSON_ENTRY_TYPE_MISMATCH = "Unexpected type found in the input file for key: ";
	
	public static final String ZONE_NOT_FOUND = "No zone match for zone name: ";
	
	public static final String TAPS_NOT_FOUND ="File must contain an array of taps";
	
	public static final String TAPS_ARE_ODD ="The number of taps is odd for customer: ";
	
	public static final String MATCHING_ZONE_NOT_FOUND = "No matching zone is to be found for Station: ";
	
	public static final String NUMBER_OF_ARGUMENTS = "The application must recieve exactly 2 input parameters!";
	
	/*****************User messages************************/
	
	public static final String FILE_UPDATED ="Your output file has been updated successfully";
	
	
	public static final String TRY_AGAIN ="Please try again with the correct input parameters";
	
	
	
	/*****************JSON input and output keys************************/
	
	public static final String TAPS_KEY = "taps";
	
	public static final String CUSTOMER_ID_KEY = "customerId";
	
	public static final String STATION_KEY = "station";
	
	public static final String UNIX_TIMESTAMP_KEY = "unixTimestamp";
	
	public static final String TOTAL_COST_IN_CENTS_KEY = "totalCostInCents";
	
	public static final String STATION_START_KEY = "stationStart";
	
	public static final String STATION_END_KEY = "stationEnd";
	
	public static final String STARTED_JOURNEY_AT_KEY = "startedJourneyAt";
	
	public static final String COST_IN_CENTS_KEY = "costInCents";
	
	public static final String ZONE_FROM_KEY = "zoneFrom";
	
	public static final String ZONE_TO_KEY = "zoneTo";
	
	public static final String TRIPS_KEY = "trips";
	
	public static final String CUSTOMER_SUMMARIES_KEY = "customerSummaries";
	
	/*********************Prices from one zone to another ***************/
	
	public static final int PRICE_WITHIN_ZONE1_AND_2 = 240;
	public static final int PRICE_WITHIN_ZONE3_AND_4 = 200;
	
	public static final int PRICE_FROM_1n2_TO_3 = 280;
	public static final int PRICE_FROM_1n2_TO_4 = 300;
	
	
}
