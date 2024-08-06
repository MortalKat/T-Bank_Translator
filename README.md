Инструкция к приложению T-Bank Translator

Для запуска приложения требуется:
  - Java SDK 21+;
  - MySql 8;

Перед началом работы вам необходимо создать базу данных MySQL. Это можно сделать через консоль или 
посредством MySQL Workbench. Введите в консоль следующее сообщение: 

CREATE DATABASE translation_db;

Создайте пользователя для базы данных и дайте ему привелегии использовать новую БД: 

CREATE USER 'translator'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON translation_db.* TO 'translator'@'localhost';
FLUSH PRIVILEGES;

Теперь вы можете скачать код или копировать его посредством команды git clone

Запустите код в IDE, подождите пока загрузятся maven-зависимости и запускайте класс TranslationApp (Shift + F10)

Далее введите данные в следующем виде:

en → ru

Hello world, this is my first program

где en - язык исходного сообщения, ru - язык перевода. Через несколько секунд приложение сохранит результат в БД, а также исходное сообщение, IP 
и время обращения.

Посмотреть сообщщения вы можете через консоль MySQL 

USE translation_db;
SHOW TABLES;
DESCRIBE user_info;

Приятного пользования!
