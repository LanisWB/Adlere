package adlere.train.exceptions;

public abstract class BaseException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3183043251322242241L;
	
	private String message;
    
    public BaseException(String msg)
    {
        this.message = msg;
    }
    public String getMessage() {
        return message;
    }
}
