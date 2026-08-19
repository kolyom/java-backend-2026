# Тема 5 — List и ArrayList (база)

## Идея

`List` — интерфейс («упорядоченная коллекция, есть индекс»).  
`ArrayList` — реализация на массиве внутри (часто используешь её).

```java
import java.util.ArrayList;
import java.util.List;

List<Integer> nums = new ArrayList<>();
nums.add(10);
nums.add(20);
int x = nums.get(0);      // 10
int n = nums.size();
nums.remove(0);           // удалить по индексу
```

`List<Integer>` а не `List<int>` — в коллекциях только объекты; для чисел — обёртки `Integer`, `Long`.

## Упражнение

Файл: `labs/oop/ListPractice.java`

1. Создай `List<Long>` (через `ArrayList`).
2. Добавь 3 числа.
3. Посчитай сумму циклом `for` (по индексу или for-each).
4. Напечатай сумму в `main`.

Без готовых решений. done / pick.
