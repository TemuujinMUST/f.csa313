# Лаборатори №6: Category-Partition ба PICT

**Нэр:** Г. Тэмүүжин
**Код:** B232270020

---

## Хэсэг A: Category-Partition (Шилжүүлгийн функц)

Тестлэгдэх функц: `POST /transfer { "from": ID, "to": ID, "amount": NUM, "currency": CODE }`

### Алдааны давуу эрэмбэ

Олон алдаа зэрэг гарвал: NO_FROM → FROZEN → NO_TO → SAME_ACCOUNT → BAD_AMOUNT → INSUFFICIENT → LIMIT гэсэн дарааллаар эхний таарсан алдааг буцаана, учир нь эхлээд оролтын данснуудыг (from, to), дараа нь дүнг, хамгийн сүүлд үлдэгдэл ба лимитийн нөхцөлийг шалгах нь тодорхойлолтын дүрмүүдийн дарааллтай нийцнэ.

### A1: Сонголт ба төлөөлөх утгууд

| Параметр / сонголт | Төлөөлөх утгууд |
|---|---|
| **from** | Valid (хүчинтэй, царцаагдаагүй) · Frozen (царцаагдсан) · Missing (байхгүй) |
| **to** | Valid · Missing (байхгүй) · SameAsFrom (from-тэй ижил) |
| **amount** | Negative (< 0) · Zero (= 0) · Normal · ExactBalance (үлдэгдэлтэй тэнцүү) · OverBalance (үлдэгдлээс их) · OverLimit (лимитээс их) |
| **currency** | Same (дансныхтай ижил) · Different (өөр, ханшийн хөрвүүлэлттэй) |
| **Далд орчин: өдрийн лимит өмнө нь хэр ашиглагдсан** | Empty (ашиглаагүй) · Partial (хэсэгчлэн) · Full (дүүрсэн) |

**Тайлбар:**
- Далд орчны сонголт нь хүсэлтийн биед байхгүй боловч үр дүнд нөлөөлнө: жишээ нь Normal дүн нь лимит Empty үед амжилттай, Full үед `ERROR_LIMIT` өгнө.
- Negative ба Zero нь тусдаа утга: хоёулаа `ERROR_BAD_AMOUNT` өгөх ч хязгаарын (boundary) өөр өөр утга.
- ExactBalance нь хязгаарын тохиолдол: дүрэм "үлдэгдлээс **хэтрэхгүй**" гэсэн тул үлдэгдэлтэй тэнцүү дүн амжилттай байх ёстой.
- currency нь Different үед үлдэгдэл, лимитийг хөрвүүлсний дараах дүнгээр шалгана (OverBalance, OverLimit-ийг хөрвүүлсний дараа тооцно).

### A2: Хязгаарлалтууд

**Тэмдэглэгээ:** `[ERROR]` = бусад сонголтоос үл хамаарч алдаа өгнө, нэг тест хангалттай. `[SINGLE]` = амжилттай захын тохиолдол, ганц тест хангалттай. `[IF]` / `[property]` = өөр утгаас хамаарна.

**Тохиролцоо:** ERROR болон SINGLE тест бүрд нэг утгаас бусад сонголтыг анхдагч утгаар авна: from = Valid, to = Valid, amount = Normal, currency = Same, лимит = Empty.

| Сонголт | Утга | Тэмдэглэгээ | Үндэслэл |
|---|---|---|---|
| from | Valid | — | Happy path-ийн үндсэн утга. |
| | Frozen | [ERROR] | Царцаагдсан данснаас шилжүүлэх нь бусад сонголтоос үл хамааран `ERROR_FROZEN` өгнө. |
| | Missing | [ERROR] | Байхгүй from нь бусад сонголтоос үл хамааран `ERROR_NO_FROM` өгнө. |
| to | Valid | — | Happy path-ийн үндсэн утга. |
| | Missing | [ERROR] | Байхгүй to нь бусад сонголтоос үл хамааран `ERROR_NO_TO` өгнө. |
| | SameAsFrom | [ERROR] | from-тэй ижил данс нь бусад сонголтоос үл хамааран `ERROR_SAME_ACCOUNT` өгнө. |
| amount | Negative | [ERROR] | Сөрөг дүн нь `ERROR_BAD_AMOUNT` өгнө, бусад сонголт нөлөөлөхгүй. |
| | Zero | [ERROR] | 0 нь `amount ≤ 0` дүрмийн хязгаар тул Negative-ээс тусад нь авсан, `ERROR_BAD_AMOUNT` өгнө. |
| | Normal | — | Үлдэгдэл, лимитэд багтах энгийн дүн, happy path-д хэрэглэнэ. |
| | ExactBalance | [IF converted] [SINGLE] | Үлдэгдэлтэй тэнцүү дүн нь амжилттай захын тохиолдол тул нэг тест хангалттай, үлдэгдлийг хөрвүүлсний дараах дүнгээр шалгадаг тул currency = Different-тэй л хослуулна. |
| | OverBalance | [ERROR] | Үлдэгдлээс их дүн нь `ERROR_INSUFFICIENT` өгнө. |
| | OverLimit | [ERROR] | Өдрийн лимитээс их дүн нь `ERROR_LIMIT` өгнө. |
| currency | Same | — | Хөрвүүлэлтгүй үндсэн тохиолдол. |
| | Different | [property converted] | Ханшийн хөрвүүлэлт хийгдэх шинж чанар тул ExactBalance-ийн нөхцөл болгон ашиглана. |
| Лимит ашигласан байдал (далд) | Empty | — | Анхдагч утга, лимит бүрэн үлдсэн. |
| | Partial | [SINGLE] | Хуримтлагдсан ашиглалтыг тооцож амжилттай болдог эсэхийг нэг удаа шалгахад хангалттай. |
| | Full | [ERROR] | Лимит дүүрсэн үед эерэг дүн бүр `ERROR_LIMIT` өгнө, OverLimit-аас өөр механизм тул тусад нь хадгална. |

### "N → M" тооцоо

**Бүрэн олонлог:** from 3 × to 3 × amount 6 × currency 2 × лимит 3 = **324**

| Алхам | Тооцоо | Тоо |
|---|---|---|
| Бүрэн олонлог | 3 × 3 × 6 × 2 × 3 | 324 |
| [ERROR] хэрэглэсний дараа | ERROR утга бүр 1 тест: Frozen, Missing(from), Missing(to), SameAsFrom, Negative, Zero, OverBalance, OverLimit, Full = 9. Үлдсэн хүчинтэй утгууд: 1 × 1 × 2 (Normal, ExactBalance) × 2 (currency) × 2 (Empty, Partial) = 8. | 9 + 8 = 17 |
| [SINGLE] хэрэглэсний дараа | ExactBalance-ийн 4 хослол → 1 (−3); Partial-ийн Normal-той 2 хослол → 1 (−1) | 17 − 4 = 13 |
| [IF] хэрэглэсний дараа | ExactBalance-ийн ганц тест currency = Different-тэй болно, тоо өөрчлөгдөхгүй | 13 |

**324 → 13**

Үлдсэн 13 тест: 9 ERROR + 2 happy path (Normal, Same/Different, Empty) + ExactBalance (Different) + Partial (Normal).
