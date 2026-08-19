# Deposit / Withdraw + Validation (день 9)

У тебя уже есть: создать кошелёк и прочитать по id.  
Сегодня — **пополнить** и **снять**, плюс базовая **валидация** суммы.

---

## 1. Какие endpoint'ы

| Метод | URL | Смысл |
|-------|-----|--------|
| POST | `/api/wallets/{id}/deposit` | положить деньги |
| POST | `/api/wallets/{id}/withdraw` | снять деньги |

Тело запроса (одинаковое по смыслу):

```json
{ "amount": 100 }
```

Ответ — обычный `WalletResponse` с обновлённым `balance`.

---

## 2. DTO для суммы

Заведи отдельный класс, например `AmountRequest`:

- поле `amount` типа `Long` / `Long`
- Lombok как у других DTO

Почему не `WalletRequest`: там `owner` + `balance` при создании.  
Для операций нужна только сумма.

---

## 3. Validation (Jakarta Validation)

Зависимость: `spring-boot-starter-validation` (добавим в pom).

На поле в DTO:

| Аннотация | Смысл |
|-----------|--------|
| `@NotNull` | amount обязателен |
| `@Positive` | строго > 0 (ноль и минус нельзя) |

На параметре controller:

```text
@Valid @RequestBody AmountRequest request
```

Без `@Valid` аннотации на DTO **не сработают**.

### Что увидит клиент при плохом amount

Spring по умолчанию → **400 Bad Request**.  
Позже можно красиво оформить через `@ExceptionHandler(MethodArgumentNotValidException.class)` — сегодня достаточно стандартного 400.

Опционально (бонус): свой handler на validation, как для 404.

---

## 4. Логика Service — deposit

Словами:

1. Найти кошелёк по id (как в `findById` — нет → `WalletNotFoundException`)
2. `новый баланс = старый + amount`
3. `setBalance` + `save`
4. Вернуть `WalletResponse`

---

## 5. Логика Service — withdraw

1. Найти кошелёк
2. Если `amount > balance` → нельзя снимать  
   → своё исключение, например `InsufficientFundsException`  
   → в `@RestControllerAdvice` отдать **400** (или 409 Conflict — на вкус, сегодня возьми **400**)
3. Иначе `balance - amount` → save → response

Не уходи в минус.

---

## 6. Controller — тонкий

```text
@PostMapping("/{id}/deposit")
return walletService.deposit(id, request);

@PostMapping("/{id}/withdraw")
return walletService.withdraw(id, request);
```

`@PathVariable Long id` + `@Valid @RequestBody AmountRequest request`.

Никакой арифметики в controller.

---

## 7. Новое исключение

По аналогии с `WalletNotFoundException`:

- класс `InsufficientFundsException` (RuntimeException)
- сообщение с id / amount / balance — по желанию
- в `GlobalExceptionHandler` — `@ExceptionHandler` + `@ResponseStatus(HttpStatus.BAD_REQUEST)`

404 для «нет кошелька», 400 для «не хватает денег» / невалидный body.

---

## 8. Зачем @Transactional (кратко, можно сегодня)

Метод Service с несколькими шагами read/update иногда помечают `@Transactional`,  
чтобы работа с БД была в одной транзакции.

Для одной `find` + `save` Spring Data часто и так ок.  
Можешь добавить `@Transactional` на `deposit`/`withdraw` — будет плюсом.  
Не обязательно для засчитывания дня.

---

## 9. Порядок проверки

1. Create кошелёк с balance 1000, запомни id
2. POST `.../deposit` `{ "amount": 200 }` → balance 1200
3. POST `.../withdraw` `{ "amount": 300 }` → balance 900
4. POST withdraw `{ "amount": 99999 }` → **400**
5. POST deposit `{ "amount": -5 }` или `0` → **400** (validation)
6. POST deposit на id 999 → **404**

---

## 10. Задание дня 9 (Spring)

1. Прочитать теорию
2. Добавить validation (pom уже обновим)
3. `AmountRequest` + `@Positive` / `@NotNull`
4. `deposit` + `withdraw` в Service и Controller
5. `InsufficientFundsException` + handler 400
6. Проверить сценарии выше
7. **`done spring`**

`pick spring` — подсказка словами.
