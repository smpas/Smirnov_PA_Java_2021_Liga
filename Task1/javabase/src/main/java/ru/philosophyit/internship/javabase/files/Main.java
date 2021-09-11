package ru.philosophyit.internship.javabase.files;

import java.io.File;
import java.io.IOException;

public class Main {
    public static String separator = File.separator;

    public static void main(String ...args) throws IOException {
        File file = new File("src" + separator + "main" + separator + "java" + separator + "ru" +
                separator + "philosophyit" + separator + "internship" + separator + "javabase");
        printDirectory(file);
    }

    public static void printDirectory(File file) {
        if (file.isFile()) {
            System.out.println(file.getPath());
            return;
        } else {
            File[] array = file.listFiles();
            if (array.length == 0) {
                System.out.println(file.getPath());
            }

            for (File check : array) {
                printDirectory(check);
            }
        }
    }
}
