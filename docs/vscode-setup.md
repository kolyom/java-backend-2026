# VS Code — как работать

Код пишешь и запускаешь **здесь**, в VS Code.  
Менторство — в чате Cursor (тот же репозиторий `java-backend-2026`).

## Один раз настроить

1. **File → Open Folder…** → `C:\Users\07042\Projects\java-backend-2026`
2. VS Code предложит поставить **Extension Pack for Java** — установи (или поставь из `.vscode/extensions.json`).
3. Дождись, пока справа внизу Java-проект проиндексируется (может занять минуту).

JDK у тебя уже есть (Java 21).

## Как запускать лабы

1. Открой `labs/EqualsHashCodeLab.java`
2. Над `main` нажми **Run Java** (▶)
3. Или **F5** → конфигурация `Lab: EqualsHashCode`
4. Вывод — во вкладке **TERMINAL** / **DEBUG CONSOLE**

## Как запускать решения LeetCode

Файлы лежат плоско: `algorithms/solutions/BinarySearch.java` (без папок `day-02` — дефис ломает Java).

**Вариант A — скрипт (надёжнее):** в терминале из корня проекта:

```powershell
.\run-java.ps1 BinarySearch
```

**Вариант B — VS Code:** открой файл → **Run Java** над `main`.  
Если `ClassNotFoundException` — сначала скрипт (вариант A), потом Ctrl+Shift+P → `Java: Clean Java Language Server Workspace`.

На LeetCode по-прежнему копируешь класс, переименовав в `Solution`.

## Связь с ментором

В Cursor-чате пиши как раньше:

- `review labs/EqualsHashCodeLab.java`
- `compile ...`
- `stuck ...`
- `accepted ...`

Путь тот же — оба редактора смотрят одну папку на диске.
