package com.dctimer.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SmartCubeOrientationTest {
    private static final String SOLVED = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";

    @Test
    public void defaultOrientationKeepsFaceletStateAndMovesUnchanged() {
        assertEquals(SOLVED, Utils.orientFacelets(SOLVED, 0));
        for (int move = 0; move < 18; move++) {
            assertEquals(move, Utils.orientSmartCubeMove(move, 0));
        }
    }

    @Test
    public void moveUnorientationRoundTripsMoves() {
        for (int orientation = 0; orientation < Utils.SMART_CUBE_ORIENTATION_FACES.length; orientation++) {
            for (int move = 0; move < 18; move++) {
                int physicalMove = Utils.unorientSmartCubeMove(move, orientation);
                assertEquals(move, Utils.orientSmartCubeMove(physicalMove, orientation));
            }
        }
    }
}
