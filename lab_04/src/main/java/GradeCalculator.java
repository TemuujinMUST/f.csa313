public class GradeCalculator {
    public String letterGrade(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                    "Нийлбэр оноо 0-100 хооронд байх ёстой");
        }

        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public double totalScore(double att, double lab, double quiz1, double quiz2, double exam) {
        if (att < 0 || att > 10) {
            throw new IllegalArgumentException(
                    "Ирцийн оноо заавал 0-10 хооронд байх ёстой");
        }

        if (lab < 0 || lab > 40) {
            throw new IllegalArgumentException(
                    "Лаборатори болон бие даалтын оноо заавал 0-40 хооронд байх ёстой");
        }

        if (quiz1 < 0 || quiz1 > 10 || quiz2 < 0 || quiz2 > 10) {
            throw new IllegalArgumentException(
                    "Сорилын оноо заавал 0-10 хооронд байх ёстой");
        }

        if (exam < 0 || exam > 30) {
            throw new IllegalArgumentException(
                    "Шалгалтын оноо заавал 0-30 хооронд байх ёстой");
        }

        return att + lab + quiz1 + quiz2 + exam;
    }
}
