package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * DataFlowTargets.bonus (файлын 31-56-р мөр)-ийн өгөгдлийн урсгалын тестүүд.
 * Шат 1: ALL-DEFS (B1, B2). Шат 2: ALL-DU-PAIRS (B3-B8).
 * D1..D35 нь README.md-ийн DU хүснэгтийн хосын дугаар.
 */
class BonusTest {

    /**
     * 5 өдөр дараалан ирсэн, гэхдээ ирсэн өдрийн тоо (5) threshold (6)-аас бага:
     * score = 0, харин хамгийн урт цуваа 5 >= 5 тул +3 => 3.
     */
    @Test
    @DisplayName("B1: [1,1,1,1,1], threshold 6 -> 3. Covers D2-D4,D6,D8,D9,D12,D14-D16,D18,D20,D21,D25,D27,D29,D33,D34")
    void bonus_streak5BelowThreshold_returns3() {
        assertEquals(3, DataFlowTargets.bonus(new int[] { 1, 1, 1, 1, 1 }, 6));
    }

    /**
     * Тасалсан өдрийн дараа ирсэн: мөр 46 (streak = 0) ажиллаж, дараа нь 41-д
     * ашиглагдана.
     * present = 1 >= 0 => 5; хамгийн урт цуваа 1 < 5 => нэмэгдэхгүй => 5.
     */
    @Test
    @DisplayName("B2: [0,1], threshold 0 -> 5. Covers D2-D4,D6,D7,D9,D13,D17,D18,D20,D21,D28,D32,D34,D35")
    void bonus_absentThenPresent_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[] { 0, 1 }, 0));
    }

    // ---------------- 2: ALL-DU-PAIRS ----------------

    @Test
    @DisplayName("B3: null attendance -> IAE. Covers D1")
    void bonus_nullAttendance_throwsIAE() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 3));
    }

    @Test
    @DisplayName("B4: threshold -1 -> IAE. Covers D2,D5")
    void bonus_negativeThreshold_throwsIAE() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] { 1 }, -1));
    }

    @Test
    @DisplayName("B5: [], threshold 0 -> 5. Covers D2,D4,D6,D7,D10,D24,D32")
    void bonus_emptyThresholdZero_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("B6: [], threshold 1 -> 0. Covers D2,D4,D6,D8,D11,D24,D30")
    void bonus_emptyThresholdOne_returns0() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {}, 1));
    }

    @Test
    @DisplayName("B7: [1,0,1], threshold 2 -> 5. Covers D2-D4,D6,D7,D9,D12,D13,D15,D17-D21,D26,D28,D32,D34,D35")
    void bonus_streakBroken_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[] { 1, 0, 1 }, 2));
    }

    @Test
    @DisplayName("B8: [1,1,1,1,1], threshold 5 -> 8. Covers D2-D4,D6,D7,D9,D12,D13,D15,D16,D18,D20,D21,D25,D27,D31,D33,D34")
    void bonus_streak5AtThreshold_returns8() {
        assertEquals(8, DataFlowTargets.bonus(new int[] { 1, 1, 1, 1, 1 }, 5));
    }
}
