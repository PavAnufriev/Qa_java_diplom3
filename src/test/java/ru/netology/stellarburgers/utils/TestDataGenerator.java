package ru.netology.stellarburgers.utils;

import java.util.Random;

/**
 * Утилитный класс для генерации тестовых данных
 */
public class TestDataGenerator {
    
    private static final Random random = new Random();
    
    /**
     * Генерирует случайный email
     */
    public static String generateRandomEmail() {
        return "test" + System.currentTimeMillis() + "@yandex.ru";
    }
    
    /**
     * Генерирует случайное имя
     */
    public static String generateRandomName() {
        String[] names = {"Иван", "Петр", "Сидор", "Анна", "Мария", "Ольга"};
        return names[random.nextInt(names.length)] + random.nextInt(1000);
    }
    
    /**
     * Генерирует случайный пароль заданной длины
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
    
    /**
     * Генерирует валидный пароль (6+ символов)
     */
    public static String generateValidPassword() {
        return generateRandomPassword(8);
    }
    
    /**
     * Генерирует невалидный пароль (менее 6 символов)
     */
    public static String generateInvalidPassword() {
        return generateRandomPassword(5);
    }
}
