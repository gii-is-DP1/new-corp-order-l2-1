package us.lsi.dp1.newcorporder.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils {

    public static <E> E getRandomElement(E[] array) {
        return getRandomElement(array, new Random());
    }

    public static <E> E getRandomElement(E[] array, Random random) {
        return array[random.nextInt(array.length)];
    }

    public static String getRandomMatchCode() {
        return RandomStringUtils.random(6, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
