# Лабораторийн ажил №1 — Playwright

## Оюутны мэдээлэл

| Мэдээлэл | Утга |
|---|---|
| Оюутны нэр | **Г. Тэмүүжин** |
| Оюутны код | **B232270020** |
| Хичээл | Программ хангамжийн чанарын баталгаа ба туршилт |
| Лабораторийн ажил | №1 — Playwright Web Testing |

## Ашигласан веб сайт

Тест хийхдээ [SauceDemo](https://www.saucedemo.com/) веб сайтыг ашигласан.

## Хийсэн ажлууд

Энэ лабораторийн ажлаар Playwright ашиглан SauceDemo веб сайтад автомат тестүүд бичсэн.
Хэрэглэгчийн зөв username болон password ашиглан амжилттай нэвтрэх үйлдлийг шалгасан.
Мөн буруу password ашигласан үед алдааны мессеж гарч байгаа эсэхийг шалгасан.
Нэвтэрсний дараа shopping cart руу орох, Checkout товч харагдаж байгаа эсэх, Continue Shopping хийх болон Logout хийх үйлдлүүдийг шалгасан.
Playwright-ийн `getByRole()`, `getByPlaceholder()`, `getByText()` болон `locator()` зэрэг locator-уудыг ашиглан веб элементүүдийг сонгож ажиллуулсан.
Мөн `expect()` ашиглан URL, текст болон элементийн харагдах байдал зэрэг хүлээгдэж буй үр дүнг шалгасан.
Үүнээс гадна Playwright Codegen ашиглан тестийн үйлдлүүдийг автоматаар үүсгэж, үүссэн кодыг туршиж үзсэн.
Тестүүдийг Chromium, Firefox болон WebKit browser-ууд дээр ажиллуулж шалгасан.

## Ашигласан Playwright API

Лабораторийн ажлын явцад дараах Playwright API-уудыг ашигласан.

| API | Зориулалт | Жишээ |
|---|---|---|
| `page.goto()` | Веб хуудас руу орох | `page.goto(url)` |
| `getByPlaceholder()` | Input-ийг placeholder-аар сонгох | `getByPlaceholder('Username')` |
| `getByRole()` | Element-ийг role болон accessible name-аар сонгох | `getByRole('button', { name: 'Login' })` |
| `getByText()` | Текстээр element сонгох | `getByText('Products')` |
| `locator()` | CSS selector, ID, attribute ашиглан element сонгох | `locator('[data-test="checkout"]')` |
| `fill()` | Input-д утга оруулах | `.fill('standard_user')` |
| `click()` | Element дээр дарах | `.click()` |
| `expect()` | Тестийн үр дүнг шалгах | `expect(page).toHaveURL(...)` |
| `toHaveURL()` | URL шалгах | `toHaveURL(url)` |
| `toBeVisible()` | Element харагдаж байгаа эсэхийг шалгах | `toBeVisible()` |
| `toHaveText()` | Element-ийн текст шалгах | `toHaveText('Checkout')` |
| `toHaveClass()` | CSS class шалгах | `toHaveClass(...)` |

---

### Яагаад XPath ашиглаагүй вэ?

XPath ашиглаж болох боловч энэ лабораторийн ажлын хүрээнд XPath-аас аль болох зайлсхийсэн. Учир нь XPath нь HTML-ийн бүтэц, element-ийн байрлалаас хэт их хамааралтай selector үүсгэх магадлалтай. Жишээлбэл, HTML-ийн бүтэц өөрчлөгдвөл урт XPath selector ажиллахаа болих боломжтой.

Харин `getByRole()`, `getByText()`, `getByPlaceholder()` зэрэг locator-ууд нь element-ийг хэрэглэгч хэрхэн харж, ашиглаж байгаатай илүү ойр байдаг. Мөн SauceDemo дээр зориудаар өгөгдсөн `data-test` attribute-ууд нь тестийн element-үүдийг тогтвортой сонгоход тохиромжтой байсан.

---

## Playwright Codegen

Лабораторийн ажлын хүрээнд **Playwright Codegen** ашиглан веб дээр хийсэн үйлдлүүдээс тестийн код автоматаар үүсгэж туршсан.

Codegen ашиглан үүссэн кодын жишээ:

```ts
await page.locator('[data-test="username"]').click();
await page.locator('[data-test="username"]').fill('standard_user');
await page.locator('[data-test="password"]').click();
await page.locator('[data-test="password"]').fill('secret_sauce');
await page.locator('[data-test="login-button"]').click();
```

### Codegen болон өөрийн бичсэн кодын ялгаа

Codegen нь element-үүдийг ихэвчлэн `data-test` болон бусад locator-уудаар автоматаар сонгож, хийсэн үйлдэл бүрийг код болгон үүсгэдэг. Үүний улмаас зарим тохиолдолд шаардлагагүй `click()` зэрэг үйлдлүүд кодонд орсон байсан.

Өөрийн бичсэн тестүүдэд шаардлагатай үйлдлүүдийг сонгон авч, assertion-уудыг өөрөө нэмж өгсөн. Тиймээс Codegen нь тестийн эхний кодыг хурдан үүсгэхэд тохиромжтой боловч үүссэн кодыг шууд ашиглахын оронд шалгаж, шаардлагагүй хэсгийг цэвэрлэх хэрэгтэй гэж ажигласан.

---

## Playwright ба Selenium-ийн ялгааны талаарх ажиглалт

Playwright болон Selenium хоёулаа веб браузерийг автоматжуулж, end-to-end тест хийхэд ашиглагддаг.
Миний ажигласнаар Playwright нь тест бичихэд харьцангуй энгийн бөгөөд `getByRole()`, `getByText()`, `getByPlaceholder()` зэрэг бэлэн locator-уудтай байдаг.
Playwright нь элемент дээр үйлдэл хийхийн өмнө автоматаар хүлээх боломжтой тул олон тохиолдолд `wait`-ийг гараар бичих шаардлага бага байдаг.
Мөн Playwright өөрийн Test Runner-тэй учраас тест ажиллуулах, assertion хийх болон browser-ууд дээр тест ажиллуулах боломжууд нэг framework дотор байдаг.
Playwright нь Chromium, Firefox болон WebKit browser-уудыг дэмждэг бөгөөд browser-уудыг Playwright-аар суулгаж, тест хийх боломжтой байдаг.
Selenium нь W3C WebDriver стандарт дээр суурилдаг бөгөөд Java, Python, C#, JavaScript, Ruby зэрэг олон хэл дэмждэг нь давуу тал гэж үзэж байна.
Харин Playwright нь JavaScript/TypeScript, Python, Java болон .NET зэрэг хэлүүдийг албан ёсоор дэмждэг бөгөөд орчин үеийн веб тестийн framework хэлбэрээр илүү олон боломжийг нэг дор өгдөг.
Миний хувьд энэхүү лабораторийн ажлын явцад Playwright нь setup болон тест бичихэд хялбар, ойлгомжтой санагдсан бөгөөд ялангуяа автомат хүлээлт болон locator ашиглах боломж нь тест бичихэд тохиромжтой байсан.

## Тестүүд

Төслийн тестүүд:

* `амжилттай нэвтрэх` — зөв мэдээллээр нэвтрэх
* `буруу нууц үгээр нэвтрэх` — буруу password ашигласан үед алдаа шалгах
* `нэвтэрсний дараах үйлдэл` — shopping cart, checkout, logout зэрэг үйлдлүүдийг шалгах
* `codegen test` — Playwright Codegen ашиглан үүсгэсэн тест

## Тест ажиллуулах

Бүх тестийг ажиллуулах:

```bash
npx playwright test
```

Тодорхой тестийг ажиллуулах:

```bash
npx playwright test -g "амжилттай нэвтрэх"
```

Жишээ нь:

```bash
npx playwright test -g "буруу нууц үгээр нэвтрэх"
```

Тодорхой browser дээр ажиллуулах:

```bash
npx playwright test --project=chromium
```

```bash
npx playwright test --project=firefox
```

```bash
npx playwright test --project=webkit
```
