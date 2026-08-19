# wallet-service — день 8 (локальный Postgres, без Docker)

## БД один раз

1. Открой DBeaver → подключение к локальному PostgreSQL (пользователь `postgres`)
2. Выполни скрипт `sql/init-local-db.sql`
3. Проверь, что есть БД `wallet` и можешь зайти user/password `wallet`/`wallet`

## application.properties

Уже настроено на:

- url: `jdbc:postgresql://localhost:5432/wallet`
- user: `wallet`
- password: `wallet`

Если используешь другого пользователя — поправь properties под себя.

## Дальше

Теория: `docs/theory/20-spring-jpa-postgres.md`  
Затем Entity + Repository + переписать Service.

Docker не трогаем.
