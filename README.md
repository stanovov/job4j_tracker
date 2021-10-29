[![Build Status](https://travis-ci.com/stanovov/job4j_tracker.svg?branch=master)](https://travis-ci.com/stanovov/job4j_tracker)
[![codecov](https://codecov.io/gh/stanovov/job4j_tracker/branch/master/graph/badge.svg)](https://codecov.io/gh/stanovov/job4j_tracker)

![](https://img.shields.io/badge/Maven-=_3-red)
![](https://img.shields.io/badge/Java-=_14-orange)
![](https://img.shields.io/badge/Liquibase-=_3-f02a18)
![](https://img.shields.io/badge/JDBC-738bff)
![](https://img.shields.io/badge/Hibernate-=_5-green)
![](https://img.shields.io/badge/PostgerSQL-=_9-blue)
![](https://img.shields.io/badge/JUnit-=_4-yellowgreen)
![](https://img.shields.io/badge/H2-0007c7)
![](https://img.shields.io/badge/JaCoCo-c75a28)
![](https://img.shields.io/badge/Checkstyle-lightgrey)

# job4j_tracker

+ [О проекте](#0-проекте)
+ [Технологии](#Технологии)
+ [Сборка и запуск](#Сборка-и-запуск)
+ [Использование](#Использование)
+ [Контакты](#Контакты)

## О проекте

Этот проект представляет собой базовое консольное приложение CRUD и демонстрирует принципы ООП. Для хранения данных 
используется Hibernate.

## Технологии

+ Сборщик проектов **Maven**;
+ Backend - **Java 14**, **JDBC**;
+ Для хранения данных используется фреймворк - **Hibernate**;
+ СУБД - **PostgreSQL**;
+ Непрерывная интеграция - **Travis CI**;
+ Тесты - **JUnit 4**;
+ Покрытие кода тестами - **Codecov**;
+ Инструмент для анализа стиля кода - **Checkstyle**;

## Сборка и запуск

### Запуск через терминал

1.Собрать jar через Maven

`mvn install -Dmaven.test-skip=true`

2.Запустить jar файл

`java -jar target/tracker.jar`

### Запуск через IDE

Перейти к папке `src/main/java` и файлу `ru.job4j.tracker.StartUI`

## Использование

![ ](images/app.png)

## Контакты

Становов Семён Сергеевич

Email: sestanovov@gmail.com

Telegram: [@stanovovss](https://t.me/stanovovss)