# Дипломный проект по курсу "Тестировщик ПО" #

## Инструкция по запуску проекта ##

#### На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker #### 

### ПРОЦЕДУРА ЗАПУСКА АВТОТЕСТОВ ###

##### В проекте 2 БД MySQL и PostgreSQL #####

1. Склонировать на локальный репозиторий Дипломный проект и открыть его в приложении IntelliJ IDEA.
2. Запустить Docker Desktop.
3. Открыть проект в IntelliJ IDEA.
4. Запустить контейнер:
   docker-compose up -d
5. Запустить целевое приложение:

   для mySQL:
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

   для postgresgl:
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
6. Во втором приложении запустить тесты:

   для mySQL:
      ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

   для postgresgl:
      ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
7. Создать отчёт Allure, открыть в браузере:

   ./gradlew allureServe
8. Закрыть отчет:
   Ctrl+C
9. Остановить работу контейнеров через команду:
   docker-compose down