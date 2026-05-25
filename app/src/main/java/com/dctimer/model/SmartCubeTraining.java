package com.dctimer.model;

import com.dctimer.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.dctimer.APP.smartCubeTrainingOrientation;

public final class SmartCubeTraining {
    public static final int SMART_333_BASE = 21 << 5;
    public static final int SMART_333_WCA = SMART_333_BASE;
    public static final int SMART_333_OLL = SMART_333_BASE + 1;
    public static final int SMART_333_PLL = SMART_333_BASE + 2;
    public static final int SMART_333_LAST_LAYER = SMART_333_BASE + 3;
    public static final int SMART_333_F2L = SMART_333_BASE + 4;

    private static final String SOLVED_FACELET = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";
    private static final int[][] F2L_MASK = toEqus("----U-------RRRRRR---FFFFFFDDDDDDDDD---LLLLLL---BBBBBB");
    private static final int[][] OLL_MASK = toEqus("UUUUUUUUU---RRRRRR---FFFFFFDDDDDDDDD---LLLLLL---BBBBBB");
    private static final int[][] SOLVED_MASK = toEqus(SOLVED_FACELET);

    private SmartCubeTraining() {
    }

    public static boolean isSmart333(int scrambleIdx) {
        int idx = scrambleIdx >> 5;
        int sub = scrambleIdx & 0x1f;
        return idx == 21 && sub >= 0 && sub <= 4;
    }

    public static boolean isTrainingOrientationMode(int scrambleIdx) {
        return isSmart333(scrambleIdx) && scrambleIdx != SMART_333_WCA;
    }

    public static boolean isComplete(int scrambleIdx, String facelet) {
        if (!isTrainingOrientationMode(scrambleIdx)) {
            return isSolvedIgnoringRotation(facelet);
        }
        String oriented = Utils.orientFacelets(facelet, smartCubeTrainingOrientation);
        switch (scrambleIdx) {
            case SMART_333_OLL:
                return isSolvedForMask(oriented, OLL_MASK) || isSolvedForMask(facelet, getPhysicalMask(OLL_MASK));
            case SMART_333_F2L:
                return isSolvedForMask(oriented, F2L_MASK) || isSolvedForMask(facelet, getPhysicalMask(F2L_MASK));
            case SMART_333_WCA:
            case SMART_333_PLL:
            case SMART_333_LAST_LAYER:
            default:
                return isSolvedForMask(oriented, SOLVED_MASK);
        }
    }

    private static boolean isSolvedForMask(String facelet, int[][] mask) {
        if (facelet == null || facelet.length() < 54) {
            return false;
        }
        for (int[] equ : mask) {
            if (equ.length == 0) {
                continue;
            }
            char color = facelet.charAt(equ[0]);
            for (int i = 1; i < equ.length; i++) {
                if (facelet.charAt(equ[i]) != color) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] getPhysicalMask(int[][] displayMask) {
        String[] physicalMask = new String[displayMask.length];
        for (int i = 0; i < displayMask.length; i++) {
            char[] facelet = "------------------------------------------------------".toCharArray();
            char maskChar = (char) ('A' + i);
            for (int index : displayMask[i]) {
                facelet[index] = maskChar;
            }
            physicalMask[i] = Utils.unorientFacelets(new String(facelet), smartCubeTrainingOrientation);
        }
        int[][] result = new int[physicalMask.length][];
        for (int i = 0; i < physicalMask.length; i++) {
            List<Integer> indices = new ArrayList<>();
            char maskChar = (char) ('A' + i);
            for (int j = 0; j < physicalMask[i].length(); j++) {
                if (physicalMask[i].charAt(j) == maskChar) {
                    indices.add(j);
                }
            }
            int[] equ = new int[indices.size()];
            for (int j = 0; j < indices.size(); j++) {
                equ[j] = indices.get(j);
            }
            result[i] = equ;
        }
        return result;
    }

    private static boolean isSolvedIgnoringRotation(String facelet) {
        if (facelet == null || facelet.length() < 54) {
            return false;
        }
        if (SOLVED_FACELET.equals(facelet)) {
            return true;
        }
        return Utils.getOrientationVariants(SOLVED_FACELET).contains(facelet);
    }

    private static int[][] toEqus(String facelet) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < facelet.length(); i++) {
            char color = facelet.charAt(i);
            if (color == '-') {
                continue;
            }
            List<Integer> indices = new ArrayList<>();
            for (int j = i; j < facelet.length(); j++) {
                if (facelet.charAt(j) == color) {
                    indices.add(j);
                }
            }
            if (indices.size() > 1) {
                int[] equ = new int[indices.size()];
                for (int j = 0; j < indices.size(); j++) {
                    equ[j] = indices.get(j);
                }
                result.add(equ);
            }
            facelet = facelet.replace(color, '-');
        }
        return result.toArray(new int[result.size()][]);
    }
}
