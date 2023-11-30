package us.lsi.dp1.newcorporder.util;

public class EnumUtils {

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getNextValue(E value) {
        Enum[] values = value.getClass().getEnumConstants();
        return (E) values[(value.ordinal() + 1) % values.length];
    }
}
