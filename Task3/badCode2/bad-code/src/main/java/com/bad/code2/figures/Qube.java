package com.bad.code2.figures;

import com.bad.code2.interfaces.ThreeDimensionalShape;

public class Qube implements ThreeDimensionalShape {
    private Double x;
    private Double y;
    private Double z;
    private Double edgeSize;

    public Qube(Double centerX, Double centerY, Double centerZ, Double edgeSize) {
        this.x = centerX;
        this.y = centerY;
        this.z = centerZ;
        this.edgeSize = edgeSize;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getZ() {
        return z;
    }

    @Override
    public Double getVolume() {
        return Math.pow(edgeSize, 3);
    }
}
