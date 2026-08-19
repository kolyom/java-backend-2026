/**
 * День 3 — исключения.
 * docs/theory/08-exceptions.md
 *
 * parsePositive(String): parseInt + если <= 0 кинуть IllegalArgumentException
 * main: три try — "42", "-5", "abc"
 * done / pick
 */
public class ExceptionLab {
    // TODO: parsePositive + main
    public static int parsePositive(String s) {
        int n = Integer.parseInt(s);
        if (n <= 0) {
            throw new IllegalArgumentException("pisky pokagi");
        }
        return n;
    }

    public static void main(String[] args) {
        try {
            String s = "42";
            System.out.println(ExceptionLab.parsePositive(s));
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        try {
            String s1 = "-5";
            System.out.println(ExceptionLab.parsePositive(s1));

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        try {
            String s2 = "abc";
            System.out.println(ExceptionLab.parsePositive(s2));

        } catch (NumberFormatException e) {
            System.err.println("Чет не получилось распарсить");
        }
    }
}
