Вариант сборки

Необходимо Java + Docker
1. Открыть проект в любой удобной для вас IDE (например IntelliJ IDEA)
2. Запустить проект

Пожалуйста! Убедитесь, что у вас не заняты порты 8080, 8000 и 5437
Если они заняты, то необходимо поменять порт базы данных в compose.yml и application.yml

Если нет IDE
1. С помощью команды docker compose up поднять БД
2. С помощью команды mvn package собрать jar backend
3. С помощью команды java -jar /target/[weldbootmvn-0.0.1-SNAPSHOT.jar](target%2Fweldbootmvn-0.0.1-SNAPSHOT.jar) поднять приложение
