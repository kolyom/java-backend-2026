# Day 01 — 31 июля 2026

## Цель дня

Закрыть окружение, понять правила работы, решить первые 5 задач, разобрать одну тему Java Core и завести дневник.

## Чеклист

- [x] Прочитал README и learning-plan
- [x] Завёл аккаунт на LeetCode (или подтвердил существующий)
- [x] Решил 5 задач Easy (список ниже)
- [ ] Разобрал тему: equals / hashCode / HashMap
- [ ] Написал короткое резюме дня в этот файл (секция «Отчёт»)

## Алгоритмы на сегодня (Easy)

1. Two Sum — [https://leetcode.com/problems/two-sum/](https://leetcode.com/problems/two-sum/)
2. Valid Anagram — [https://leetcode.com/problems/valid-anagram/](https://leetcode.com/problems/valid-anagram/)
3. Contains Duplicate — [https://leetcode.com/problems/contains-duplicate/](https://leetcode.com/problems/contains-duplicate/)
4. Best Time to Buy and Sell Stock — [https://leetcode.com/problems/best-time-to-buy-and-sell-stock/](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
5. Valid Parentheses — [https://leetcode.com/problems/valid-parentheses/](https://leetcode.com/problems/valid-parentheses/)

Правила решения:

- Сначала сам, минимум 20 минут на задачу, прежде чем смотреть подсказки.
- После решения — запиши в `algorithms/day-01.md`: подход, сложность по времени и памяти, где застрял.
- Язык: Java. Без копирования чужих решений.



## Теория на сегодня

Тема: **контракт equals/hashCode и как из-за него ломается HashMap**.

Сделай руками маленький класс `Money` или `AccountId` с полями. Нарушь контракт (equals есть, hashCode нет — или наоборот). Положи объекты в `HashSet` / используй как ключ в `HashMap`. Посмотри, что происходит. Объясни своими словами почему.

Референс (прочитать после практики, не вместо):

- Effective Java, Item 10–11 (equals/hashCode) — если есть книга
- Или статья Baeldung: [https://www.baeldung.com/java-equals-hashcode-contracts](https://www.baeldung.com/java-equals-hashcode-contracts)



## Отчёт (заполни в конце дня)

**Сколько времени ушло:**  
**Задачи решены:**  
**Где застрял:**  
**Что понял про equals/hashCode:**  
**Вопрос ментору:**  