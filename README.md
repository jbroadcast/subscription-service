Сборка докер образа производится с помощью spring-boot-maven-plugin.
Для сборки образа необходимо в консоли в корне проекта выполнить команду mvn clean install -DskipTests.

Запуск проекта в докере производится через команду docker-compose up из папки docker-compose.
