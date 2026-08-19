# Handoff: Java Backend Mentorship → следующий ИИ

**Дата снимка:** 16 августа 2026  
**Workspace:** `C:\Users\07042\Projects\java-backend-2026`  
**Основной чат-история:** Cursor agent transcript `0dcd42ce-2144-418f-9702-4aabbbf9b15e` («Java Backend Mentorship»)  
**Трекер:** `docs/day-tracker.md`  
**Теория:** `docs/theory/*.md` (28 файлов)

Этот документ — полный контекст для продолжения менторства **без** генерации готовых решений задач и бизнес-логики за студента.

---

## 1. Кто студент и цель

- CS-студент (Россия), цель: **стажировка / junior Java backend**.
- Приоритет компаний: Т-Банк (Т-Старт), VK, Яндекс, Wildberries, Сбер (сложнее по гео), плюс менее хайповые: Иннотех (ВТБ), Наумен, Лига ЦЭ, ICL, Ростелеком ИТ, Positive Technologies.
- Формат: **удалёнка / гибрид из региона**.
- Вторая дверь: **AQA на Java** (та же база).
- Самооценка часто занижена; бывает стыд после «подсмотрел» — лучше диаграммы и наводящие вопросы, не жаргон.
- Нужна **теория перед кодом** (15–25 мин), потом задача. Плохо реагирует на «сразу пиши, разберёшься».
- Слабые зоны: связные списки (долго не складывалась картинка), указатели/in-place, иногда синтаксис Java vs Python (`[1,1]`, `result[i]`).
- Эмоционально: может злиться, если не дали синтаксис API (Queue и т.п.) — API/синтаксис давать можно; **полное решение алгоритма — нет**.

### Оценка готовности (озвучивалась ментором)

| Момент | Intern screening | Прямой junior |
|--------|------------------|---------------|
| После дня 4 | ~38/100 | ~20/100 |
| После дней 5–9 (≈10 авг) | ~52/100 | ~28/100 |
| После дня 12 (история операций, ≈13–16 авг) | **~60/100** | **~33/100** |

**Вывод на 16.08:** отклики на **стажировки уже уместны**; на «Junior 1+ год Spring» — рано. Воронки длинные — заявки не откладывать «пока идеально».

---

## 2. Жёсткие правила менторства (ОБЯЗАТЕЛЬНО)

Триггеры пользователя:

| Сообщение | Действие |
|-----------|----------|
| **`done`** | Проверить его файл (прогнать `main`/compile), разобрать код **его**, похвалить/замечания, выдать **следующее** задание (stub + теория при необходимости) |
| **`pick`** | Только слова, схемы, наводящие вопросы. **Без готового кода решения** |
| **`done spring`** | Ревью Spring-части дня |

Запрещено:

- Писать **полное рабочее решение** алгоритма или готовый рабочий transfer/deposit «под ключ».
- В stubs — только TODO / пустые return.
- После `done` — учить **на его коде**, потом next.

Разрешено:

- Теория в `docs/theory/*.md`.
- Синтаксис/API Java (`Queue`, `ArrayDeque`, enum, аннотации) — это не «решение задачи».
- Указывать баги в его коде словами / одной строкой-цитатой из файла.
- Рефакторинг-идеи словами (например `applyBalanceChange`).

Коммуникация: по-русски, прямо, кратко; без лишней воды; смелый код-ревью.

---

## 3. Репозиторий и окружение

```
java-backend-2026/
  wallet-service/     # Spring Boot 3.3.13, Java 21, Maven Wrapper
  algorithms/solutions/   # ~38 Java-файлов LeetCode
  docs/theory/        # ликбезы
  docs/day-tracker.md
  docs/learning-plan.md (план августа, частично устарел по галочкам)
```

