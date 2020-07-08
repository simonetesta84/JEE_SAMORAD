package samorad.exception;

public class ServiceEjbException extends Exception {
	
	private String messageException;

	
	public ServiceEjbException(String messageException) {
		super();
		this.messageException = messageException;
	}

	public String getMessageException() {
		return messageException;
	}
}
