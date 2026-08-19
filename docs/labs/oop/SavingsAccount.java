/**
 * Тема 3 — наследование.
 * Задание: docs/theory/05-inheritance.md
 *
 * SavingsAccount extends BankAccount
 * + annualRate, applyInterest(), main с проверкой
 *
 * done / pick в чат.
 */
public class SavingsAccount extends BankAccount {
    private double annualRate;

    public SavingsAccount(Long id, Long balanceKopecks, double annualRate) {
        super(id, balanceKopecks);
        this.annualRate = annualRate;
    }

    public void applyInterest() {
        this.deposit((Long) (this.getBalanceKopecks() * this.annualRate));
    }

    @Override
    public void print() {
        super.print();
        System.out.println(this.annualRate);
    }

    // TODO: extends, поле, конструктор с super, applyInterest, main
    public static void main(String[] args) {
        BankAccount a = new SavingsAccount(123, 20, 0.09);
        a.print();
    }
}
