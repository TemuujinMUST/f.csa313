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

### A3: Спецификацийн хүснэгт

Статус: амжилттай бол `201 {"result":"OK"}`, оролтын алдаа бол `200 {"result":"ERROR_..."}`. Бусад сонголтыг анхдагч утгаар (from = Valid, to = Valid, amount = Normal, currency = Same, лимит = Empty) авсан.

| № | from | to | amount | currency | Лимит | Төрөл | Хүлээгдэх үр дүн |
|---|---|---|---|---|---|---|---|
| 1 | Valid | Valid | Normal | Same | Empty | Happy path | 201 `OK` |
| 2 | Valid | Valid | Normal | Different | Empty | Happy path (хөрвүүлэлттэй) | 201 `OK` |
| 3 | Valid | Valid | ExactBalance | Different | Empty | SINGLE (захын тохиолдол) | 201 `OK` |
| 4 | Valid | Valid | Normal | Same | Partial | SINGLE (хуримтлагдсан лимит) | 201 `OK` |
| 5 | **Frozen** | Valid | Normal | Same | Empty | ERROR | 200 `ERROR_FROZEN` |
| 6 | **Missing** | Valid | Normal | Same | Empty | ERROR | 200 `ERROR_NO_FROM` |
| 7 | Valid | **Missing** | Normal | Same | Empty | ERROR | 200 `ERROR_NO_TO` |
| 8 | Valid | **SameAsFrom** | Normal | Same | Empty | ERROR | 200 `ERROR_SAME_ACCOUNT` |
| 9 | Valid | Valid | **Negative** | Same | Empty | ERROR | 200 `ERROR_BAD_AMOUNT` |
| 10 | Valid | Valid | **Zero** | Same | Empty | ERROR (хязгаар) | 200 `ERROR_BAD_AMOUNT` |
| 11 | Valid | Valid | **OverBalance** | Same | Empty | ERROR | 200 `ERROR_INSUFFICIENT` |
| 12 | Valid | Valid | **OverLimit** | Same | Empty | ERROR | 200 `ERROR_LIMIT` |
| 13 | Valid | Valid | Normal | Same | **Full** | ERROR (далд орчин) | 200 `ERROR_LIMIT` |
| 14 | **Frozen** | **Missing** | Normal | Same | Empty | Давхар нөхцөл | 200 `ERROR_FROZEN` |
| 15 | Valid | **SameAsFrom** | **Zero** | Same | Empty | Давхар нөхцөл | 200 `ERROR_SAME_ACCOUNT` |
| 16 | Valid | Valid | **OverBalance** | Same | **Full** | Давхар нөхцөл | 200 `ERROR_INSUFFICIENT` |

**Давхар нөхцөлийн үндэслэл** (дээрх давуу эрэмбээр):
- **14:** from Frozen ба to Missing хоёулаа алдаатай. FROZEN нь NO_TO-оос өмнө шалгагддаг тул `ERROR_FROZEN` буцна.
- **15:** to нь SameAsFrom, amount нь Zero хоёулаа алдаатай. Дансны шалгалт (SAME_ACCOUNT) дүнгийн шалгалтаас (BAD_AMOUNT) өмнө байдаг тул `ERROR_SAME_ACCOUNT` буцна.
- **16:** дүн нь үлдэгдлээс их, лимит нь дүүрсэн. INSUFFICIENT нь LIMIT-ээс өмнө шалгагддаг тул `ERROR_INSUFFICIENT` буцна.

**Тооны тайлбар:** A2-ын 13 тест (мөр 1-13) дээр 3 давхар нөхцөлийн мөр (14-16) нэмсэн, нийт 16 спецификаци. A2-ын "324 → 13" тоо нь зөвхөн нэг алдаатай тестүүдийг тоолсон тул давхар нөхцөлийн мөрүүд түүнд ороогүй.

## Хэсэг B: PICT

### B1: Browser жишээ

Файл: `browser.model`. Үр дүн: `results/browser-2way.txt`, `results/browser-3way.txt`.

| Хамралт | Мөрийн тоо | Файл |
|---|---|---|
| Бүрэн олонлог (3 × 3 × 2 × 2 × 2 × 2) | 144 | — |
| 2-way | 10 | results/browser-2way.txt |
| 3-way (`/o:3`) | 20 | results/browser-3way.txt |

