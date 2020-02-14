package adlere.train.commun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adlere.train.exceptions.FunctionalException;
import adlere.train.exceptions.FunctionalException.ZoneNotFoundException;

public enum Zone {

	ZONE1(1, new String[] { "A", "B" }), ZONE2(2, new String[] { "C", "D", "E" }), ZONE3(3,
			new String[] { "C", "E", "F" }), ZONE4(4, new String[] { "F", "G", "H", "I" });

	private int name;
	private String[] stations;

	private Zone(int name, String[] stations) {
		this.name = name;
		this.stations = stations;
	}

	public static Zone getZoneByName(int name) throws ZoneNotFoundException {
		switch (name) {
		case 1:
			return ZONE1;
		case 2:
			return ZONE2;
		case 3:
			return ZONE3;
		case 4:
			return ZONE4;
		default:
			throw new FunctionalException.ZoneNotFoundException(name);
		}
	}

	public int priceFrom(Zone zone) {
		switch (zone.getName()) {
		case 1:
		case 2:
			if (name == 1 || name == 2) {
				return Constants.PRICE_WITHIN_ZONE1_AND_2;
			}
			if (name == 3) {
				return Constants.PRICE_FROM_1n2_TO_3;
			}

			return Constants.PRICE_FROM_1n2_TO_4;

		case 3:
			if (name == 4 || name == 3) {
				return Constants.PRICE_WITHIN_ZONE3_AND_4;

			}
			return Constants.PRICE_FROM_1n2_TO_3;

		default:
			if (name == 4 || name == 3) {
				return Constants.PRICE_WITHIN_ZONE3_AND_4;

			}
			return Constants.PRICE_FROM_1n2_TO_4;
		}

	}

	public static List<Zone> getListOfPossibleZones(String station) {
		List<Zone> possibleZones = new ArrayList<Zone>();
		if (Arrays.stream(ZONE1.getStations()).anyMatch(x -> x.equals(station))) {
			possibleZones.add(ZONE1);
		}

		if (Arrays.stream(ZONE2.getStations()).anyMatch(x -> x.equals(station))) {
			possibleZones.add(ZONE2);
		}

		if (Arrays.stream(ZONE3.getStations()).anyMatch(x -> x.equals(station))) {
			possibleZones.add(ZONE3);
		}

		if (Arrays.stream(ZONE4.getStations()).anyMatch(x -> x.equals(station))) {
			possibleZones.add(ZONE4);
		}
		return possibleZones;
	}

	public int getName() {
		return name;
	}

	public String[] getStations() {
		return stations;
	}

}
