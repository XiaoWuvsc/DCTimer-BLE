package com.dctimer.model;

import com.dctimer.APP;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SmartCubeTrainingTest {
    private static final String SOLVED = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";

    @Test
    public void smart333UsesLastScrambleGroupIndex() {
        assertTrue(SmartCubeTraining.isSmart333(22 << 5));
        assertTrue(SmartCubeTraining.isSmart333((22 << 5) + 4));
        assertFalse(SmartCubeTraining.isSmart333((21 << 5) + 4));
        assertFalse(SmartCubeTraining.isTrainingOrientationMode(SmartCubeTraining.SMART_333_WCA));
        assertTrue(SmartCubeTraining.isTrainingOrientationMode(SmartCubeTraining.SMART_333_OLL));
    }

    @Test
    public void defaultTrainingOrientationIsYellowTopGreenFront() {
        APP.resetPref();

        assertEquals(13, APP.smartCubeTrainingOrientation);
    }

    @Test
    public void ollTrainingStopsWhenOllIsSolvedWithoutPll() {
        int originalOrientation = APP.smartCubeTrainingOrientation;
        try {
            APP.smartCubeTrainingOrientation = 0;
            char[] facelets = SOLVED.toCharArray();
            facelets[9] = 'L';
            facelets[36] = 'R';

            String pllUnsolved = new String(facelets);

            assertTrue(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_OLL, pllUnsolved));
            assertFalse(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_PLL, pllUnsolved));
        } finally {
            APP.smartCubeTrainingOrientation = originalOrientation;
        }
    }

    @Test
    public void f2lTrainingStopsWhenF2lIsSolvedWithoutLastLayer() {
        int originalOrientation = APP.smartCubeTrainingOrientation;
        try {
            APP.smartCubeTrainingOrientation = 0;
            char[] facelets = SOLVED.toCharArray();
            facelets[0] = 'R';

            String ollUnsolved = new String(facelets);

            assertTrue(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_F2L, ollUnsolved));
            assertFalse(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_OLL, ollUnsolved));
        } finally {
            APP.smartCubeTrainingOrientation = originalOrientation;
        }
    }

    @Test
    public void wcaPllAndLastLayerRequireSolvedState() {
        int originalOrientation = APP.smartCubeTrainingOrientation;
        try {
            APP.smartCubeTrainingOrientation = 0;
            char[] facelets = SOLVED.toCharArray();
            facelets[9] = 'L';
            facelets[36] = 'R';
            String pllUnsolved = new String(facelets);

            assertFalse(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_WCA, pllUnsolved));
            assertFalse(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_PLL, pllUnsolved));
            assertFalse(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_LAST_LAYER, pllUnsolved));
            assertTrue(SmartCubeTraining.isComplete(SmartCubeTraining.SMART_333_WCA, SOLVED));
        } finally {
            APP.smartCubeTrainingOrientation = originalOrientation;
        }
    }
}
