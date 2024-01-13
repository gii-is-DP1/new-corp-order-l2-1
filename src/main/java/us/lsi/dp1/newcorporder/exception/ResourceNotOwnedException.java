package us.lsi.dp1.newcorporder.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class ResourceNotOwnedException extends RuntimeException {

	@Serial private static final long serialVersionUID = -3906338266891937036L;

	public ResourceNotOwnedException(final Object object) {
		super(String.format("%s not owned.", object.getClass().getSimpleName()));
	}
}
