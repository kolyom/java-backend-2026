# Spring Data JPA + PostgreSQL (день 8)

Сейчас кошельки живут в `HashMap` в памяти: перезапустил приложение — данные пропали.  
Сегодня: **сохранять в PostgreSQL** через Spring Data JPA.

---

## 1. Зачем слои меняются

**Было (день 6–7):**

```text
Controller → Service → HashMap
```

**Станет:**

```text
Controller → Service → Repository → PostgreSQL
                              ↑
                           Entity
```

| Слой | Роль |
|------|------|
| **Entity** | Java-класс ↔ строка таблицы |
| **Repository** | `save`, `findById` — Spring пишет SQL |
| **Service** | бизнес-логика; больше не держит Map |
| **DTO** | то, что ходит по HTTP (Request/Response) — **не** путать с Entity |

---

## 2. Entity ≠ DTO

| | Entity | DTO |
|---|--------|-----|
| Зачем | хранение в БД | обмен с клиентом |
| Аннотации | `@Entity`, `@Id`, … | обычно только Lombok |
| Пример | `Wallet` | `WalletRequest`, `WalletResponse` |

Service принимает DTO → собирает/обновляет Entity → через Repository в БД → обратно собирает DTO для ответа.

Не отдавай Entity напрямую из controller (привычка на проде).

---

## 3. Что такое JPA / Hibernate / Spring Data

- **JPA** — набор правил/аннотаций: «этот класс = таблица»
- **Hibernate** — реализация JPA (Spring Boot подключает её сам)
- **Spring Data JPA** — интерфейсы `JpaRepository`: ты пишешь метод, Spring делает SQL

Тебе сегодня: Entity + `JpaRepository` + настройки datasource.

---

## 4. Entity — как выглядит идея

Класс, например `Wallet`:

- `@Entity` — это сущность БД
- `@Table(name = "wallets")` — имя таблицы (можно опустить, тогда имя по классу)
- `@Id` — первичный ключ
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` — id выдаёт БД (serial/bigserial)
- поля `owner`, `balance` — колонки

Lombok: `@Getter` `@Setter` или `@Data` + `@NoArgsConstructor` (JPA нужен пустой конструктор).

---

## 5. Repository

Интерфейс, **без** реализации класса:

```text
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
```

- `Wallet` — тип сущности
- `Long` — тип id

Бесплатно получаешь:
- `save(entity)`
- `findById(id)` → `Optional<Wallet>`
- `existsById`, `delete`, …

Spring сам создаст bean. В Service внедряешь через конструктор (`@RequiredArgsConstructor` + `private final WalletRepository ...`).

---

## 6. PostgreSQL локально (без Docker)

У тебя уже стоит PostgreSQL 16 (как в день 4) — **Docker не нужен**.

### Один раз: создать БД

В DBeaver или psql под пользователем `postgres` выполни скрипт:

`wallet-service/sql/init-local-db.sql`

Или руками:

```sql
CREATE USER wallet WITH PASSWORD 'wallet';
CREATE DATABASE wallet OWNER wallet;
```

Если пользователь `wallet` уже есть — шаг с USER пропусти.  
Если БД `wallet` уже есть — ок.

Подключение в DBeaver для проверки:
- host: `localhost`
- port: `5432`
- database: `wallet`
- user: `wallet`
- password: `wallet`

### Если хочешь под своим postgres-пользователем

В `application.properties` поставь свои username/password и создай БД `wallet` себе.  
URL тот же: `jdbc:postgresql://localhost:5432/wallet`

### Docker (опционально, не сегодня)

`docker-compose.yml` лежит на будущее. Сейчас его **не** запускай.

---

## 7. application.properties

Нужны строки вида:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/wallet
spring.datasource.username=wallet
spring.datasource.password=wallet
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### ddl-auto

| значение | смысл |
|----------|--------|
| `update` | создать/дополнить таблицы по Entity (удобно учиться) |
| `validate` | только проверить, не менять |
| `none` | ничего не делать |
| `create-drop` | создать при старте, удалить при стопе (для тестов) |

На учёбе ок `update`. На проде обычно миграции (Flyway) — позже.

`show-sql=true` — в консоли увидишь SQL, это полезно для понимания.

---

## 8. Зависимости в pom.xml

Нужны (уже добавим в проект):

- `spring-boot-starter-data-jpa`
- `postgresql` (runtime)

После изменения pom — **Reload Maven**.

---

## 9. Как переписать Service (логика словами)

### create

1. Из `WalletRequest` собрать `Wallet` (owner, balance; id не ставь — БД сама)
2. `walletRepository.save(wallet)` — вернётся entity уже **с id**
3. Собрать `WalletResponse` из entity и вернуть

`nextId` и `HashMap` — **удалить**.

### findById

1. `walletRepository.findById(id)` → `Optional`
2. Если пусто → `throw new WalletNotFoundException(id)`
3. Если есть → entity → `WalletResponse`

Controller **не меняется** по смыслу (те же URL).

---

## 10. Порядок работы сегодня

1. Прочитать эту теорию
2. Создать БД локально (`sql/init-local-db.sql` в DBeaver) — **без Docker**
3. Reload Maven (зависимости JPA уже в pom)
4. Проверить `application.properties` (url/user/password)
5. Создать `entity/Wallet.java`
6. Создать `repository/WalletRepository.java`
7. Переписать `WalletService` на Repository
8. Запустить, проверить create → рестарт → GET

---

## 11. Частые ошибки

| Симптом | Причина |
|---------|---------|
| Не стартует, ошибка datasource | Postgres не запущен / неверный url |
| `Connection refused` | Docker не up или порт 5432 занят |
| Таблицы нет | нет `@Entity` / не тот пакет / ddl-auto |
| id всегда null | забыли `@GeneratedValue` или смотришь DTO до save |
| Advice не ловит | пакеты ок? исключение то же? |

Entity должна лежать под `ru.learning.wallet` (сканирование от `@SpringBootApplication`).

---

## 12. Карта пакетов

```text
ru.learning.wallet/
  entity/Wallet.java
  repository/WalletRepository.java
  service/WalletService.java      ← без Map
  controller/...                  ← как был
  dto/...
  exception/...
```

---

## 13. Задание (Spring день 8)

1. Поднять Postgres  
2. Entity + Repository  
3. Service через БД, Map убрать  
4. create + get работают после рестарта  
5. В чат: **`done spring`**

`pick spring` — подсказка словами.