- ОС: Windows, PowerShell.
- Запуск алго: VS Code/Cursor config **`Java`** / F5, `preLaunchTask` компилирует в `bin`. CodeLens Run над `main` часто ломался (ClassNotFound) — не советовать.
- IDE: монорепо + Jakarta Persistence иногда «does not exist» — открыть модуль `wallet-service` / Maven reload; `mvnw compile` — источник правды.
- Lombok: не комбинировать `@NoArgsConstructor` + `@RequiredArgsConstructor` на DTO → дубликат ctor.
- БД: **локальный PostgreSQL** (без Docker в текущем сетапе). user/db/password: `wallet`/`wallet`, `localhost:5432/wallet`. Init: `wallet-service/sql/init-local-db.sql` — в DBeaver **по одному** statement (`CREATE DATABASE` не в одной транзакции).
- `spring.jpa.hibernate.ddl-auto=update`.

Пакет: `ru.learning.wallet`.

---

## 4. Pet-проект wallet-service — текущее состояние

### API

| Метод | URL | Смысл |
|-------|-----|--------|
| POST | `/api/wallets/create` | создать |
| GET | `/api/wallets/{id}` | получить |
| POST | `/api/wallets/{id}/deposit` | пополнить `{ "amount": N }` |
| POST | `/api/wallets/{id}/withdraw` | снять |
| POST | `/api/wallets/transfer` | перевод `fromId`, `toId`, `amount` |
| GET | `/api/wallets/{id}/operations` | история операций |
| — | actuator health | есть |

### Слои

- **Entity:** `Wallet` (id, owner, balance); `WalletOperation` (walletId, type enum, amount, counterpartyWalletId, createdAt); `OperationType` enum: DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT.
- **DTO:** WalletRequest/Response, AmountRequest (`@NotNull` `@Positive`), TransferRequest (`@NotNull` from/to/amount), TransferResponse (from+to WalletResponse), OperationResponse.
- **Service:** `toDto(Wallet)`, `toDto(WalletOperation)`, `fromDto(WalletRequest)` — **внимание:** в `fromDto` до сих пор часто делают `save` (мелочь: save только в `create`).
- **Ядро:** `applyBalanceChange(walletId, delta, type, counterpartyId)` — find, balance+delta, save wallet, создать WalletOperation, save operation, return toDto. Deposit/withdraw/transfer вызывают его.
- **Transfer:** проверка `fromId.equals(toId)` → `SameWalletTransferException` (400); два apply (OUT/IN); `@Transactional` на public `transfer`.
- **Exceptions + `@RestControllerAdvice`:** WalletNotFound → 404; InsufficientFunds, SameWalletTransfer → 400.
- **Repository:** `WalletRepository`; `WalletOperationRepository.findByWalletIdOrderByCreatedAtDesc(Long)` — derived query Spring Data (студент сначала бесился на длинное имя — объяснить как соглашение, не полиморфизм).

### Известные техдолги / замечания ментора (ещё актуальны)

1. `@Transactional` на **`private` `applyBalanceChange`** почти бесполезен (Spring proxy не видит private + self-invoke). Нужно на **public** `deposit`/`withdraw` (на transfer уже есть).
2. На `OperationType` в entity желательно `@Enumerated(EnumType.STRING)`.
3. `fromDto` не должен содержать `save`.
4. Условие баланса: должно быть `(balance + delta) >= 0` (не `> 0`) — студент вроде поправил.
5. Нет unit/integration тестов, нет Flyway, нет security — следующие этапы после деревьев.
6. Студент сам просил день с `toDto`/`fromDto` — сделано; спрашивал про static vs instance — ответ: не обязаны static; Spring уже создаёт бин; save не в маппере.

### Важные педагогические моменты по Spring

- «Много классов на простую логику» — слои vs шум; маппинг выносить.
- `@NotNull` на fromId/toId — не про генерацию id БД, а про JSON request при transfer.
- SameWalletTransfer — бизнес-правило в service, не Bean Validation.
- Нельзя два `findById(Long)` с разным return type в одном классе — это не полиморфизм; история → `getOperations`.
- Entity наружу не отдаём → OperationResponse.

---

## 5. Прогресс по дням (сжато)

