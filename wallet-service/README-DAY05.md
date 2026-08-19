# wallet-service — день 5

## Запуск (рабочий способ)

Открой терминал в папке `wallet-service`:

```powershell
cd C:\Users\07042\Projects\java-backend-2026\wallet-service
.\run.ps1
```

Или напрямую:

```powershell
.\mvnw.cmd spring-boot:run
```

Первый запуск может занять 1–3 минуты (скачивает Maven и зависимости).

Успех = в консоли:
```text
Started WalletServiceApplication
```

Проверка: `http://localhost:8080/api/health` → `Ok`

### Если порт 8080 занят

```powershell
netstat -ano | findstr :8080
taskkill /PID <номер_из_последней_колонки> /F
```

Или поменяй в `application.properties`:
```properties
server.port=8081
```

---

## VS Code Run (опционально)

Если зелёная кнопка Run запускает `-cp ...\bin WalletServiceApplication` — **игнорируй**, это не Spring.

Можно открыть workspace-файл из корня репо:
`java-backend-2026.code-workspace` → там `wallet-service` отдельной папкой.

## Твоё задание

1. Создай пакет `ru.learning.wallet.controller`.
2. Класс `HealthController` (или `PingController`):
   - `@RestController`
   - `@RequestMapping("/api")`
   - GET `/health` или `/ping` → ответ `"OK"` (или свой JSON)
3. Проверь в браузере: `http://localhost:8080/api/health`

## Готово

В чат: `done spring`

## Если не стартует

`pick spring` + текст ошибки из консоли.

Сегодня **без PostgreSQL** — только web. JPA подключим позже.
