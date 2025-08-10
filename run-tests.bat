@echo off
echo Запуск тестов Stellar Burgers...
echo.

REM Запуск всех тестов в Chrome
echo Запуск всех тестов в Chrome:
mvn clean test

echo.
echo Для запуска в Яндекс браузере используйте:
echo mvn clean test -Dbrowser=yandex
echo.
echo Для генерации Allure отчета:
echo mvn allure:report
echo mvn allure:serve

pause
