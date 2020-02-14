package adlere.train.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import adlere.train.commun.Zone;
import adlere.train.exceptions.FunctionalException;
import adlere.train.exceptions.FunctionalException.MatchingZoneException;

public class Trip {


	private String stationStart;
	private String stationEnd;
	private LocalDateTime startedJourneyAt;
	private int costInCents;
	private Zone zoneFrom;
	private Zone zoneTo;

	private Trip(String stationStart, String stationEnd, LocalDateTime startedJourneyAt) throws MatchingZoneException {
		this.stationStart = stationStart;
		this.stationEnd = stationEnd;
		this.startedJourneyAt = startedJourneyAt;
		this.costInCents = 0;
		setZoneAndPrice();
	}
	
	public static Optional<Trip> createNewTrip(String stationStart, String stationEnd, LocalDateTime startedJourneyAt) throws MatchingZoneException{
		return Optional.of(new Trip(stationStart, stationEnd, startedJourneyAt));
	}

	public void setZoneAndPrice() throws MatchingZoneException {
		List<Zone> possibleStartZones = Zone.getListOfPossibleZones(stationStart);

		if (possibleStartZones.isEmpty()) {
			throw new FunctionalException.MatchingZoneException(stationStart);
		}

		List<Zone> possibleEndZones = Zone.getListOfPossibleZones(stationEnd);
		if (possibleEndZones.isEmpty()) {
			throw new FunctionalException.MatchingZoneException(stationEnd);
		}

		for (Zone zone : possibleStartZones) {
			for (Zone zone2 : possibleEndZones) {
				int tempPrice = zone2.priceFrom(zone);
				if (costInCents != 0) {
					if (tempPrice < costInCents) {
						zoneFrom = zone;
						zoneTo = zone2;
						costInCents = tempPrice;
					}
				} else {
					zoneFrom = zone;
					zoneTo = zone2;
					costInCents = tempPrice;
				}
			}
		}
	}

	public String getStationStart() {
		return stationStart;
	}


	public String getStationEnd() {
		return stationEnd;
	}


	public LocalDateTime getStartedJourneyAt() {
		return startedJourneyAt;
	}


	public int getCostInCents() {
		return costInCents;
	}


	public Zone getZoneFrom() {
		return zoneFrom;
	}


	public Zone getZoneTo() {
		return zoneTo;
	}


}
