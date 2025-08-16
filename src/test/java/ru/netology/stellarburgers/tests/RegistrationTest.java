package ru.netology.stellarburgers.tests;

import io.qameta.allure.*;
import org.junit.After;
import org.junit.Test;
import ru.netology.stellarburgers.api.UserApiClient;
import ru.netology.stellarburgers.base.BaseTest;
import ru.netology.stellarburgers.pages.MainPage;
import ru.netology.stellarburgers.pages.RegisterPage;
import ru.netology.stellarburgers.utils.TestDataGenerator;

import static org.junit.Assert.*;

/**
 * Тесты функциональности регистрации
 */
@Epic("Stellar Burgers")
@Feature("Регистрация пользователей")
public class RegistrationTest extends BaseTest {
    
    private String createdUserToken;
    
    @Override
    @After
    public void tearDown() {
        // Удаляем созданного пользователя через API
        if (createdUserToken != null) {
            UserApiClient.deleteUser(createdUserToken);
        }
    }
    
    @Test
    @Description("Успешная регистрация с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulRegistration() {
        // Генерируем тестовые данные с помощью DataFaker
        String name = TestDataGenerator.generateRandomName();
        String email = TestDataGenerator.generateRandomEmail();
        String password = TestDataGenerator.generateValidPassword();
        
        // Переходим на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        
        // Регистрируемся
        registerPage.register(name, email, password);
        
        // Проверяем, что произошел переход на /login
        assertTrue("После успешной регистрации должен быть переход на страницу входа",
                driver.getCurrentUrl().contains("/login"));
        
        // Получаем токен для удаления пользователя
        createdUserToken = UserApiClient.loginUser(email, password);
    }
    
    @Test
    @Description("Регистрация с паролем менее 6 символов показывает ошибку")
    @Severity(SeverityLevel.NORMAL)
    public void testRegistrationWithInvalidPassword() {
        // Генерируем тестовые данные с помощью DataFaker
        String name = TestDataGenerator.generateRandomName();
        String email = TestDataGenerator.generateRandomEmail();
        String invalidPassword = TestDataGenerator.generateInvalidPassword(); // менее 6 символов
        
        // Переходим на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        
        // Пытаемся зарегистрироваться с некорректным паролем
        registerPage.tryRegisterWithError(name, email, invalidPassword);
        
        // Проверяем, что отображается сообщение об ошибке
        assertTrue("Должно отображаться сообщение об ошибке для некорректного пароля", 
            registerPage.isErrorMessageDisplayed());
        
        String errorMessage = registerPage.getErrorMessage();
        assertFalse("Сообщение об ошибке не должно быть пустым", 
            errorMessage.isEmpty());
    }
    
    @Test
    @Description("Навигация с регистрации на вход работает корректно")
    @Severity(SeverityLevel.MINOR)
    public void testNavigationToLoginFromRegistration() {
        // Переходим на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        
        // Нажимаем ссылку "Войти"
        registerPage.clickLoginLink();
        
        // Проверяем, что перешли на страницу входа
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Должен быть переход на страницу входа", 
            currentUrl.contains("login"));
    }
}
