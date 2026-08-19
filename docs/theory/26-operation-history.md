# История операций (день 12)

Сейчас кошелёк умеет create / get / deposit / withdraw / transfer, но **нет аудита**: «кто когда что делал».

Сегодня — отдельная таблица **операций** и endpoint для просмотра.

---

## 1. Зачем

| Без истории | С историей |
|-------------|------------|
| видишь только текущий balance | видишь цепочку: +100, −50, transfer… |
| сложнее дебажить | проще объяснить на интервью «как audit log» |

Это типичный следующий шаг pet-проекта перед тестами и security.

---

## 2. Entity `WalletOperation`

Пример полей (имена можешь чуть сменить, смысл сохрани):

| Поле | Смысл |
|------|--------|
| `id` | PK, auto |
| `walletId` | чей это кошелёк |
| `type` | enum: `DEPOSIT`, `WITHDRAW`, `TRANSFER_IN`, `TRANSFER_OUT` |
| `amount` | сумма операции |
| `counterpartyWalletId` | для transfer — второй кошелёк (nullable) |
| `createdAt` | `Instant` или `LocalDateTime` |

Одна строка = **одно событие** с точки зрения конкретного кошелька.  
Transfer → **две** записи (OUT у from, IN у to) в одной транзакции.

---

## 3. Где писать

В **`WalletService`**, после успешного изменения balance:

- `deposit` → одна запись DEPOSIT  
- `withdraw` → WITHDRAW  
- `transfer` → TRANSFER_OUT + TRANSFER_IN (в том же `@Transactional`)

Repository: `WalletOperationRepository extends JpaRepository<...>`.

---

## 4. API

```text
GET /api/wallets/{id}/operations
→ список DTO (type, amount, counterparty, createdAt), новые сверху
```

404 если кошелька нет. Пустой список — если операций ещё не было.

---

## 5. Мелочи

- `@Transactional` на deposit/withdraw тоже (запись balance + operation атомарно)  
- `fromDto` — только entity; `save` в `create`  
- Enum для type — не строки «deposit» в коде наугад

---

Проверка: create → deposit → transfer → GET operations — видишь все шаги.

Готово → **`done spring`**.