**2-way ба greedy:** Лекц дээр гараар 9 мөрөнд багтааж байсан бол PICT 10 мөр өгсөн. Content × Cookies хоёр сонголт тус бүр 3 утгатай тул 9 хос байх ба нэг мөр нэг хосыг л хамардаг тул 9 нь доод хязгаар юм. PICT нь greedy алгоритм хэрэглэдэг: мөр бүрд одоогоор хамраагүй хамгийн олон хосыг хамарсан мөрийг сонгодог тул хамгийн цөөн мөрийг заавал өгдөггүй, харин хурдан, хангалттай багахан массив олдог. Хамгийн цөөн мөртэй covering array олох нь тооцооллын хувьд хэцүү (NP-hard) тул практикт ойролцоо шийдэл хангалттай гэж үздэг.

**3-way:** 3-way нь 20 мөр буюу бүрэн олонлог 144-ийн ойролцоогоор 14%. Хосын оронд гурвалыг хамруулсан тул 2-way-ийн 10 мөрөөс 2 дахин олон боловч бүрэн олонлогоос хамаагүй цөөн хэвээр байна.

### B2: Шилжүүлгийн модель PICT-ээр

Файлууд: `transfer-nc.model`, `transfer.model`, `transfer-neg.model`. Үр дүн: `results/transfer-nc.txt`, `results/transfer.txt`, `results/transfer-neg.txt`.

| Хувилбар | Мөрийн тоо | Happy path мөр | Нэг мөр дэх алдааны тоо (мөрийн тоо) |
|---|---|---|---|
| transfer-nc (хязгаарлалтгүй) | 19 | 0 | 1: 3 · 2: 7 · 3: 7 · 4: 2 |
| transfer (A2 хязгаарлалттай) | 21 | 0 | 1: 6 · 2: 6 · 3: 9 |
| transfer-neg (`~` сөрөг утгатай) | 25 | 5 | 0: 5 · 1: 20 |

**transfer.model-ийн хязгаарлалтууд:** ExactBalance нь хөрвүүлсний дараах дүнгээр шалгагддаг ([IF converted]) тул currency = Different-тэй л хослуулсан. Байхгүй данстай (Missing) үед дүн утга алдагддаг тул Amount = Normal болгосон (лабын жишээний дагуу). from байхгүй үед to нь from-тэй ижил байх боломжгүй тул SameAsFrom-ыг хориглосон.

**Ажиглалт:**
- transfer-nc ба transfer-д happy path мөр **нэг ч гараагүй**: PICT хос бүрийг хамарсан боловч бүх утга хүчинтэй мөрийг шаарддаггүй. Бүх мөр нь 1-4 алдаатай тул error masking гарч, хамгийн эхний алдаа бусдыг нууна. Тухайлбал "Frozen, Missing, Normal, Same, Full" мөрөнд 3 алдаа зэрэг байгаа ба давуу эрэмбээр `ERROR_FROZEN` буцна, бусад хоёр нь шалгагдахгүй.
- transfer (21) нь transfer-nc (19)-ээс илүү мөртэй: хязгаарлалт нь зарим хосыг боломжгүй болгосон ч PICT greedy тул мөрийн тоо багасахгүй байж болдог.
- transfer-neg-д мөр бүрд хамгийн ихдээ нэг сөрөг утга (20 мөр нэг сөрөг, 5 мөр сөрөггүй) тул алдаа бүр тусдаа шалгагдаж, happy path мөр 5 гарсан. Энэ нь category-partition-ий [ERROR] тэмдэглэгээтэй дүйнэ.

**`~` хэрэглэхэд A2-ын хязгаарлалт хэрэгтэй хэвээр үү?** [ERROR]-той холбоотой хязгаарлалт (Missing үед Amount = Normal гэх мэт) хэрэггүй болсон, учир нь `~` нь нэг мөрөнд ганц алдаа л байлгадаг. Харин [IF] төрлийн хязгаарлалт хэрэгтэй хэвээр: ExactBalance нь Different currency-тэй л байх ёстой тул `IF [Amount] = "ExactBalance" THEN [Currency] = "Different";` мөрийг transfer-neg.model-д үлдээсэн.

### B2: Бодит тест кейс

