package adlere.train.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import adlere.train.exceptions.FunctionalException.MatchingZoneException;

public class Bill {


	private int customerId;
	private int totalCostInCents;
	private List<Trip> trips;


	private Bill(int customerId, List<Tap> taps) throws MatchingZoneException {
		this.customerId = customerId;
		this.totalCostInCents = 0;
		trips = new ArrayList<>();
		this.doCreateTrips(taps);
	}
	
	public static Optional<Bill> createNewBillFromTaps(int customerId, List<Tap> taps) throws MatchingZoneException {
		return Optional.of(new Bill(customerId, taps));
	}

	public void doCreateTrips(List<Tap> taps) throws MatchingZoneException {
		int i = 0;
		while (i < taps.size() - 1) {
			Tap entry = taps.get(i);
			++i;
			Tap exit = taps.get(i);
			i++;
			Trip trip = Trip.createNewTrip(entry.getStation(), exit.getStation(), entry.getDate()).get();
			totalCostInCents += trip.getCostInCents();
			trips.add(trip);
		}
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getTotalCostInCents() {
		return totalCostInCents;
	}

	public List<Trip> getTrips() {
		return trips;
	}

}
