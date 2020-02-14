package adlere.train.exceptions;

import adlere.train.commun.Constants;

public class TechnicalException {

	public static class FileTypeException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = 484376579387374066L;

		public FileTypeException() {
			super(Constants.FILE_TYPE_ERROR);
		}

	}
	
	public static class FileAccessException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = 484376579387374066L;

		public FileAccessException() {
			super(Constants.FILE_PROTECTED_OR_NOT_FOUND_ERROR);
		}

	}
	
	public static class FileEmptyException extends BaseException {



		/**
		 * 
		 */
		private static final long serialVersionUID = 573913583323349632L;

		public FileEmptyException() {
			super(Constants.FILE_EMPTY_ERROR);
		}

	}
}
