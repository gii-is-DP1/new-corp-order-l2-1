package us.lsi.dp1.newcorporder.util;

public interface IterableEnum<E extends Enum<E>> {

    @SuppressWarnings("unchecked")
    default E nextValue() {
        return EnumUtils.getNextValue((E) this);
    }
}
