import java.util.ArrayList;
import java.util.List;

/**
 * Тема 5 — List / ArrayList.
 * docs/theory/07-arraylist.md
 *
 * List<Long>, 3 числа, сумма, печатать в main.
 * done / pick.
 */
public class ListPractice {
    public static void main(String[] args) {
        List<Long> array = new ArrayList<>();
        array.add(1212322222L);
        array.add(322222L);
        array.add(1212L);
        Long sum = 0L;
        for (Long i : array) {
            sum += i;
        }
        System.out.println(sum);
    }

}
