# Spring REST + DTO (день 5, часть 2)

Ты уже поднял приложение и сделал `GET /api/health`.  
Сейчас следующий шаг: **принять данные от клиента** и вернуть ответ.

Это база любого backend: создать кошелёк, зарегистрировать пользователя, оформить заказ.

---

## 1. GET vs POST — в чём разница

| | GET | POST |
|---|-----|------|
| Зачем | **Получить** данные | **Создать** / отправить данные |
| Тело запроса | обычно пустое | есть JSON в body |
| Пример | список кошельков, health | создать кошелёк |
| Идемпотентность | да (повторный запрос = тот же эффект) | нет (каждый раз может создать новое) |

Твой `health` — это GET: клиент ничего не отправляет, только читает ответ.

Создание кошелька — POST: клиент **шлёт** JSON с `owner` и `balance`.

---

## 2. Как выглядит HTTP-запрос (POST)

Клиент (Postman, фронт, curl) отправляет:

```http
POST /api/wallets/create HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "owner": "Nikolay",
  "balance": 1000
}
```

Сервер отвечает:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "owner": "Nikolay",
  "balance": 1000
}
```

Сегодня тебе нужно научить Spring **принять** первый JSON и **вернуть** ответ.

---

## 3. Зачем DTO, а не Entity сразу

**DTO** (Data Transfer Object) — класс только для обмена данными по сети.

```text
Клиент  →  JSON  →  DTO  →  (позже Service)  →  Entity  →  БД
Клиент  ←  JSON  ←  DTO  ←  (позже Service)  ←  Entity  ←  БД
```

### Почему не тащить Entity напрямую в API?

1. **Безопасность** — в Entity могут быть поля, которые клиенту видеть нельзя (`passwordHash`, внутренние id).
2. **Разные формы** — запрос и ответ часто разные (создание: 2 поля, ответ: id + createdAt + balance).
3. **Независимость от БД** — сегодня без PostgreSQL, завтра подключим; API не меняется.
4. **Валидация** — на DTO удобно вешать `@NotBlank`, `@Positive` и т.д.

Сегодня: **только DTO, без Entity, без БД**. Просто «принял → вернул».

---

## 4. Структура пакетов (куда класть файлы)

```text
ru.learning.wallet/
  WalletServiceApplication.java    ← точка входа
  controller/
    HealthController.java
    WalletController.java          ← новый
  dto/
    CreateWalletRequest.java       ← что приходит от клиента
    WalletResponse.java            ← что отдаём (можно один класс на старте)
```

Правило: **controller** — только HTTP. Никакой бизнес-логики «если баланс < 0» в controller надолго не живёт — потом уйдёт в service.

---

## 5. Lombok — что это и зачем

**Lombok** — библиотека, которая **генерирует boilerplate-код на этапе компиляции**: конструкторы, геттеры, сеттеры, `equals`, `hashCode`, `toString`.

Вместо 40 строк руками:

```java
public class CreateWalletRequest {
    private String owner;
    private Long balance;
    // + конструкторы + 4 метода + toString...
}
```

Пишешь поля + аннотации — компилятор/Lombok делает остальное.

### Подключение (у тебя уже в pom.xml)

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

После изменения `pom.xml`:
1. Reload Maven project (правый клик на `pom.xml` → Update Project)
2. Установи расширение VS Code: **Lombok Annotations Support** (`vscjava.vscode-lombok`)
3. Перезагрузи окно, если IDE не видит аннотации

---

## 6. Аннотации Lombok — шпаргалка

| Аннотация | Что генерирует | Когда использовать |
|-----------|----------------|-------------------|
| `@Getter` | getXxx() для всех полей | нужны только геттеры |
| `@Setter` | setXxx() для всех полей | нужны только сеттеры |
| `@NoArgsConstructor` | конструктор без аргументов `()` | **обязательно для DTO** — Jackson/Spring создаёт объект так |
| `@AllArgsConstructor` | конструктор со всеми полями | удобно для тестов, `@Builder` |
| `@RequiredArgsConstructor` | конструктор только для `final` полей | **Service, Repository** — DI через конструктор |
| `@Data` | `@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructor` | **DTO** — самый частый выбор |
| `@Builder` | builder-паттерн `.builder().owner("x").build()` | когда много полей, удобная сборка |
| `@ToString` | метод toString() | отладка в логах |
| `@EqualsAndHashCode` | equals() и hashCode() | сравнение объектов, HashMap |

### Важно про `@Data`

`@Data` **не** добавляет `@NoArgsConstructor` по умолчанию (если нет `final` полей — добавит `@RequiredArgsConstructor`, но не no-args).

Для DTO с Jackson часто пишут **так**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWalletRequest {
    private String owner;
    private Long balance;
}
```

- `@NoArgsConstructor` — Spring/Jackson создаёт пустой объект и ставит поля через сеттеры
- `@AllArgsConstructor` — удобно в коде: `new CreateWalletRequest("Nikolay", 1000)`

### `@RequiredArgsConstructor` — для Service (позже)

Когда появится Service с зависимостями:

```java
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository repository;  // final → попадёт в конструктор
}
```

Spring сам вызовет `new WalletService(repository)`. Это **DI через конструктор** — стандарт в Spring.

---

