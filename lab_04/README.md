# Лаборатори №4: Нэгжийн тестийн эхлэл - JUnit 5

## Оюутны мэдээлэл

**Оюутны нэр:** Г. Тэмүүжин
**Оюутны код:** B232270020

## Java болон Maven хувилбар

### `java -version`

```text
java 17 2021-09-14 LTS
Java(TM) SE Runtime Environment (build 17+35-LTS-2724)
Java HotSpot(TM) 64-Bit Server VM (build 17+35-LTS-2724, mixed mode, sharing)
```

### `mvn -version`

```text
Apache Maven 3.9.10 (5f519b97e944483d878815739f519b2eade0a91d)
Maven home: /opt/homebrew/Cellar/maven/3.9.10/libexec
Java version: 17, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
Default locale: en_MN, platform encoding: UTF-8
OS name: "mac os x", version: "26.6", arch: "aarch64", family: "mac"
```

## Тестийн тоо

32

`GradeCalculatorTest.java` файлд нийт **18 тестийн метод** байна.

* `@Test` — 16 метод
* `@ParameterizedTest` — 2 метод

`mvn test` командыг ажиллуулахад `results/mvn-test.txt` файлд:

```text
Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
```

гэж гарсан. Тестийн методын тоо 18 боловч `@ParameterizedTest` нь `@CsvSource` доторх мөр бүрийг тусдаа тестийн тохиолдол болгон ажиллуулдаг тул нийт ажилласан тестийн тоо 32 болсон.

## Мутацийн тестийн үр дүн

Мутацийн хувилбарыг ажиллуулахад нийт 32 тестээс **2 тест унасан**:

* `GradeCalculatorTest.ninetyIsExactlyA`
* `GradeCalculatorTest.letterGradeBoundaries(double, String)[2]`

Эдгээр тестүүдийн хүлээгдэж буй үр дүн нь `90` оноо `A` байх боловч мутацид орсон код `90` оноог `B` гэж буцаасан. Энэ нь `90` онооны хязгаарын нөхцөлийг шалгасан тестүүд тухайн алдааг зөв илрүүлж чадсаныг харуулж байна.

## Дүгнэлт

Энэ лабораторийн ажлаар JUnit 5 ашиглан `GradeCalculator` классын нэгжийн тестүүдийг бичсэн. Ердийн утга болон хязгаарын утгуудыг шалгахын зэрэгцээ буруу оролтын үед `IllegalArgumentException` үүсэж байгаа эсэхийг шалгасан. Мөн ижил логиктой олон тестийг `@ParameterizedTest` болон `@CsvSource` ашиглан нэгтгэн бичсэн. `totalScore` методын зөв нийлбэр болон буруу оролтын тохиолдлуудыг мөн шалгасан. Хамгийн сонирхолтой алдаа нь `90` онооны хязгаарын нөхцөл байсан бөгөөд мутацид орсон код `90` оноог `B` гэж буцаахад миний тестүүд энэ алдааг илрүүлсэн. Энэ нь boundary value testing нь онооны ангиллын зааг дээрх алдааг илрүүлэхэд үр дүнтэй болохыг харуулсан.
