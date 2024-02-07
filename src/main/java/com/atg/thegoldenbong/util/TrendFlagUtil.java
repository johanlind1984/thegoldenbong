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

        if ((vDist15Change / vdist0) >= 0.3 && vdist0 > 200) {
            return true;
        }

        return false;
    }

    public static boolean isRedFlag(double vDist60Change, double vDist15Change, double vdist0) {

        if ((vDist15Change / vDist60Change) > 0.5 && (vDist15Change < vDist60Change) && vDist15Change < -20) {
            return true;
        }

        if ((vDist60Change / vdist0) > 0.06 && vDist60Change < -200) {
            return true;
        }

        return false;
    }
}
