# brokenserver
Simple server for debug

Использовался для отлова отдельного запроса http (перенапривили через nginx) и оставления его без ответа (цель - фронт ждет ответа продолжительное время)
Запуск - ./gradlew build && java -classpath build/classes/java/main ru.axxle.brokenserver.Server `<port>`
