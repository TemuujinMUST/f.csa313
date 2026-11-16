package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {
    @Test
    @DisplayName("G1: gcd(7,0)=7, 0 iterations. Covers P2 (x 13->20), P6 (y 13->15F)")
    void gcd_yIsZero_skipsLoopAndReturnsX() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2: gcd(12,8)=4, 2 iterations. Covers P1,P3,P4,P5,P7,P8,P9,P10,P11,P12,P13")
    void gcd_twoIterations_returnsGreatestCommonDivisor() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G3: gcd(0,5)=5, 1 iteration. Covers P1,P4,P5,P7,P8,P10,P13")
    void gcd_xIsZero_oneIterationReturnsY() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
