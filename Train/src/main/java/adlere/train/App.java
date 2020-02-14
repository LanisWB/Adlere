package adlere.train;

import java.io.IOException;
import java.util.Optional;

import adlere.train.commun.CommunOperations;
import adlere.train.commun.Constants;
import adlere.train.customer.CustomerTaps;
import adlere.train.customer.CustomersSummaries;
import adlere.train.exceptions.FunctionalException;
import adlere.train.exceptions.FunctionalException.ArgumentNumberMismatchException;
import adlere.train.exceptions.FunctionalException.JsonEntryNotFoundException;
import adlere.train.exceptions.FunctionalException.JsonEntryTypeException;
import adlere.train.exceptions.FunctionalException.MatchingZoneException;
import adlere.train.exceptions.FunctionalException.OddTapsException;
import adlere.train.exceptions.FunctionalException.TapsNotFoundException;
import adlere.train.exceptions.TechnicalException.FileAccessException;
import adlere.train.exceptions.TechnicalException.FileEmptyException;
import adlere.train.exceptions.TechnicalException.FileTypeException;

/**
 * Hello world!
 *
 */
public class App {
	/*
	 * do you want me to test main method also ? Is the use of static factory
	 * methods appreciated ? I created optionals not null is it more useful in
	 * thiscase to use it nullable? the code coverage is above 80%, is it sufficiant
	 * or do I need to add tests? Is there any other tips? PS: I will be cleaning
	 * thecode and adding comments after your response
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 2 || args.length > 2) {
				throw new FunctionalException.ArgumentNumberMismatchException();
			}
			Optional<CustomerTaps> customerTaps = CustomerTaps.createCustomersTapsFromFile(args[0]);

			CustomersSummaries customerSummeries = CustomersSummaries.createNewCustomersSummaries(args[1]).get();

			customerSummeries.doCalculateBills(customerTaps.get());

			CommunOperations.doCreateOutputFile(customerSummeries.getSummaries(), customerSummeries.getPath());

			System.out.println(Constants.FILE_UPDATED);

		} catch (ArgumentNumberMismatchException | FileAccessException | FileEmptyException | TapsNotFoundException
				| JsonEntryNotFoundException | JsonEntryTypeException | OddTapsException | MatchingZoneException
				| FileTypeException | IOException e) {
			e.printStackTrace();
			System.out.println(Constants.TRY_AGAIN);
		}

	}
}
