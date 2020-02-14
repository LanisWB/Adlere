package adlere.train.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import adlere.train.commun.CommunOperations;
import adlere.train.exceptions.FunctionalException.MatchingZoneException;
import adlere.train.exceptions.TechnicalException.FileAccessException;

public class CustomersSummaries {

	private String path;

	private List<Bill> summaries;

	private CustomersSummaries(String path) throws FileAccessException, IOException {
		CommunOperations.checkFile(path);
		this.path = path;
		summaries = new ArrayList<Bill>();
	}

	public static Optional<CustomersSummaries> createNewCustomersSummaries(String path)
			throws FileAccessException, IOException {
		return Optional.of(new CustomersSummaries(path));
	}

	public boolean doCalculateBills(CustomerTaps customersTaps) throws MatchingZoneException {
		for (Map.Entry<Integer, List<Tap>> entry : customersTaps.getMap().entrySet()) {
			summaries.add(Bill.createNewBillFromTaps(entry.getKey(), entry.getValue()).get());
		}
		return true;
	}

	public List<Bill> getSummaries() {
		return summaries;
	}

	public String getPath() {
		return path;
	}

}
