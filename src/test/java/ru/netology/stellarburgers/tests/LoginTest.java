package ru.netology.stellarburgers.tests;

import io.qameta.allure.*;
import org.junit.Before;
import org.junit.Test;
import ru.netology.stellarburgers.base.BaseTest;
import ru.netology.stellarburgers.pages.MainPage;
import ru.netology.stellarburgers.pages.LoginPage;
import ru.netology.stellarburgers.pages.RegisterPage;
import ru.netology.stellarburgers.pages.ForgotPasswordPage;
import ru.netology.stellarburgers.utils.TestDataGenerator;

import static org.junit.Assert.*;

/**
 * Тесты функциональности входа в систему
 */
@Epic("Stellar Burgers")
@Feature("Вход в систему")
public class LoginTest extends BaseTest {
    
    private String testEmail;
    private String testPassword;
    private String testName;
    
    @Before
    public void prepareTestUser() {
        // Создаем тестового пользователя для каждого теста
        testName = TestDataGenerator.generateRandomName();
        testEmail = TestDataGenerator.generateRandomEmail();
        testPassword = TestDataGenerator.generateValidPassword();
        
        // Регистрируем пользователя
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.register(testName, testEmail, testPassword);
        
        // Возвращаемся на главную страницу
        driver.get(BASE_URL);
    }
    
    @Test
    @Description("Проверка входа пользователя через основную кнопку входа")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaMainLoginButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();
        
        // Входим в систему
        loginPage.login(testEmail, testPassword);
        
        // Проверяем: ушли со страницы /login
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }
    
    @Test
    @Description("Проверка входа пользователя через кнопку 'Личный кабинет'")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccountButton();
        
        // Входим в систему
        loginPage.login(testEmail, testPassword);
        
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }
    
    @Test
    @Description("Проверка входа пользователя через ссылку 'Войти' на странице регистрации")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        LoginPage loginPage = registerPage.clickLoginLink();
        
        // Входим в систему
        loginPage.login(testEmail, testPassword);
        
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }
    
    @Test
    @Description("Проверка входа пользователя через ссылку 'Войти' на странице восстановления пароля")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginViaForgotPasswordForm() {
        MainPage mainPage = new MainPage(driver);
        LoginPage initialLoginPage = mainPage.clickLoginButton();
        ForgotPasswordPage forgotPasswordPage = initialLoginPage.clickForgotPasswordLink();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        
        // Входим в систему
        loginPage.login(testEmail, testPassword);
        
        assertFalse("После входа не должны оставаться на /login", driver.getCurrentUrl().contains("/login"));
    }
    
    @Test
    @Description("Проверка отображения ошибки при вводе неверных учетных данных")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginWithInvalidCredentials() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickLoginButton();
        
        // Пытаемся войти с неверными данными
        String invalidEmail = "invalid@example.com";
        String invalidPassword = "wrongpassword";
        
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLoginButtonExpectingStay();

        // Проверяем, что остались на странице входа (не произошел переход)
        assertTrue("При неверных данных должны остаться на странице входа", driver.getCurrentUrl().contains("login"));
    }
}
