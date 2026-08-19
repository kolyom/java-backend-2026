-- День 4: учебная схема (PostgreSQL)
-- Выполни целиком один раз после подключения к БД.

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id   INT PRIMARY KEY,
    name TEXT NOT NULL,
    city TEXT NOT NULL
);

CREATE TABLE orders (
    id      INT PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    amount  INT NOT NULL,
    created DATE NOT NULL
);

INSERT INTO users (id, name, city) VALUES
    (1, 'Anna',  'Moscow'),
    (2, 'Bob',   'Kazan'),
    (3, 'Clara', 'Moscow'),
    (4, 'Dima',  'Sochi');

INSERT INTO orders (id, user_id, amount, created) VALUES
    (10, 1, 100, '2026-01-10'),
    (11, 1,  50, '2026-01-15'),
    (12, 2, 200, '2026-02-01'),
    (13, 2,  80, '2026-02-10'),
    (14, 3, 120, '2026-03-01');

-- Dima специально без заказов (для LEFT JOIN / понимания INNER)