## 7. DTO с Lombok (твой вариант на сегодня)

**Request** — что приходит от клиента:

```java
package ru.learning.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWalletRequest {
    private String owner;
    private Long balance;
}
```

**Response** — что отдаём:

```java
package ru.learning.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {
    private String owner;
    private Long balance;
}
```

Импорты всегда `import lombok.XXX;` — не путай с Spring.

**Имена полей** = ключи в JSON:
- `"owner"` → `owner`
- `"balance"` → `balance`

---

## 8. DTO без Lombok (если хочешь понять, что под капотом)

```java
public class CreateWalletRequest {
    private String owner;
    private Long balance;

    public CreateWalletRequest() {}

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public Long getBalance() { return balance; }
    public void setBalance(Long balance) { this.balance = balance; }
}
```

Lombok генерирует то же самое. В реальных проектах на DTO почти всегда Lombok.

---

## 9. Controller с POST

```java
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @PostMapping("/create")
    public WalletResponse create(@RequestBody CreateWalletRequest request) {
        // сегодня: собрать ответ из request и вернуть
    }
}
```

### Аннотации

| Аннотация | Что делает |
|-----------|------------|
| `@RestController` | класс = REST API, возврат из метода → тело ответа |
| `@RequestMapping("/api/wallets")` | базовый путь для всех методов класса |
| `@PostMapping("/create")` | этот метод на POST `/api/wallets/create` |
| `@RequestBody` | Spring читает JSON из body и кладёт в объект `request` |

Полный URL: `POST http://localhost:8080/api/wallets/create`

---

## 10. Кто превращает JSON в Java? (Jackson)

В `spring-boot-starter-web` уже есть **Jackson**.

```text
JSON строка  --Jackson-->  CreateWalletRequest
WalletResponse  --Jackson-->  JSON в ответе
```

Тебе не нужно вручную парсить `{"owner":"..."}`.  
Достаточно DTO + `@RequestBody` + return DTO/объекта.

Если JSON кривой (нет поля, неверный тип) — Spring вернёт **400 Bad Request** сам.

---

## 11. Что делать в методе сегодня (логика дня 5)

Задание **без БД и без Service**:

1. Принять `CreateWalletRequest`
2. Создать `WalletResponse` (или вернуть тот же тип — но лучше отдельный response)
3. Скопировать `owner` и `balance` из request в response
4. Вернуть response

Псевдологика словами:
- взял owner из request → положил в response
- взял balance из request → положил в response
- return response

Позже сюда добавится: `walletService.create(request)` → сохранение в БД → return с `id`.

---

## 12. Как проверить (Postman или curl)

### Postman
1. Method: **POST**
2. URL: `http://localhost:8080/api/wallets/create`
3. Body → **raw** → **JSON**:
```json
{
  "owner": "Nikolay",
  "balance": 1000
}
```
4. Send → в ответе тот же JSON (или твой response DTO)

### curl (PowerShell)
```powershell
curl.exe -X POST http://localhost:8080/api/wallets/create `
  -H "Content-Type: application/json" `
  -d "{\"owner\":\"Nikolay\",\"balance\":1000}"
```

Успех = **200** и JSON с `owner` и `balance`.

---

## 13. Частые ошибки

| Ошибка | Причина |
|--------|---------|
| 404 Not Found | неверный URL или нет `@PostMapping` |
| 415 Unsupported Media Type | забыл `Content-Type: application/json` |
| 400 Bad Request | JSON не совпадает с полями DTO (опечатка в ключе) |
| `null` в полях | нет `@NoArgsConstructor` / геттеров / сеттеров |
| Пустой ответ `{}` | поля private без геттеров на response |

| IDE не видит Lombok | нет расширения `vscode-lombok` или не Reload Maven |

---

## 14. Слои — картина наперёд (не делай сегодня)

```text
Controller  →  принимает HTTP, вызывает Service, отдаёт DTO
Service     →  бизнес-правила (нельзя withdraw больше balance)
Repository  →  save/find в PostgreSQL
Entity      →  таблица в БД (@Entity)
```

Сегодня ты на ступени **Controller + DTO**.  
Следующий шаг после `done spring` — **Service** или сразу **Entity + PostgreSQL**.

---

## 15. Твоё задание после прочтения

1. Reload Maven (Lombok подтянулся из pom)
2. Пакет `dto`: `CreateWalletRequest`, `WalletResponse` с `@Data` + `@NoArgsConstructor` + `@AllArgsConstructor`
3. Пакет `controller`: `WalletController`
4. `POST /api/wallets/create` — принять JSON, вернуть те же данные
5. Проверить в Postman
6. В чат: **`done spring`**

Не подключай БД, не пиши Service, не усложняй.

---

## 16. Вопросы себе перед кодом

- Какой HTTP-метод для создания? → POST
- Где лежит класс с полями `owner` и `balance` для входа? → dto
- Какая аннотация читает JSON из body? → `@RequestBody`
- Зачем `@NoArgsConstructor` на DTO? → Jackson создаёт объект через пустой конструктор
- Чем `@Data` отличается от `@Getter` + `@Setter`? → Data ещё toString, equals, hashCode
- Какой полный URL? → `POST /api/wallets/create`

Если на все ответил — можно писать.

`pick spring` — подсказка словами, без готового кода.
