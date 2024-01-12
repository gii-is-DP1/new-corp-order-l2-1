package us.lsi.dp1.newcorporder.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.net.HttpURLConnection;
import java.net.URL;
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

    public static boolean isValidImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();

            // Verifica si la respuesta es exitosa y el contenido es una imagen
            if (responseCode / 100 == 2) {
                String contentType = connection.getContentType();
                return contentType.startsWith("image/");
            }
        } catch (Exception e) {
            System.err.println("Error al validar la URL de la imagen: " + e.getMessage());
        }
        return false;
    }
}
