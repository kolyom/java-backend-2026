# Streams: из коллекции в int[]

Кратко под задачу вроде пересечения массивов (Set → массив).

---

## 1. Что такое stream одной фразой

**Поток элементов**, по которому можно пройтись операциями: отфильтровать, преобразовать, собрать результат.

Не заменяет понимание циклов — это другой синтаксис тех же идей.

---

## 2. Set\<Integer\> → int[]

```java
import java.util.Set;

Set<Integer> n3 = ...; // уже заполнил

int[] result = n3.stream()
        .mapToInt(Integer::intValue)  // Integer → примитив int
        .toArray();                   // собираем int[]
```

По шагам:

| Кусок | Смысл |
|--------|--------|
| `n3.stream()` | поток элементов Set (`Integer`) |
| `mapToInt(...)` | каждый `Integer` → обычный `int` (иначе будет `Integer[]` / Object stream) |
| `Integer::intValue` | «вызови у объекта метод intValue» (то же, что `x -> x.intValue()`) |
| `toArray()` | после `mapToInt` даёт именно **`int[]`** |

Короткая запись то же самое:

```java
int[] result = n3.stream().mapToInt(i -> i).toArray();
```

(`i -> i` для `Integer` срабатывает как unboxing в `mapToInt`.)

---

## 3. Зачем mapToInt, а не map

```java
// так получится Integer[], не int[] (и toArray будет другим)
n3.stream().map(i -> i).toArray();
```

Для примитивного `int[]` нужен **`IntStream`**: `mapToInt` → `toArray()`.

---

## 4. int[] → stream (наоборот, полезно знать)

```java
import java.util.Arrays;

int sum = Arrays.stream(nums).sum();
int[] copy = Arrays.stream(nums).filter(x -> x > 0).toArray();
```

`Arrays.stream(int[])` сразу даёт `IntStream`.

---

## 5. Осторожно

- Порядок элементов из `HashSet` **не гарантирован** — для пересечения это ок (порядок любой).
- Пустой set → `toArray()` вернёт `int[]` длины 0.

---

В своей задаче: сначала собери пересечение в `Set`, в конце одна строка stream → `return` массива.
