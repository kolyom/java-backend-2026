# Spring: исключения и @ControllerAdvice (день 7)

Ты прав: **проверка `if (wallet == null)` в controller — это уже логика**, которую лучше убрать из HTTP-слоя.

Цель: controller только вызывает service и возвращает результат.  
«Не найден» обрабатывается через **исключение** + глобальный обработчик.

---

## 1. Как должен выглядеть «тонкий» controller

Идеал на сегодня:

```java
@GetMapping("/{id}")
public WalletResponse getById(@PathVariable Long id) {
    return walletService.findById(id);
}
```

- нет `if`
- нет `ResponseEntity.notFound()`
- нет знания «что такое 404»

Controller знает только: «дай кошелёк по id».  
Если кошелька нет — **service сам решит**, как об этом сообщить (бросит исключение).

---

## 2. Цепочка вызовов

```text
GET /api/wallets/999
        ↓
WalletController.getById(999)
        ↓
WalletService.findById(999)
        ↓
в Map нет ключа 999
        ↓
throw new WalletNotFoundException(999)
        ↓
Spring не находит обработчик в controller
        ↓
ищет @ControllerAdvice с @ExceptionHandler
        ↓
метод пишет HTTP 404 (+ можно JSON с текстом ошибки)
```

Если кошелёк **есть** — исключение не бросается, controller спокойно возвращает `WalletResponse` → Spring сам отдаст **200** + JSON.

---

## 3. Своё исключение — зачем

Не бросай голый `RuntimeException("not found")` — непонятно, *какую* ошибку ловить.

Сделай класс в пакете, например `ru.learning.wallet.exception`:

```text
WalletNotFoundException extends RuntimeException
```

Зачем `RuntimeException` (unchecked):
- не надо писать `throws` на каждом методе
- в Spring так делают часто для бизнес-ошибок

Что положить внутрь:
- сообщение `"Wallet not found: id=" + id`
- опционально поле `Long id` + getter — чтобы в advice можно было отдать id клиенту

Конструктор принимает `id` или сообщение — на твой вкус.

---

## 4. Что делает Service

Псевдологика `findById`:

1. `WalletResponse wallet = memory.get(id);`
2. Если `wallet == null` → `throw new WalletNotFoundException(id);`
3. Иначе → `return wallet;`

Вся «бизнес-проверка есть/нет» живёт **здесь**, не в controller.

`create` не меняется.

---

## 5. @ControllerAdvice — глобальный ловчик

Обычный класс (часто пакет `exception` или `advice`):

```text
@RestControllerAdvice   // или @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {
    ...
}
```

### Разница коротко

| Аннотация | Смысл |
|-----------|--------|
| `@ControllerAdvice` | перехват для всех controller'ов |
| `@RestControllerAdvice` | то же + ответы сразу как тело HTTP (удобно для REST API) |

Для REST бери **`@RestControllerAdvice`**.

Spring при старте регистрирует этот класс как bean и при исключении из любого controller ищет подходящий `@ExceptionHandler`.

---

## 6. @ExceptionHandler — какой метод на какое исключение

Внутри advice:

```text
@ExceptionHandler(WalletNotFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)   // ← это и есть 404
public ... handleWalletNotFound(WalletNotFoundException ex) {
    // вернуть тело ошибки (строку, Map, свой ErrorResponse DTO)
}
```

Что важно:

1. **`@ExceptionHandler(WalletNotFoundException.class)`**  
   — этот метод сработает **только** на это исключение (и наследников, если не перехватят раньше).

2. **`@ResponseStatus(HttpStatus.NOT_FOUND)`**  
   — HTTP-код ответа = **404**.  
   Без этой аннотации (и без `ResponseEntity`) по умолчанию может уйти 500.

3. **Возвращаемое значение** = тело ответа:
   - `String` → текст
   - `Map<String, Object>` → простой JSON
   - свой `ErrorResponse` DTO → аккуратный JSON `{ "message": "...", "id": 999 }`

Альтернатива без `@ResponseStatus`:

```text
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(...);
```

Оба способа ок. На старте проще `@ResponseStatus` + DTO/Map.

---

## 7. Зачем это лучше, чем if в controller

| if в controller | Exception + Advice |
|-----------------|---------------------|
| каждый GET/PUT/DELETE копирует null-check | проверка в одном месте (service) |
| controller знает про HTTP-коды | controller тонкий |
| сложно единообразно оформить ошибки | один формат JSON ошибок на всё API |
| забыли проверить → NPE → 500 | явный контракт: нет сущности → исключение |

Когда появятся `deposit`, `withdraw`, `delete` — везде один и тот же `WalletNotFoundException`, один обработчик.

---

## 8. Пакеты (куда класть)

```text
ru.learning.wallet/
  controller/WalletController.java
  service/WalletService.java
  dto/...
  exception/
    WalletNotFoundException.java
    GlobalExceptionHandler.java      // @RestControllerAdvice
    ErrorResponse.java               // опционально, DTO ошибки
```

Имена могут быть своими (`ApiExceptionHandler` и т.д.) — смысл тот же.

---

## 9. Что увидит клиент

**Успех:** `GET /api/wallets/1`  
→ 200  
```json
{ "id": 1, "owner": "Nikolay", "balance": 1000 }
```

**Нет кошелька:** `GET /api/wallets/999`  
→ 404  
```json
{ "message": "Wallet not found: id=999" }
```
(точный JSON — как сделаешь в handler)

---

## 10. Частые ошибки

| Симптом | Причина |
|---------|---------|
| Всё равно 500 | нет `@RestControllerAdvice` / класс не в сканируемом пакете |
| 500 + Whitelabel | есть exception, но нет `@ExceptionHandler` на этот тип |
| 200 и пусто | вернул null из service вместо throw |
| Advice «не видит» | пакет `exception` вне `ru.learning.wallet` (тогда `@SpringBootApplication` его не сканирует) |

`@SpringBootApplication` сканирует пакет приложения и **подпакеты**.  
Класс advice должен быть под `ru.learning.wallet...`.

---

## 11. Минимальный чеклист задания (обновлённый)

1. Класс `WalletNotFoundException`
2. В `findById`: нет в Map → `throw ...`
3. Класс с `@RestControllerAdvice` + `@ExceptionHandler` + 404
4. Controller GET: **только** `return walletService.findById(id);`
5. Проверка Postman: create → GET id → GET 999 (404)

---

## 12. Связь с тем, что уже знаешь

Это тот же смысл, что `try/catch`, только:

- `throw` делает **service**
- `catch` делает **Spring** через `@ExceptionHandler`
- тебе не нужно писать try/catch в каждом controller

---

Прочитал → пиши код.  
Застрял на exception/advice → **`pick spring`**.  
Готово → **`done spring`**.
