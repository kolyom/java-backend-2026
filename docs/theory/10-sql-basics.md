# SQL с нуля (день 4) — развёрнуто

Цель дня: читать и писать простые запросы к PostgreSQL так, как спрашивают на стажировке.

---

## 1. Что такое таблица

Таблица = как Excel-лист:
- **строка** — одна запись (один пользователь, один заказ)
- **столбец** — поле (`id`, `name`, `price`)
- **тип** — integer, text, numeric, timestamp, …

Пример `users`:

| id | name  | city    |
|----|-------|---------|
| 1  | Anna  | Moscow  |
| 2  | Bob   | Kazan   |
| 3  | Clara | Moscow  |

`id` часто **PRIMARY KEY** — уникальный идентификатор строки.

---

## 2. SELECT — «достань данные»

Синтаксис:

```sql
SELECT столбцы
FROM таблица;
```

Примеры:

```sql
SELECT * FROM users;                 -- все столбцы
SELECT name, city FROM users;        -- только имя и город
SELECT name AS user_name FROM users; -- столбец в результате назовётся user_name
```

`*` = все колонки (для учёбы ок, в проде чаще перечисляют явно).

---

## 3. WHERE — фильтр строк

```sql
SELECT name FROM users
WHERE city = 'Moscow';
```

Операторы: `=`, `<>` / `!=`, `<`, `>`, `<=`, `>=`, `AND`, `OR`, `IN`, `LIKE`, `IS NULL`.

```sql
SELECT * FROM users
WHERE city = 'Moscow' AND id > 1;

SELECT * FROM users
WHERE city IN ('Moscow', 'Kazan');

SELECT * FROM users
WHERE name LIKE 'A%';   -- имя начинается на A
```

Строки в SQL — в **одинарных** кавычках: `'Moscow'`.

---

## 4. ORDER BY и LIMIT

```sql
SELECT * FROM users
ORDER BY name ASC;    -- по возрастанию

SELECT * FROM users
ORDER BY id DESC
LIMIT 2;              -- две строки с конца по id
```

---

## 5. Две таблицы и зачем JOIN

`orders`:

| id | user_id | amount |
|----|---------|--------|
| 10 | 1       | 100    |
| 11 | 1       | 50     |
| 12 | 2       | 200    |

`user_id` ссылается на `users.id` — это связь.

Без JOIN ты видишь только номера. С JOIN — имя рядом с заказом.

### INNER JOIN — только совпавшие пары

```sql
SELECT u.name, o.amount
FROM users u
INNER JOIN orders o ON o.user_id = u.id;
```

Читай: «возьми users и orders, склей строки где `orders.user_id = users.id`».

`u` и `o` — короткие имена (алиасы), чтобы не писать длинно.

Результат для данных выше:

| name | amount |
|------|--------|
| Anna | 100    |
| Anna | 50     |
| Bob  | 200    |

Clara без заказов **не попадёт** в INNER JOIN.

### LEFT JOIN — все слева + совпадения справа

```sql
SELECT u.name, o.amount
FROM users u
LEFT JOIN orders o ON o.user_id = u.id;
```

Clara будет со строкой, где `amount` = NULL (заказов нет).

На старте чаще всего спрашивают **INNER JOIN**.

---

## 6. GROUP BY — агрегаты

«Посчитай что-то **по группам**».

Функции: `COUNT`, `SUM`, `AVG`, `MIN`, `MAX`.

```sql
-- сколько заказов у каждого пользователя
SELECT user_id, COUNT(*) AS order_count
FROM orders
GROUP BY user_id;
```

```sql
-- сумма трат по пользователю + имя
SELECT u.name, SUM(o.amount) AS total
FROM users u
INNER JOIN orders o ON o.user_id = u.id
GROUP BY u.name;
```

Правило: в `SELECT` рядом с агрегатом можно только то, что есть в `GROUP BY` (или сам агрегат).  
Нельзя: `SELECT name, amount` при `GROUP BY name`, если `amount` не агрегирован.

Фильтр **после** группировки — `HAVING` (не WHERE):

```sql
SELECT user_id, SUM(amount) AS total
FROM orders
GROUP BY user_id
HAVING SUM(amount) > 100;
```

- `WHERE` — до группы (фильтр строк)  
- `HAVING` — после группы (фильтр групп)

---

## 7. Порядок написания vs порядок выполнения (полезно помнить)

Пишешь обычно:

`SELECT → FROM → JOIN → WHERE → GROUP BY → HAVING → ORDER BY → LIMIT`

Думаешь логически ближе к:

`FROM/JOIN → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT`

---

## 8. Что делать руками сегодня

1. Прочитай `sql/day04/README.md` — как открыть базу.  
2. Выполни seed (создаст таблицы и данные).  
3. Решай задачи из `sql/day04/tasks.md`.  
4. Пиши запросы в `sql/day04/answers.sql`.  
5. В чат: `done` по SQL-блоку (когда все задачи из tasks сделаны) или `pick` по конкретной задаче.

Готовые ответы за тебя не пишу — только теория и наводки.