| Дни | Spring | Алгоритмы (темы) |
|-----|--------|------------------|
| 1–4 | старт, Java Core labs | Two Sum, Anagram, Duplicate, Stock, Parentheses + equals/hashCode |
| 5–6 | Boot skeleton, REST DTO, Service, in-memory Map | Majority, Remove Dup, First Unique, Roman, strStr, Power of Two |
| 7 | GET by id, 404 advice | Is Subsequence, Perfect Square, Number of 1 Bits |
| 8 | JPA + Postgres | Merge Sorted Array, Remove Element, Search Insert |
| 9 | deposit/withdraw + validation + InsufficientFunds | Sqrt, Excel Column, Pascal Triangle |
| 10 | `@Transactional` + transfer + TransferRequest/Response | Parentheses/Stock skip (уже решал); Linked List Cycle (HashSet узлов) |
| 11 | toDto/fromDto маппинг; SameWallet + @NotNull | Merge Two Lists, Max Depth, Invert Tree |
| 12 | WalletOperation + applyBalanceChange + GET operations | Level Order (BFS) ✅ |
| **13 (сейчас)** | — | **Symmetric Tree ← TODO**; Path Sum next |

Студент долго не понимал связные списки → теория `03-linked-list-intro.md`, таблица по Reverse; Cycle сначала с val в Set (баг), потом Set&lt;ListNode&gt;.

Деревья: max depth и invert — ок; level order — разобрали очередь, `size` этажа, `count--`; **Symmetric Tree stub готов, студент ещё не сдал `done`**.

---

## 6. Алгоритмы — файлы

Путь: `algorithms/solutions/*.java`  
Паттерн: класс с `main`, комментарий LeetCode URL, `done` / `pick`.

Уже закрыты (среди прочих): TwoSum, ValidParentheses, BestTime..., LinkedListCycle, ReverseLinkedList, MergeTwoSortedLists, MaxDepth, InvertBinaryTree, PascalsTriangle, BinaryTreeLevelOrderTraversal, много Easy массивы/строки/биты.

**Сейчас:** `SymmetricTree.java` + теория `docs/theory/28-symmetric-tree.md`  
**Потом:** Path Sum (ещё не выдан stub — выдать после `done` по Symmetric).

---

## 7. Что делать дальше (приоритет)

1. **Symmetric Tree** → `done` → review → **Path Sum** (закрыть день 13 алго).
2. Spring polish: `@Transactional` на deposit/withdraw; `@Enumerated(STRING)`; `fromDto` без save.
3. **Резюме + GitHub** wallet-service; 10–20 откликов на intern/trainee (не middle).
4. Потом по роадмапу: тесты (`@SpringBootTest` / MockMvc), Flyway, чуть SQL JOIN/индексы, 1–2 Medium в неделю, security позже.
5. Floyd cycle (O(1) memory) — опционально после стабильного HashSet-варианта.

---

## 8. Стиль подсказок, который работает

- Картинки ASCII, таблицы «шаг | состояние».
- Числовой прогон на маленьком примере.
- «Где val?» → две структуры: `result` и `level`.
- Не путать: derived query Spring Data ≠ полиморфизм; перегрузка `toDto(Wallet)` / `toDto(WalletOperation)` — ок (разные параметры).
- При фрустрации: дать синтаксис API, не алгоритм целиком.
- Триггер `газ` = «продолжай следующий шаг без лишних вопросов».

---

## 9. Чего студент уже может рассказать на интервью (проект)

«Сделал wallet-service на Spring Boot 3 / Java 21 / JPA / PostgreSQL: CRUD кошелька, deposit/withdraw с validation, атомарный transfer с `@Transactional`, история операций (enum + audit table), DTO-маппинг, exception handler 404/400, derived query для списка операций.»

Дыры, которые спросят: тесты, изоляция транзакций глубже, concurrent transfer, Flyway, security.

---

## 10. Быстрый чеклист для нового ИИ при старте сессии

1. Прочитать `docs/day-tracker.md` и этот handoff.  
2. При алгоритме — открыть актуальный `algorithms/solutions/*.java`.  
3. Соблюдать `done` / `pick`.  
4. Не решать Symmetric/Path Sum за него.  
5. Обновлять `day-tracker.md` при закрытии задач.  
6. Теорию класть в `docs/theory/NN-....md` при новых темах.  
7. Коммиты — только если студент явно попросил.

---

**Конец handoff.** При сомнении — смотреть код на диске, не только этот текст (студент правит файлы между сессиями).
