# SQL день 4 — как запустить

Нужен PostgreSQL. Варианты:

## Вариант A — Docker (если Docker Desktop запущен)

В PowerShell из корня проекта:

```powershell
docker run --name pg-day04 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=learning -p 5432:5432 -d postgres:16
```

Подожди ~5 секунд, затем:

```powershell
Get-Content sql\day04\seed.sql -Raw | docker exec -i pg-day04 psql -U postgres -d learning
```

Интерактивно:

```powershell
docker exec -it pg-day04 psql -U postgres -d learning
```

Внутри `psql` вставляешь запросы из `answers.sql` / пробуешь руками.

Остановить позже:

```powershell
docker stop pg-day04
docker rm pg-day04
```

## Вариант B — уже стоит локальный Postgres

Подключись клиентом (DBeaver, pgAdmin, `psql`) к своей БД и выполни `seed.sql`.

## Вариант C — без установки

[https://www.db-fiddle.com](https://www.db-fiddle.com) → PostgreSQL → в левую панель вставь содержимое `seed.sql` → Build Schema → справа пиши SELECT.

---

Дальше: задачи в `tasks.md`, ответы — в `answers.sql`.