| № | Эх мөр | Setup | Оролт (POST /transfer) | Oracle |
|---|---|---|---|---|
| 1 | transfer-neg: Valid, Valid, Normal, Same, Empty (Happy path) | ACC-A: идэвхтэй, үлдэгдэл 1000 MNT; ACC-B: идэвхтэй; өдрийн лимит 5000, өнөөдөр ашиглаагүй | `{"from":"ACC-A","to":"ACC-B","amount":100,"currency":"MNT"}` | 201, `result = OK` |
| 2 | transfer-neg: ~Frozen, Valid, Normal, Same, Empty (1 алдаа) | ACC-A: **царцаагдсан**, үлдэгдэл 1000 MNT; ACC-B: идэвхтэй; лимит ашиглаагүй | `{"from":"ACC-A","to":"ACC-B","amount":100,"currency":"MNT"}` | 200, `result = ERROR_FROZEN` |
| 3 | transfer: Frozen, Missing, Normal, Same, Full (3 алдаа) | ACC-A: **царцаагдсан**, 1000 MNT; ACC-B бүртгээгүй; лимит **дүүрсэн** | `{"from":"ACC-A","to":"ACC-X","amount":100,"currency":"MNT"}` | 200, `result = ERROR_FROZEN` (давуу эрэмбээр FROZEN нь NO_TO, LIMIT-ээс өмнө) |

## Дүгнэлт

Гараар хийсэн A3 хүснэгт алдааны семантикийг илүү сайн барьдаг: мөр бүр тодорхой зорилготой, нэг алдаатай, хүлээгдэх статус ба `result`-тай бөгөөд давхар нөхцөлийн мөрүүдийг (14-16) давуу эрэмбэд үндэслэн бичсэн. PICT-ийн гаралт зөвхөн оролтын хослол өгдөг, хүлээгдэх үр дүн (oracle) өгдөггүй тул "Frozen, Missing, Normal, Same, Full" мэт олон алдаатай мөрийн үр дүнг давуу эрэмбээр гараар тогтоох шаардлагатай болсон. Хязгаарлалтгүй transfer-nc (19 мөр) болон transfer (21 мөр) загварт happy path мөр нэг ч гараагүй, мөр бүрд 1-4 алдаа орсон тул эхний алдаа бусдыг нууж (error masking), алдаа бүр бие даан шалгагдахгүй байв. `~` хэрэглэсэн transfer-neg (25 мөр) нь мөр бүрд хамгийн ихдээ нэг алдаа оруулж, 5 happy path мөр гаргаснаар энэ асуудлыг шийдсэн. Хосын хамралтыг PICT баталгаажуулдаг: transfer-nc нь 111 хосыг бүгдийг нь 19 мөрөөр хамарсан бол миний 16 мөртэй A3 хүснэгт 111 хосын 61-ийг (55%) л хамарсан тул хосын хамралтыг баталгаажуулж чаддаггүй. Гэхдээ transfer-neg-ийн хамрал зориуд сул (111 хосын 82), учир нь `~` утгуудыг хооронд нь хослуулдаггүй, энэ нь хамрал ба алдааны тусгаарлалтын хоорондын солилцоо юм. Мөн PICT greedy алгоритм хэрэглэдэг тул хамгийн цөөн мөр олдоггүй: хязгаарлалттай transfer (21) нь хязгаарлалтгүй transfer-nc (19)-аас илүү мөртэй гарсан. Гараар бичсэн хүснэгт хязгаарын утгуудыг (ExactBalance, Zero) санаатайгаар сонгодог бол PICT тэдгээрийг зөвхөн сонголтын жагсаалтад байвал л хамруулна. Иймээс хоёр арга бие биеийг орлохгүй, харин нөхдөг: category-partition-аар сонголт, ERROR/SINGLE/IF хязгаарлалтыг гаргаж, PICT-ээр `~`-тай хослол үүсгэж, oracle-ыг гараар тогтоох нь хамгийн сайн нийлбэр байлаа. Хамгийн их бодол шаардсан алхам нь A2-ын хязгаарлалтууд ба алдааны давуу эрэмбийг тогтоох байлаа, учир нь тэр нь A3 хүснэгт болон гурван PICT моделийн бүтцийг бүгдийг нь тодорхойлсон.
