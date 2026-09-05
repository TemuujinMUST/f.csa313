import { test, expect } from '@playwright/test'

// Хэрэглэгчийн амжилттай нэвтрэлтийг шалгах тест
test('амжилттай нэвтрэх', async ({ page }) => {
  await page.goto('https://www.saucedemo.com'); // Сайтын линк рүү орох. Жишээ нь: SSL, URL хүчинтэй эсэхийг шалгах
  await page.getByPlaceholder('Username').fill('standard_user'); // Input элементэд утга оруулах 
  await page.getByPlaceholder('Password').fill('secret_sauce'); // Input элементэд утга оруулах
  await page.getByRole('button', { name: 'Login' }).click(); // 'Login' button элемент дээр click буюу дарах үйлдэл хийх
  await expect(page.getByText('Products')).toBeVisible(); // Login хийж орсны дараах хуудсанд Products гэдэг текст байгаа эсэхийг шалгах
  await expect(page).toHaveURL('https://www.saucedemo.com/inventory.html'); // Login хийж орсны дараах хуудасны url нь таарч байгаа эсэхийг шалгах
  const locator = page.locator('.app_logo'); // app_logo гэдэг class-тай элементийг locator нэртэй хувьсагчид оноох
  await expect(locator).toHaveText('Swag Labs'); // locator элемент ийм текст агуулж байгаа эсэхийг шалгах
  await page.getByRole('button', { name: 'Open menu' }).click(); // 'Open menu' button элемент дээр click буюу дарах үйлдэл хийх
  await page.locator('#logout_sidebar_link').click(); // logout_sidebar_link гэдэг id-тай элемент дээр click буюу дарах үйлдэл хийх
  await expect(page).toHaveURL('https://www.saucedemo.com'); // Logout хийж гарсны дараах хуудасны url нь таарч байгаа эсэхийг шалгах
  await expect(page.getByText('Accepted usernames are')).toBeVisible(); // Logout хийж гарсны дараах хуудас ийм текст агуулж байгаа эсэхийг шалгах
})

// Хэрэглэгчийн буруу нууц үгээр нэвтрэлтийг шалгах тест
test('буруу нууц үгээр нэвтрэх', async ({ page }) => {
  await page.goto('https://www.saucedemo.com'); // Сайтын линк рүү орох
  await page.getByPlaceholder('Username').fill('standard_user'); // Username input элементэд хэрэглэгчийн нэр оруулах
  await page.getByPlaceholder('Password').fill('wrong_password'); // Password input элементэд буруу нууц үг оруулах
  await page.getByRole('button', { name: 'Login' }).click(); // 'Login' button элемент дээр click буюу дарах үйлдэл хийх
  await expect(page.getByText('Username and password do not match any user in this service')).toBeVisible(); // Буруу нууц үг оруулсан үед алдааны мессеж харагдаж байгаа эсэхийг шалгах
  await expect(page).toHaveURL('https://www.saucedemo.com'); // Буруу нууц үгээр нэвтэрсэн үед login хуудасны URL хэвээр байгаа эсэхийг шалгах
  const locator = page.locator('#password'); // password id-тай input элементийг locator нэртэй хувьсагчид оноох
  await expect(locator).toHaveClass('input_error form_input error'); // Password input элемент алдааны үед зөв class-уудтай болсон эсэхийг шалгах
})

// Хэрэглэгчийн нэвтэрсний дараах алхамыг шалгах тест
test('нэвтэрсний дараах үйлдэл', async ({ page }) => {
  await page.goto('https://www.saucedemo.com'); // Сайтын линк рүү орох
  await page.getByPlaceholder('Username').fill('standard_user'); // Username input элементэд хэрэглэгчийн нэр оруулах
  await page.getByPlaceholder('Password').fill('secret_sauce'); // Password input элементэд зөв нууц үг оруулах
  await page.getByRole('button', { name: 'Login' }).click(); // 'Login' button элемент дээр click буюу дарах үйлдэл хийх
  await expect(page).toHaveURL('https://www.saucedemo.com/inventory.html'); // Амжилттай нэвтэрсний дараа inventory хуудасны URL руу орсон эсэхийг шалгах
  await page.locator('[data-test="shopping-cart-link"]').click(); // Shopping cart link дээр click хийж сагсны хуудас руу орох
  await expect(page).toHaveURL('https://www.saucedemo.com/cart.html'); // Shopping cart дээр дарсны дараа cart хуудасны URL зөв эсэхийг шалгах
  await expect(page.getByRole('button', { name: 'Checkout' })).toHaveText('Checkout'); // Checkout button элемент 'Checkout' гэсэн тексттэй байгаа эсэхийг шалгах
  await page.getByRole('button', { name: 'Continue shopping' }).click(); // 'Continue shopping' button дээр дарж бүтээгдэхүүний хуудас руу буцах
  await page.getByRole('button', { name: 'Open menu' }).click(); // 'Open menu' button дээр дарж хажуугийн цэсийг нээх
  await page.locator('#logout_sidebar_link').click(); // Logout link дээр дарж системээс гарах
  await expect(page).toHaveURL('https://www.saucedemo.com'); // Logout хийсний дараа login хуудасны URL руу буцсан эсэхийг шалгах
  await page.getByText('Accepted usernames are'); // Logout хийсний дараах login хуудсанд 'Accepted usernames are' гэсэн текст байгаа эсэхийг шалгах
})

// Codegen-р бичүүлсэн шалгах тест
test('codegen test', async ({ page }) => {
  await page.goto('https://www.saucedemo.com/'); // Сайтын үндсэн хуудас руу орох
  await page.locator('[data-test="username"]').click(); // Username input элемент дээр click хийх
  await page.locator('[data-test="username"]').fill('standard_user'); // Username input элементэд хэрэглэгчийн нэр оруулах
  await page.locator('[data-test="password"]').click(); // Password input элемент дээр click хийх
  await page.locator('[data-test="password"]').fill('secret_sauce'); // Password input элементэд зөв нууц үг оруулах
  await page.locator('[data-test="login-button"]').click(); // Login button дээр click хийж системд нэвтрэх
  await page.locator('[data-test="add-to-cart-sauce-labs-backpack"]').click(); // Sauce Labs Backpack бүтээгдэхүүнийг сагсанд нэмэх
  await page.locator('[data-test="shopping-cart-link"]').click(); // Shopping cart link дээр дарж сагсны хуудас руу орох
  await page.locator('[data-test="checkout"]').click(); // Checkout button дээр дарж захиалгын мэдээлэл оруулах хуудас руу орох
  await page.locator('[data-test="firstName"]').click(); // First Name input элемент дээр click хийх
  await page.locator('[data-test="lastName"]').click(); // Last Name input элемент дээр click хийх
  await page.locator('[data-test="postalCode"]').click(); // Postal Code input элемент дээр click хийх
  await page.locator('[data-test="continue"]').click(); // Continue button дээр дарж дараагийн алхам руу шилжих
  await page.locator('[data-test="cancel"]').click(); // Cancel button дээр дарж өмнөх алхам руу буцах
  await page.locator('[data-test="continue-shopping"]').click(); // Continue Shopping button дээр дарж бүтээгдэхүүний хуудас руу буцах
  await page.getByRole('button', { name: 'Open Menu' }).click(); // 'Open Menu' button дээр дарж хажуугийн цэсийг нээх
  await page.locator('[data-test="logout-sidebar-link"]').click(); // Logout link дээр дарж системээс гарах
});
