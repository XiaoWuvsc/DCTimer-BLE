package scrambler;

import com.dctimer.model.SmartCubeTraining;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Smart333ScramblerTest {
    @Test
    public void smart333TrainingCategoriesGenerateScrambles() {
        int[] categories = {
                SmartCubeTraining.SMART_333_WCA,
                SmartCubeTraining.SMART_333_OLL,
                SmartCubeTraining.SMART_333_PLL,
                SmartCubeTraining.SMART_333_LAST_LAYER,
                SmartCubeTraining.SMART_333_F2L
        };

        for (int category : categories) {
            Scrambler scrambler = new Scrambler(null);

            scrambler.generateScramble(category, true);

            assertFalse(scrambler.getScramble().trim().isEmpty());
            assertTrue(scrambler.isSmart333Scramble());
        }
    }
}
