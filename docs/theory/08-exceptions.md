# Тема 6 — Исключения (развёрнуто)

Цель: понимать, что происходит при ошибке, уметь самому кинуть исключение и поймать его в `try/catch`.  
Лаба: `labs/oop/ExceptionLab.java`.

---

## 1. Зачем исключения вообще

Обычный `return` плохо подходит для ошибок:

```java
int parse(String s) {
    // если ошибка — вернуть что? -1? 0? Но -1 может быть валидным ответом
}
```

Исключение — отдельный канал: «нормальный результат» vs «что-то пошло не так».  
Метод либо возвращает значение, либо **прерывается** с исключением.

---

## 2. Что такое исключение

Это **объект** (наследник `Throwable` → обычно `Exception`).  
В нём есть тип (`NumberFormatException`, `IllegalArgumentException`, …) и сообщение (`getMessage()`).

Когда исключение **кинули** и никто не поймал — программа падает, в консоли stack trace (цепочка вызовов).

---

## 3. Строку в число: `Integer.parseInt`

Синтаксис:

```java
int x = Integer.parseInt("42");   // x == 42
int y = Integer.parseInt(s);      // s — переменная типа String
```

Важно:

| Вход | Результат |
|------|-----------|
| `"42"` | `42` |
| `" 42 "` | часто тоже ок (с пробелами по краям — зависит; лучше `s.trim()`) |
| `"abc"` | **не** вернёт число — кинет `NumberFormatException` |
| `null` | `NumberFormatException` или NPE (не передавай null) |

`parseInt` возвращает примитив **`int`**, не `Integer`.

Для `Long`:

```java
Long z = Long.parseLong("100000");
```

В лабе используй именно:

```java
int n = Integer.parseInt(s);
```

---

## 4. Сам кинуть исключение: `throw`

Синтаксис:

```java
throw new IllegalArgumentException("must be positive");
```

Разбор:

- `new IllegalArgumentException("...")` — создали объект ошибки с текстом  
- `throw` — **прервали** метод и отправили ошибку «вверх» вызывающему  

После `throw` следующие строки метода **не выполняются**.

Пример в контексте лабы (идея):

```java
int n = Integer.parseInt(s);
if (n <= 0) {
    throw new IllegalArgumentException("must be positive");
}
return n;
```

---

## 5. Поймать: `try` / `catch`

Синтаксис:

```java
try {
    // код, который может кинуть исключение
    int x = parsePositive("42");
    System.out.println(x);
} catch (IllegalArgumentException e) {
    // сюда попадём, только если кинули IllegalArgumentException
    System.out.println(e.getMessage());
}
```

Несколько разных ошибок — несколько `catch` подряд (от более частных к общим, если нужно):

```java
try {
    int x = parsePositive(s);
    System.out.println(x);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
} catch (NumberFormatException e) {
    System.out.println("парсинг не удался");
}
```

Или три отдельных `try` в `main` — для лабы так даже нагляднее (каждый сценарий отдельно).

### Что такое переменная `e`

Это пойманный объект исключения. Полезное:

```java
e.getMessage();   // текст, который передали в new ...( "текст" )
e.printStackTrace(); // печатает полный след (для отладки)
```

---

## 6. «Пробросить наружу» и слово `throws`

Иногда метод **не ловит** ошибку, а говорит вызывающему: «разбирайся ты».

Для **checked**-исключений компилятор требует либо `catch`, либо объявить в сигнатуре:

```java
public void read(String path) throws IOException {
    // работа с файлом может кинуть IOException
}
```

`throws` в заголовке метода = предупреждение: «при вызове меня может прилететь `IOException`».

Вызывающий тогда пишет свой `try/catch` или тоже `throws`.

### Для твоей лабы

`IllegalArgumentException` и `NumberFormatException` — это **Runtime** (unchecked).  
Им **`throws` писать не обязательно**. Метод может просто делать `throw new ...` без `throws` в сигнатуре.

---

## 7. Checked vs Runtime — зачем Runtime

**Checked** (например `IOException`):  
компилятор заставляет обработать или объявить. Типично «внешний мир» — диск, сеть.

**Runtime / unchecked** (например `IllegalArgumentException`, `NullPointerException`, `NumberFormatException`):  
компилятор не заставляет. Типично:

- кривые аргументы метода  
- ошибки программиста (забыли проверить null)  
- неудачный парсинг, если решили не делать его checked  

Зачем так сделали: иначе каждый второй метод был бы обвешан `throws` из‑за мелких проверок аргументов.

На бэкенде часто: сервис кидает Runtime при плохом запросе → глобальный обработчик ловит → отдаёт клиенту HTTP 400.

---

## 8. Карта «кто что делает» в лабе

| Место | Действие |
|--------|----------|
| `Integer.parseInt("abc")` | сам кидает `NumberFormatException` |
| твой код при `n <= 0` | ты кидаешь `IllegalArgumentException` |
| `main` + `try/catch` | ловишь и печатаешь, программа не падает |

---

## 9. Частые ошибки новичков

1. Поймать `Exception` сразу всем подряд — пока лучше ловить конкретные типы.  
2. Пустой `catch { }` — ошибка проглочена, потом не найти баг.  
3. Путать «вернуть −1» и «кинуть исключение» — для лабы нужен именно `throw`.  
4. Забыть, что после непойманного исключения `main` оборвётся — для `"abc"` и `"-5"` нужен `catch`.

---

## 10. Задание лабы (чеклист)

Файл: `labs/oop/ExceptionLab.java`

- [ ] `static int parsePositive(String s)`
  - [ ] `Integer.parseInt(s)`
  - [ ] если число `<= 0` → `throw new IllegalArgumentException("must be positive")`
  - [ ] иначе `return` числа
- [ ] `main`: три отдельных `try`
  - [ ] `"42"` → напечатать число
  - [ ] `"-5"` → `catch (IllegalArgumentException e)` → напечатать `e.getMessage()`
  - [ ] `"abc"` → `catch (NumberFormatException e)` → сообщение, что парсинг не удался

`done` / `pick` (подсказки словами; готовое решение лабы не выдаём).
