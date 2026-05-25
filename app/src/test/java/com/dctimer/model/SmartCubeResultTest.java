package com.dctimer.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SmartCubeResultTest {
    private static final String SOLVED = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";
    private static final SmartCube.CompletionChecker NEVER_COMPLETE = new SmartCube.CompletionChecker() {
        @Override
        public boolean isComplete(String cubeState) {
            return false;
        }
    };

    @Test
    public void ganResultUsesDeviceMoveDeltasWithoutScaling() {
        SmartCube cube = new SmartCube();
        cube.setType(BLEDevice.TYPE_GANI_CUBE);
        cube.setCubeState(SOLVED);

        cube.applyMove(0, 0, null, NEVER_COMPLETE);
        cube.markSolveStarted(SOLVED);
        cube.applyMove(3, 500, null, NEVER_COMPLETE);
        cube.applyMove(6, 700, null, NEVER_COMPLETE);

        cube.calcResult();

        assertEquals(1200, cube.getResult());
    }

    @Test
    public void resetSolveTrackingKeepsCurrentCubeStateForTrainingPhases() {
        SmartCube cube = new SmartCube();
        cube.setCubeState(SOLVED);

        cube.applyMove(0, 0, null, NEVER_COMPLETE);
        String phaseCompleteState = cube.getCubeState();

        cube.resetSolveTracking();

        assertNotEquals(SOLVED, phaseCompleteState);
        assertEquals(phaseCompleteState, cube.getCubeState());
    }
}
