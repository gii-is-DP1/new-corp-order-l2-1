package us.lsi.dp1.newcorporder.util;

import us.lsi.dp1.newcorporder.exception.AccessDeniedException;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;

public final class RestPreconditions {

	private RestPreconditions() {
        throw new AssertionError();
    }

    public static <T> T checkNotNull(final T resource,String resourceName, String fieldName, Object fieldValue) {
        if (resource == null) {
            throw new ResourceNotFoundException(resourceName, fieldName, fieldValue);
        }

        return resource;
    }

    public static void checkAccess(boolean expression) {
        if (!expression)
            throw new AccessDeniedException();
    }

    public static void checkAccess(boolean expression, String message) {
        if (!expression)
            throw new AccessDeniedException(message);
    }
}
