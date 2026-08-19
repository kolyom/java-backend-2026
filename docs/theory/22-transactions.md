# Транзакции + transfer (день 10)

У тебя уже есть create / get / deposit / withdraw через JPA + PostgreSQL.  
Сегодня — **атомарный перевод** между двумя кошельками и понимание **`@Transactional`**.

---

## 1. Зачем транзакции

Представь перевод: снять 100 с A, положить 100 на B.

Если после снятия упадёт JVM / ошибка БД — деньги «исчезли».  
**Транзакция** = «либо оба шага, либо ни одного» (atomic).

В Spring Data JPA запись в БД обычно уходит при **commit** транзакции (или flush).  
Метод сервиса без явной транзакции часто всё равно в транзакции репозитория на один вызов — но **два** `save` подряд в одном бизнес-методе лучше обернуть явно.

---

## 2. `@Transactional`

Пакет: `org.springframework.transaction.annotation.Transactional`.

Вешают обычно на **публичные методы Service** (не на private — proxy Spring их не перехватит).

```java
@Transactional
public WalletResponse deposit(Long id, Long amount) { ... }
```

Смысл: весь метод — одна транзакция. Исключение (runtime) → **rollback**. Успешный выход → **commit**.

Для чтения иногда ставят `@Transactional(readOnly = true)` — подсказка БД/ Hibernate.

---

## 3. Задание: transfer

### Service

```text
transfer(fromId, toId, amount):
  найти from и to (нет → WalletNotFoundException)
  fromId == toId → своё исключение или IllegalArgumentException → 400
  amount > 0 (валидация DTO)
  if from.balance < amount → InsufficientFundsException
  from.balance -= amount
  to.balance += amount
  // dirty checking / save — как у тебя уже принято
  вернуть что удобно (например to, или оба — реши сам и зафиксируй в API)
```

Метод **обязан** быть `@Transactional`: списание и зачисление в одной транзакции.

### DTO

Например `TransferRequest`:

- `fromId` / `toId` (или from в path, to + amount в body — выбери один стиль и держись его)
- `amount` с `@NotNull` `@Positive`

### Controller

`POST /api/wallets/transfer`  
тело — `TransferRequest`, ответ — `WalletResponse` (или оба баланса — как решишь).

---

## 4. Что проверить руками

1. Два create → transfer 50 → балансы верные  
2. transfer больше чем есть → 400  
3. несуществующий id → 404  
4. from == to → 400  

---

## 5. Частые ловушки

| Ловушка | Почему |
|---------|--------|
| `@Transactional` на private | proxy не видит |
| self-invoke `this.transfer(...)` из того же класса | обход proxy → аннотация не сработает |
| checked exception без `rollbackFor` | по умолчанию rollback только на unchecked |
| два отдельных commit без общей транзакции | риск «полуперевода» |

---

Когда Spring готов — напиши **`done spring`**.
