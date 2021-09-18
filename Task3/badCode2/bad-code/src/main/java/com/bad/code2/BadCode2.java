package com.bad.code2;

import com.bad.code2.figures.Qube;
import com.bad.code2.figures.Square;
import com.bad.code2.interfaces.ThreeDimensionalShape;
import com.bad.code2.interfaces.TwoDimensionalShape;

public class BadCode2 {
    public static void main(String... args) {
        ThreeDimensionalShape qube = new Qube(1d, 1d, 1d, 10d);
        System.out.println("Qube volume: " + qube.getVolume());

        TwoDimensionalShape square = new Square(1d, 1d, 5d);
        System.out.println("Square perimeter: " + square.getPerimeter());
    }

}
