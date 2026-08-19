# Spring Boot — вводная (день 5)

Зачем это тебе на стажировке: почти весь Java backend в РФ — Spring.

---

## 1. Что ты уже делал vs что делает Spring

**Твой BankAccount в `main`:**
- ты сам создаёшь объекты
- сам вызываешь методы
- программа заканчивается

**Spring Boot приложение:**
- поднимает **веб-сервер** (Tomcat внутри)
- слушает HTTP (например порт 8080)
- когда приходит запрос `GET /api/health` — вызывает **твой** метод и отдаёт ответ
- живёт, пока не остановишь

Ты писал бэкенд «внутри main». Spring — бэкенд как **сервис**, к которому ходят по сети.

---

## 2. Главные слова

| Слово | Просто |
|--------|--------|
| **Controller** | класс с методами под URL (REST API) |
| **Service** | бизнес-логика (перевод, проверки) |
| **Repository** | работа с БД |
| **Bean** | объект, которым управляет Spring (создал, внедрил) |
| **DI** | Spring сам передаёт зависимости в конструктор/поля |

Сегодня только **Controller + запуск**. Service/Repository/БД — следующие дни.

---

## 3. Как выглядит минимальный REST

```java
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
```

- `@RestController` — «этот класс отвечает на HTTP»
- `@GetMapping("/health")` — на GET `/api/health` (с учётом `@RequestMapping`)
- метод возвращает тело ответа

После запуска в браузере или Postman: `http://localhost:8080/api/health`

---

## 4. Структура проекта wallet-service

```text
wallet-service/
  pom.xml                          ← Maven, зависимости
  src/main/java/.../WalletServiceApplication.java  ← точка входа
  src/main/resources/application.properties        ← порт, настройки БД
  src/main/java/.../controller/                  ← сюда твой HealthController
```

`WalletServiceApplication` с `@SpringBootApplication` + `main` с `SpringApplication.run(...)` — Spring сам поднимает всё.

---

## 5. application.properties сегодня

Минимум:

```properties
server.port=8080
spring.application.name=wallet-service
```

БД пока **не подключаем** — сначала убедись, что приложение стартует без ошибок.  
(Если Spring ругнётся на datasource — временно отключим JPA в следующем шаге; см. README-DAY05.)

---

## 6. Как проверить, что всё ок

В консоли при запуске ищешь строку вроде:

```text
Started WalletServiceApplication in X seconds
```

Потом открываешь URL health/ping — видишь свой ответ.

---

## 7. Задание дня 5 (Spring-блок)

1. Открой папку `wallet-service` в VS Code / IDEA как Maven-проект.  
2. Запусти `WalletServiceApplication` (Run Java / зелёная стрелка).  
3. Создай свой класс `HealthController` (или `PingController`) с GET `/api/health` или `/api/ping`.  
4. Ответ — строка `"OK"` или JSON `{"status":"up"}` (как получится).  
5. В чат: `done spring` когда работает.

Готовый бизнес-код переводов сегодня **не** пишем — только старт и один endpoint.

---

`pick spring` — подсказка словами, если не стартует или не понятен Controller.
