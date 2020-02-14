package adlere.train.exceptions;

import adlere.train.commun.Constants;

public class FunctionalException {

	public static class JsonEntryNotFoundException extends BaseException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3833107563390217522L;

		public JsonEntryNotFoundException(String key) {
			super(Constants.JSON_ENTRY_NOT_FOUND+key);
		}

	}
	
	public static class JsonEntryTypeException extends BaseException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3833107563390217522L;

		public JsonEntryTypeException(String key) {
			super(Constants.JSON_ENTRY_TYPE_MISMATCH+key);
		}

	}
	
	public static class ZoneNotFoundException extends BaseException {


		/**
		 * 
		 */
		private static final long serialVersionUID = -6535043861970585655L;

		public ZoneNotFoundException(int zone) {
			super(Constants.ZONE_NOT_FOUND+zone);
		}

	}
	
	public static class TapsNotFoundException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = 1812597394778279753L;

		public TapsNotFoundException() {
			super(Constants.TAPS_NOT_FOUND);
		}

	}
	
	public static class OddTapsException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = 1812597394778279753L;

		public OddTapsException(int id) {
			super(Constants.TAPS_ARE_ODD+id);
		}

	}
	
	public static class MatchingZoneException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = -7879867262270839833L;

		public MatchingZoneException(String station) {
			super(Constants.MATCHING_ZONE_NOT_FOUND+station);
		}

	}
	
	public static class ArgumentNumberMismatchException extends BaseException {




		/**
		 * 
		 */
		private static final long serialVersionUID = -6852373650405373274L;

		public ArgumentNumberMismatchException() {
			super(Constants.NUMBER_OF_ARGUMENTS);
		}

	}
}
