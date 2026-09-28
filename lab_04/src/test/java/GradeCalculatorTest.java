import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GradeCalculatorTest {
    // Алхам 4: Нэгжийн тестүүд

    @Test
    @DisplayName("95 оноо A дүн байх ёстой")
    void ninetyFiveIsA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(95.0);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("85 оноо B дүн байх ёстой")
    void eightyFiveIsB() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(85.0);
        assertEquals("B", grade);
    }

    @Test
    @DisplayName("75 оноо C дүн байх ёстой")
    void seventyFiveIsC() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(75.0);
        assertEquals("C", grade);
    }

    @Test
    @DisplayName("65 оноо D дүн байх ёстой")
    void sixtyFiveIsD() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(65.0);
        assertEquals("D", grade);
    }

    @Test
    @DisplayName("30 оноо F дүн байх ёстой")
    void thirtyIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(30.0);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(90.0);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("89.99 оноо B дүн байх ёстой (хязгаарын тохиолдол)")
    void eightyNinePointNinetyNineIsB() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(89.99);
        assertEquals("B", grade);
    }

    @Test
    @DisplayName("60 оноо яг D дүн байх ёстой (хязгаарын тохиолдол)")
    void sixtyIsExactlyD() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(60.0);
        assertEquals("D", grade);
    }

    @Test
    @DisplayName("59.99 оноо F дүн байх ёстой (хязгаарын тохиолдол)")
    void fiftyNinePointNinetyNineIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(59.99);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("0 оноо F дүн байх ёстой")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(0.0);
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("100 оноо A дүн байх ёстой")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();
        String grade = calc.letterGrade(100.0);
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("-1 оноо оруулахад IllegalArgumentException үүсэх ёстой")
    void negativeScoreThrowsException() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.letterGrade(-1.0));
    }

    @Test
    @DisplayName("101 оноо оруулахад IllegalArgumentException үүсэх ёстой")
    void scoreAbove100ThrowsException() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.letterGrade(101.0));
    }

    @Test
    @DisplayName("att=-5 үед IllegalArgumentException үүсэх ёстой")
    void negativeAttendanceThrowsException() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.totalScore(-5, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("lab=41 үед IllegalArgumentException үүсэх ёстой")
    void labAboveMaximumThrowsException() {
        GradeCalculator calc = new GradeCalculator();
        assertThrows(
                IllegalArgumentException.class,
                () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    @Test
    @DisplayName("Бүх оноо дээд хязгаарт байх үед нийлбэр 100 байх ёстой")
    void maximumScoresShouldEqual100() {
        GradeCalculator calc = new GradeCalculator();
        double total = calc.totalScore(10, 40, 10, 10, 30);
        assertEquals(100.0, total);
    }
}
