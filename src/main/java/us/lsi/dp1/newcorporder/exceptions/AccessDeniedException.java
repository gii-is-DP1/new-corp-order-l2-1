package us.lsi.dp1.newcorporder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

	@Serial private static final long serialVersionUID = -1461835347378078101L;

	public AccessDeniedException() {
		super("Access denied!");
	}

	public AccessDeniedException(String message) {
		super(message);
	}
}
