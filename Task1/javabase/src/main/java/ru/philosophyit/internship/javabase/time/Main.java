package ru.philosophyit.internship.javabase.time;

import java.util.Calendar;

public class Main {
    public static void main(String ...args) {
        Calendar firstDay = Calendar.getInstance();
        Calendar lastDay = Calendar.getInstance();

        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        while (firstDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            firstDay.add(Calendar.DAY_OF_MONTH, -1);
        }

        lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        while (lastDay.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            lastDay.add(Calendar.DAY_OF_MONTH, 1);
        }

        System.out.println("пн  вт  ср  чт  пт  сб  вс");
        while (firstDay.before(lastDay)) {
            System.out.printf("%-4d", firstDay.get(Calendar.DAY_OF_MONTH));
            firstDay.add(Calendar.DAY_OF_MONTH, 1);
            if (firstDay.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) System.out.println();
        }
    }
}
