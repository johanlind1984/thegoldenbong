package com.atg.thegoldenbong.util;

public class TrendFlagUtil {

    public static boolean isTrendFlag(double vDist60Change, double vDist15Change, double vdist0) {

        if (vdist0 > 6000) {
            return true;
        }

        if ((vDist15Change / vDist60Change) > 0.5 && vDist15Change > 20) {
            return true;
        }

        if (vDist60Change < -100 && vDist15Change > 0) {
            return true;
        }

        return false;
    }
}
