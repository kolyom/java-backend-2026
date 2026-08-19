# Spring: GET по id + PathVariable (день 7)

Вчера: создали кошелёк (`POST`), положили в `Map`, вернули с `id`.  
Сегодня: **прочитать** кошелёк по id — `GET /api/wallets/{id}`.

БД пока **не** подключаем. Работаем с тем же `Map` в Service.

**Важно:** null-check и 404 **не** держи в controller.  
Читай также: **`docs/theory/18-spring-exception-handler.md`**  
(исключение + `@RestControllerAdvice`).

---

## 1. Зачем GET по id

Типичный REST:

| Метод | URL | Смысл |
|-------|-----|--------|
| POST | `/api/wallets` или `/create` | создать |
| GET | `/api/wallets/5` | получить кошелёк с id=5 |
| GET | `/api/wallets` | список (позже) |

Клиент знает id (из ответа create) и спрашивает: «дай мне этот кошелёк».

---

## 2. Path variable — кусок URL

В URL `http://localhost:8080/api/wallets/5`  
число **5** — это не query (`?id=5`), а **часть пути**.

В Spring:

```java
@GetMapping("/{id}")
public WalletResponse getById(@PathVariable Long id) {
    return walletService.findById(id);
}
```

- `@GetMapping("/{id}")` + класс `@RequestMapping("/api/wallets")`  
  → полный путь `GET /api/wallets/5`
- `@PathVariable Long id` — Spring достаёт `5` из URL и кладёт в переменную `id`

Имя в `{}` и имя параметра должны совпадать (`id` ↔ `id`),  
либо явно: `@PathVariable("id") Long walletId`.

---

## 3. Query vs Path — не путай

| | Path | Query |
|---|------|-------|
| Пример | `/api/wallets/5` | `/api/wallets?id=5` |
| Аннотация | `@PathVariable` | `@RequestParam` |
| Когда | «конкретный ресурс» | фильтры, опции |

Для «получить сущность по id» почти всегда **Path**.

---

## 4. Service: найти в Map

У тебя уже есть:

```text
Map<Long, WalletResponse> memory
```

Нужен метод вроде `findById(Long id)`:

1. Взять из map по ключу `id`
2. Если **есть** — вернуть объект
3. Если **нет** — сообщить контроллеру, что не найдено (404)

`map.get(id)` вернёт `null`, если ключа нет.  
`map.containsKey(id)` — проверить наличие.

---

## 5. Что делать, если не нашли (404)

Клиент запросил `/api/wallets/999`, а такого нет → **404 Not Found**.

**Не делай так в controller** (логика не там):

```java
if (wallet == null) {
    return ResponseEntity.notFound().build();
}
```

Правильный путь на сегодня — **исключение из Service** + глобальный обработчик:

→ подробно в **`docs/theory/18-spring-exception-handler.md`**

Кратко:
1. Service: нет в Map → `throw new WalletNotFoundException(id)`
2. `@RestControllerAdvice` + `@ExceptionHandler` → HTTP 404
3. Controller: только `return walletService.findById(id);`

---

## 6. Optional — коротко (полезно)

```java
Optional<WalletResponse> findById(Long id) {
    return Optional.ofNullable(memory.get(id));
}
```

В controller:

```text
если optional пустой → 404
иначе → 200 + тело
```

Методы: `isPresent()`, `isEmpty()`, `get()`, `orElse(...)`, `orElseThrow(...)`.

---

## 7. Порядок проверки руками

1. Запусти приложение (`.\run.ps1` в `wallet-service`)
2. `POST /api/wallets/create` с JSON → запомни `id` из ответа (например `1`)
3. `GET http://localhost:8080/api/wallets/1` → тот же owner/balance
4. `GET http://localhost:8080/api/wallets/999` → **404**

Postman: Method GET, URL с id, Body не нужен.

---

## 8. Структура (что менять)

```text
controller/WalletController.java  ← добавить GET
service/WalletService.java        ← добавить findById
dto/ — без изменений
```

Controller по-прежнему тонкий: достал id → вызвал service → вернул ответ/404.

---

## 9. Частые ошибки

| Ошибка | Почему |
|--------|--------|
| 404 на существующий id | забыли `put` при create / другой id |
| 500 | `NullPointerException` — вернули null без проверки |
| GET не находится | `@GetMapping` vs опечатка в пути |
| id всегда null | забыли `@PathVariable` |

---

## 10. Задание дня 7 (Spring)

1. Прочитай эту теорию
2. В `WalletService` — метод поиска по id (из Map)
3. В `WalletController` — `GET /api/wallets/{id}`
4. Нет в Map → **404**
5. Есть → **200** + `WalletResponse`
6. Проверь: create → get по id → get несуществующий
7. В чат: **`done spring`**

PostgreSQL / JPA — **не сегодня**. Сначала GET + 404.

`pick spring` — подсказка словами.
