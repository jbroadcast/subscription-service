Сборка докер образа производится с помощью spring-boot-maven-plugin.

Для сборки образа необходимо через консоль в корне проекта выполнить команду:
```bash
mvn clean install -DskipTests
```

Запуск проекта в докере производится через команду docker-compose up из папки subscription-compose.
```bash
docker-compose up
```