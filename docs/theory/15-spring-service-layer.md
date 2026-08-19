# Spring Service-слой (день 6)

Вчера: Controller принимал JSON и сразу собирал ответ.  
Сегодня: выносим логику в **Service** — как в реальных проектах.

---

## 1. Проблема «толстого» Controller

Сейчас у тебя в `WalletController` примерно так:

```text
принял request → скопировал поля → вернул response
```

Пока это 3 строки — норм. Но дальше появится:
- проверка: баланс не может быть отрицательным
- генерация `id` кошелька
- сохранение в память / БД
- поиск по id
- списание / пополнение

Если всё это в Controller — файл раздувается, тестировать сложно, переиспользовать нельзя.

**Правило:** Controller = только HTTP. Бизнес-логика = Service.

---

## 2. Слои (картина целиком)

```text
HTTP-запрос
    ↓
Controller   — принял JSON, вызвал service, вернул ответ
    ↓
Service      — правила, создание, поиск, проверки
    ↓
Repository   — работа с БД (сегодня ещё нет)
    ↓
PostgreSQL
```

Сегодня делаем **Controller → Service → хранение в памяти** (List или Map).  
БД подключим на следующем шаге.

---

## 3. Что такое Service

**Service** — обычный Java-класс с аннотацией `@Service`.  
Spring создаёт его один раз (singleton) и **внедряет** туда, где нужно.

Пример структуры (схема, не копируй слепо):

```text
WalletController
    вызывает → walletService.create(request)

WalletService
    проверяет данные
    создаёт кошелёк
    кладёт в List/Map
    возвращает WalletResponse
```

Controller **не знает**, как именно создаётся кошелёк — только вызывает метод.

---

## 4. @Service

```java
@Service
public class WalletService {
    // методы create, findById, ...
}
```

- `@Service` = «это бизнес-слой, Spring, зарегистрируй как bean»
- Аналоги: `@Component` (общий), `@Repository` (для БД) — позже

Spring сканирует пакет `ru.learning.wallet` и находит классы с такими аннотациями.

---

## 5. Внедрение зависимостей (DI)

Controller нужен `WalletService`. Spring **сам** передаёт его в конструктор.

### Способ 1: @RequiredArgsConstructor (Lombok) — рекомендую

```java
@RestController
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/create")
    public WalletResponse create(@RequestBody WalletRequest request) {
        return walletService.create(request);
    }
}
```

- `final` поле → Lombok генерирует конструктор с этим аргументом
- Spring при старте вызывает `new WalletController(walletService)`
- **Не пиши** `new WalletService()` вручную в controller

### Способ 2: конструктор руками (без Lombok)

```java
private final WalletService walletService;

public WalletController(WalletService walletService) {
    this.walletService = walletService;
}
```

Смысл тот же.

### Что НЕ делать

```java
private WalletService walletService = new WalletService(); // плохо
```

Так Spring не управляет объектом, тесты и БД потом не подключишь нормально.

---

## 6. Хранение в памяти (до PostgreSQL)

Пока без БД — кошельки храним в **коллекции внутри Service**:

| Вариант | Плюсы |
|---------|--------|
| `List<WalletResponse>` | просто, порядок сохраняется |
| `Map<Long, WalletResponse>` | быстрый поиск по id |

Для `create` удобнее **Map**: ключ = `id`, значение = кошелёк.

```text
Map:
  1 → { id: 1, owner: "Nikolay", balance: 1000 }
  2 → { id: 2, owner: "Anna", balance: 500 }
```

Поле в Service, например:
```java
private final Map<Long, WalletResponse> storage = new HashMap<>();
```

`final` = ссылка на map не меняется, но **внутрь** map можно `put`.

---

## 7. Генерация id

Клиент при создании **не** присылает id — его выдаёт сервер.

Простой вариант на сегодня:

```java
private Long nextId = 1;
// при create: id = nextId++;  потом put в map
```

Или `AtomicLong` — потом, для многопоточности.

В `WalletResponse` добавь поле `id` (Long).

---

## 8. Метод create в Service — логика словами

1. Взять `owner` и `balance` из `request`
2. (Опционально) проверить: `balance >= 0` — если нет, бросить исключение (позже `@Valid`)
3. Сгенерировать `id`
4. Собрать `WalletResponse` с `id`, `owner`, `balance`
5. Положить в `storage` по ключу `id`
6. Вернуть `response`

Controller после рефакторинга:

```text
return walletService.create(request);
```

Одна строка.

---

## 9. Пакеты

```text
ru.learning.wallet/
  controller/
    WalletController.java
  service/
    WalletService.java      ← новый
  dto/
    WalletRequest.java
    WalletResponse.java     ← добавить поле id
```

---

## 10. Проверка

1. Запусти `.\run.ps1` в `wallet-service`
2. POST `http://localhost:8080/api/wallets/create` с телом:
```json
{ "owner": "Nikolay", "balance": 1000 }
```
3. В ответе должен быть **`id`** (например `1`) + owner + balance
4. Второй POST — `id` должен быть `2`

---

## 11. Частые ошибки

| Ошибка | Что не так |
|--------|------------|
| `NullPointerException` в controller | не внедрил Service (нет `@RequiredArgsConstructor` / конструктора) |
| Service не находится | класс не в пакете `ru.learning.wallet` или нет `@Service` |
| id всегда 0 | не проставляешь id в response |
| Два POST — один id | `nextId` не увеличиваешь |

---

## 12. Задание дня 6 (Spring-блок 1)

1. Прочитай эту теорию
2. Добавь в `WalletResponse` поле `id`
3. Создай `WalletService` с `@Service`
4. In-memory `Map<Long, WalletResponse>` + генерация id
5. Метод `create(WalletRequest request)` → `WalletResponse`
6. `WalletController` — только вызов `walletService.create(request)`
7. DI через `@RequiredArgsConstructor` + `private final WalletService walletService`
8. Проверь 2 POST подряд — разные id
9. В чат: **`done spring`**

БД и Repository — следующий шаг, не сегодня.

`pick spring` — подсказка словами.
