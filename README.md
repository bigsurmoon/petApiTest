# Тестовое задание для Soft Engineering
## Задача 3.1 API-автотесты
Написать API - автотесты для сайта https://petstore.swagger.io/ по одному методу из любых блоков (GET,PUT,POST,DELETE).
## Стек технологий:
* Java
* Junit
* Maven
* Rest Assured
* Allure
## Порядок запуска тестов:
* Клонировать проект используя команду "$ git clone https://github.com/bigsurmoon/petApiTest" 
* Открыть проект в IDE (напр. [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/))
* Собрать проект: mvn clean install
* Запуск тестов: mvn test
* Генерация Allure отчета: mvn allure:serve

* ## Структура проекта

```plaintext
project-root/
├── .allure/                          # Каталог конфигурации Allure
├── .idea/                            # Каталог конфигурации IntelliJ IDEA
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── se/
│       │           └── petstore/
│       │               └── api/
│       │                   ├── PetStoreApiTests.java # Класс с тестами API
│       │                   └── PostPet.java          # Класс для создания объектов питомцев
│       └── resources/
│           └── allure.properties     # Файл конфигурации Allure
├── .gitignore                        # Файл конфигурации для исключения файлов и каталогов из системы контроля версий Git
├── pom.xml                           # Файл конфигурации Maven
