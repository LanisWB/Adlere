package adlere.train.customer;

import java.time.LocalDateTime;
import java.util.Optional;

import adlere.train.commun.CommunOperations;

public class Tap {
	
	private String station;
	private LocalDateTime date;

	private Tap(String station, int unixTimestamp) {
		this.station = station;
		this.date = CommunOperations.getDateFromInt(unixTimestamp);
	}
	
	public static Optional<Tap> createTap(String station, int unixTimestamp) {
		Tap tap = new Tap(station, unixTimestamp);
		return Optional.of(tap);
		
	}



	public String getStation() {
		return station;
	}


	public LocalDateTime getDate() {
		return date;
	}


	@Override
	public String toString() {
		return "Tap [station=" + station + ", date=" + date + "]";
	}
}
