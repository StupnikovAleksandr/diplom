# Дипломный проект по профессии «Инженер по тестированию»

Автоматизированное тестирование мобильного приложения **"Мобильный хоспис"** с помощью **Espresso** и отчётов **Allure**.

---

##  Документация

| Файл | Описание |
|------|---------|
| [Documentation/Plan.md](https://github.com/StupnikovAleksandr/diplom/blob/main/Documentation/Plan.md) | План тестирования |
| [Documentation/Check.xlsx](https://github.com/StupnikovAleksandr/diplom/blob/main/Documentation/Сhecklist.xlsx) | Чек-лист с результатами ручных тестов |
| [Documentation/Cases.xlsx](https://github.com/StupnikovAleksandr/diplom/blob/main/Documentation/Case%20.xlsx) | Тест-кейсы |
| [Documentation/allure-results.zip](https://github.com/StupnikovAleksandr/diplom/blob/main/Documentation/allure-results.zip) | Готовый Allure-отчёт |
 
---

##  Как запустить автотесты

### 1. Подготовка системы

- Установите **JDK 18** и настройте переменную окружения:  
  `JAVA_HOME = путь_к_jdk_18`
- Установите **Android Studio** с SDK
- Настройте переменную:  
  `ANDROID_HOME = путь_к_android_sdk`
- Создайте эмулятор с **Android API 29**

---

### 2. Загрузка проекта

Откройте терминал и выполните: git clone https://github.com/StupnikovAleksandr/diplom.git

### 3. Запуск тестов

1. Откройте проект в **Android Studio**
2. Дождитесь загрузки Gradle
3. Запустите эмулятор
4. Перейдите в папку тестов:  
5. ВАЖНО: перед запуском тестов, проверьте созданные новости другими студентами.(необходимо удалить новости,которые будут в будующем)

   `app/src/androidTest/java/ru/iteco/fmhandroid/ui`
5. Кликните правой кнопкой на нужном тесте → **Run 'НазваниеТеста'**

---

### 4. Просмотр отчёта Allure

После запуска тестов:

1. Выполните в терминале команду: `allure serve `
