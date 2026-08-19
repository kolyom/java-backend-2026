
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Day 01 lab: equals / hashCode / HashMap.
 *
 * В VS Code: открой файл → Run Java (▶) над main, или F5.
 *
 * Задание: 1. Запусти BrokenAccountId как есть — посмотри вывод. 2. Почини
 * FixedAccountId (equals + hashCode руками). 3. Раскомментируй
 * fixedHashMapDemo() и запусти снова. 4. Объясни в day-log, почему Set "терял"
 * объект.
 */
public class EqualsHashCodeLab {

    public static void main(String[] args) {
        // brokenHashSetDemo();
        System.out.println("---");
        // После починки раскомментируй:
        fixedHashMapDemo();
    }

    static void brokenHashSetDemo() {
        Set<BrokenAccountId> ids = new HashSet<>();
        BrokenAccountId a = new BrokenAccountId(42L);
        BrokenAccountId b = new BrokenAccountId(42L);

        ids.add(a);
        System.out.println("contains a? " + ids.contains(a));
        System.out.println("contains b? " + ids.contains(b));
        System.out.println("a.equals(b)? " + a.equals(b));
        System.out.println("size=" + ids.size());
    }

    static void fixedHashMapDemo() {
        Map<FixedAccountId, String> owners = new HashMap<>();
        FixedAccountId key1 = new FixedAccountId(42L);
        FixedAccountId key2 = new FixedAccountId(42L);

        owners.put(key1, "Ivan");
        System.out.println("get by key2: " + owners.get(key2));
        System.out.println("key1.equals(key2)? " + key1.equals(key2));
        System.out.println("hash equal? " + (key1.hashCode() == key2.hashCode()));
    }

    /**
     * Намеренно сломанный класс: equals есть, hashCode — нет.
     */
    static class BrokenAccountId {

        private final Long value;

        BrokenAccountId(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof BrokenAccountId that)) {
                return false;
            }
            return Objects.equals(value, that.value);
        }

        // hashCode НЕ переопределён — это баг. Почини в FixedAccountId.
    }

    /**
     * Сюда пишешь правильную реализацию.
     */
    static class FixedAccountId {

        private final Long value;

        FixedAccountId(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FixedAccountId that)) {
                return false;
            }
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
        // TODO: equals
        // TODO: hashCode
    }
}
