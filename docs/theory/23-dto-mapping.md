# DTO ↔ Entity маппинг (день 11)

Сейчас в `WalletService` одно и то же копируется вручную:

```text
entity.getId() → response.setId()
entity.getOwner() → response.setOwner()
...
```

Это **не бизнес-логика** — это шум. Выносим в отдельные методы.

---

## 1. Зачем DTO вообще

| Слой | Что наружу |
|------|------------|
| Entity (`Wallet`) | как лежит в БД, JPA-аннотации |
| Response DTO | что отдаём клиенту в JSON |
| Request DTO | что принимаем при create / transfer |

Entity **не** отдаём в REST напрямую: лишние поля, связи, Hibernate-прокси, смена схемы БД ломает API.

---

## 2. Два направления

| Метод | Направление | Когда |
|-------|-------------|--------|
| `toDto` / `toResponse` | `Wallet` → `WalletResponse` | после find, save, deposit, withdraw, transfer |
| `toEntity` / `fromRequest` | `WalletRequest` → `Wallet` | только **create** (новый объект без id) |

`AmountRequest` / `TransferRequest` — не превращаем в entity целиком: там только сумма или id для операций.

---

## 3. Где держать методы

**Вариант A (день 11):** private-методы внутри `WalletService`:

```text
private WalletResponse toResponse(Wallet wallet)
private Wallet fromRequest(WalletRequest request)
```

**Вариант B (позже):** отдельный `@Component WalletMapper` — когда мапперов много или хочешь unit-тесты только на маппинг.

На старте достаточно A.

---

## 4. Правила

1. **Не мутируй** entity в `toResponse` — только читаешь и собираешь DTO.
2. **`toEntity` для create** — новый `Wallet()`, поля из request, **id не трогаешь** (генерит БД).
3. После рефакторинга в `create` / `findById` / `deposit` / `withdraw` / `transfer` **не должно остаться** ручного `new WalletResponse()` + три `set` — только вызов `toResponse(saved)`.
4. Имена: `toResponse` и `toEntity` — норм; `toDto` тоже ок, главное — единообразие в проекте.

---

## 5. Пример формы (не копируй слепо — напиши сам)

```text
toResponse(wallet):
  создать WalletResponse
  скопировать id, owner, balance
  вернуть

toEntity(request):
  создать Wallet
  owner и balance из request
  вернуть (без save — save остаётся в service)
```

---

## 6. Чеклист готовности

- [ ] `create` использует `toEntity` + `toResponse`
- [ ] `findById`, `deposit`, `withdraw` — только `toResponse`
- [ ] `transfer` — два вызова `toResponse`, без дублирования set-ов
- [ ] `mvn compile` проходит
- [ ] Postman/curl: create → get → deposit → transfer — как раньше

---

## 7. Бонус (если останется время)

- `@NotNull` на `fromId` / `toId` в `TransferRequest`
- `fromId.equals(toId)` → 400 (своё исключение или `IllegalArgumentException` в handler)

Когда Spring готов → **`done spring`**.
