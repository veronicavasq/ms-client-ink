package ms.client.exception;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;


public class InternalException extends Exception {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private HttpStatus status;
	@Getter
	@Setter
	private String errorMessage;
	@Getter
	@Setter
	private Object data;

	public InternalException(HttpStatus status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}

}
