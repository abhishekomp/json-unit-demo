package org.example.utils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoldenFileReader {
    /**
     * Reads the golden JSON file from the resources' directory.
     *
     * @param resourcePath the path to the golden JSON file
     * @return the content of the JSON file as a string
     * @throws Exception if the file cannot be read
     */
    public static String readGoldenJson(String resourcePath) throws Exception {
        URL url = GoldenFileReader.class.getResource(resourcePath);
        if (url == null) throw new IllegalArgumentException("Golden JSON not found: " + resourcePath);
        return Files.readString(Paths.get(url.toURI()));
    }
}
