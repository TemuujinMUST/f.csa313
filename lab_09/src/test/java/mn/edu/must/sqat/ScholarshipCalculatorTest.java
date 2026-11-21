package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ScholarshipCalculatorTest {

    private ScholarshipCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new ScholarshipCalculator();
    }

    // Part A

    @Test
    void letterGrade_95_isA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGrade_85_isB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpa_95and85_is3point5() {
        assertEquals(3.5, calc.gpa(new double[] { 95, 85 }), 1e-9);
    }

    @Test
    void scholarship_highGpaFullCredits_is100() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_goodGpaEnoughCredits_is70() {
        assertEquals(70, calc.scholarship(3.5, 12, false));
    }

    // Part B

    @Test
    void letterGrade_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-5));
    }

    @Test
    void letterGrade_above100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(105));
    }

    @Test
    void letterGrade_75_isC() {
        assertEquals("C", calc.letterGrade(75));
    }

    @Test
    void letterGrade_65_isD() {
        assertEquals("D", calc.letterGrade(65));
    }

    @Test
    void letterGrade_50_isF() {
        assertEquals("F", calc.letterGrade(50));
    }

    @Test
    void gpa_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpa_emptyArray_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[] {}));
    }

    @Test
    void gpa_cdfGrades_is1() {
        assertEquals(1.0, calc.gpa(new double[] { 75, 65, 50 }), 1e-9);
    }

    @Test
    void scholarship_negativeGpa_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.5, 15, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.5, 15, false));
    }

    @Test
    void scholarship_negativeCredits_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    @Test
    void scholarship_withViolation_is0() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_highGpaLowCredits_is70() {
        assertEquals(70, calc.scholarship(3.9, 14, false));
    }

    @Test
    void scholarship_averageGpa_is50() {
        assertEquals(50, calc.scholarship(3.2, 12, false));
    }

    @Test
    void scholarship_goodGpaLowCredits_is50() {
        assertEquals(50, calc.scholarship(3.5, 10, false));
    }

    @Test
    void scholarship_lowGpa_is0() {
        assertEquals(0, calc.scholarship(2.5, 10, false));
    }

    // Part C

    @Test
    void gpa_loopZero_emptyAndNull_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[] {}));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpa_loopOne_singleScore_is4() {
        assertEquals(4.0, calc.gpa(new double[] { 95 }), 1e-9);
    }

    @Test
    void gpa_loopMany_fourScores_is2point5() {
        assertEquals(2.5, calc.gpa(new double[] { 95, 85, 75, 65 }), 1e-9);
    }

    // Part D

    @ParameterizedTest(name = "letterGrade({0}) = {1}")
    @CsvSource({
            "60,    D",
            "59.99, F",
            "69.99, D",
            "0,     F",
            "100,   A",
            "89.99, B",
            "90,    A"
    })
    void letterGrade_boundaryValues(double score, String expected) {
        assertEquals(expected, calc.letterGrade(score));
    }
}
