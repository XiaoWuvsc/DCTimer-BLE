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

    @Test
    public void orientedMoveMatchesOrientedFaceletTransition() {
        String probe = Utils.applySmartCubeMove(SOLVED, 0);
        probe = Utils.applySmartCubeMove(probe, 3);
        probe = Utils.applySmartCubeMove(probe, 6);
        probe = Utils.applySmartCubeMove(probe, 10);

        for (int orientation = 0; orientation < Utils.SMART_CUBE_ORIENTATION_FACES.length; orientation++) {
            for (int move = 0; move < 18; move++) {
                String expectedToState = Utils.orientFacelets(Utils.applySmartCubeMove(probe, move), orientation);
                String displayFromState = Utils.orientFacelets(probe, orientation);
                int displayMove = Utils.orientSmartCubeMove(move, orientation);

                assertEquals(expectedToState, Utils.applySmartCubeMoveGeometry(displayFromState, displayMove));
            }
        }
    }

    @Test
    public void faceletOrientationRoundTrips() {
        String probe = Utils.applySmartCubeMove(SOLVED, 0);
        probe = Utils.applySmartCubeMove(probe, 3);
        probe = Utils.applySmartCubeMove(probe, 6);
        probe = Utils.applySmartCubeMove(probe, 10);

        for (int orientation = 0; orientation < Utils.SMART_CUBE_ORIENTATION_FACES.length; orientation++) {
            assertEquals(probe, Utils.unorientFacelets(Utils.orientFacelets(probe, orientation), orientation));
        }
    }
}
