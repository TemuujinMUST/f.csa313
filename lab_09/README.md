# Лаборатори №9: Код хамралт — JaCoCo-гоор тестээ хөтлөх

- **Нэр:** Г. Тэмүүжин
- **Код:** B232270020

**java --version:**
```
java 17 2021-09-14 LTS
Java(TM) SE Runtime Environment (build 17+35-LTS-2724)
Java HotSpot(TM) 64-Bit Server VM (build 17+35-LTS-2724, mixed mode, sharing)
```

**mvn --version:**
```
Apache Maven 3.9.10 (5f519b97e944483d878815739f519b2eade0a91d)
Maven home: /opt/homebrew/Cellar/maven/3.9.10/libexec
Java version: 17, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
Default locale: en_MN, platform encoding: UTF-8
OS name: "mac os x", version: "26.6", arch: "aarch64", family: "mac"
```

## Хэсэг A: Happy path тестүүд ба эхний хэмжилт

**Тестүүд (5, зөвхөн хэвийн урсгал):**

| Тест | Шалгасан зүйл |
|---|---|
| `letterGrade_95_isA` | `letterGrade(95) == "A"` |
| `letterGrade_85_isB` | `letterGrade(85) == "B"` |
| `gpa_95and85_is3point5` | `gpa([95, 85]) == 3.5` |
| `scholarship_highGpaFullCredits_is100` | `scholarship(3.9, 16, false) == 100` |
| `scholarship_goodGpaEnoughCredits_is70` | `scholarship(3.5, 12, false) == 70` |

**Эхний хамралт** (`results/jacoco-A.csv`, `ScholarshipCalculator` класс):

| Хэмжүүр | COVERED | MISSED | Хамралт |
|---|---|---|---|
| Instruction | 99 | 50 | 99/149 ≈ 66.4% |
| Branch | 21 | 20 | 21/41 ≈ 51.2% |
| Line | 15 | 10 | 15/25 = 60.0% |
| Method | 4 | 0 | 4/4 = 100% |

**Шар/улаан мөрүүдийн ажиглалт:** Шар ромботой мөрүүд нь оролтын шалгалтууд (20, 31, 48-р мөр) ба `scholarship`-ийн `&&` нөхцөлүүд (52, 53-р мөр): тестүүд зөвхөн зөв оролт өгсөн тул `score < 0 || score > 100`, `scores == null || scores.length == 0`, `gpa < 0 || gpa > 4 || credits < 0` нөхцөлүүд хэзээ ч үнэн болоогүй, мөн кредит үргэлж хангалттай байсан тул `credits >= 15`, `credits >= 12`-ийн худал салаа хамрагдаагүй; мөн `hasViolation` (51-р мөр) зөвхөн `false`, `gpa`-ийн switch (36-р мөр) зөвхөн A, B дүнгээр явсан. Улаан (огт хүрээгүй) мөрүүд нь гурван алдааны зам — `throw new IllegalArgumentException` (21, 32, 49-р мөр), өөрөөр хэлбэл буруу оноо, null/хоосон массив, хязгаараас гадуурх голч/кредит, мөн `letterGrade`-ийн C/D/F буцаалт (25–27-р мөр), switch-ийн C/D салаа (39–40-р мөр), `scholarship`-ийн 50 ба сүүлийн 0 буцаалт (54–55-р мөр). Өөрөөр хэлбэл happy path тестүүд зөвхөн "сайн оюутан"-ы замыг хамарч, алдааны ба доод түвшний дүнгийн замуудыг бүрэн орхисон.
