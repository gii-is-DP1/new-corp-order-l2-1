package us.lsi.dp1.newcorporder.util;

import java.util.Random;

public class RandomUtils {

    public static <E> E getRandomElement(E[] array) {
        return getRandomElement(array, new Random());
    }

    public static <E> E getRandomElement(E[] array, Random random) {
        return array[random.nextInt(array.length)];
    }
}
