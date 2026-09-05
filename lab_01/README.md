# Playwright Web Testing Lab

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
