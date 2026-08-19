-- Выполни в DBeaver / psql под суперпользователем (обычно postgres)
-- Создаёт БД и пользователя для wallet-service

CREATE USER wallet WITH PASSWORD 'wallet';

CREATE DATABASE wallet OWNER wallet;

GRANT ALL PRIVILEGES ON DATABASE wallet TO wallet;
