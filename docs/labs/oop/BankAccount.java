
/**
 * Тема 1 — класс и объект.
 *
 * Задание — см. docs/theory/01-class-and-object.md
 * Сделай класс по требованиям, запусти main в VS Code (Run Java).
 */
public class BankAccount {

    private Long id;
    private Long balanceKopecks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalanceKopecks() {
        return balanceKopecks;
    }

    public void deposit(Long amount) {
        if (amount >= 0) {
            this.balanceKopecks += amount;
        }

    }

    public boolean withdraw(Long amount) {
        if (amount >= 0) {
            if (this.balanceKopecks - amount >= 0) {
                this.balanceKopecks -= amount;
                return true;
            }
        }

        return false;
    }

    public void print() {
        System.out.println(this.id);
        System.out.println(this.balanceKopecks);
    }

    public BankAccount(Long id, Long balanceKopecks) {
        this.id = id;
        this.balanceKopecks = balanceKopecks;
    }

    public static void main(String[] args) {
        BankAccount a = new BankAccount(213124342, 0);
        BankAccount b = new BankAccount(99999342, 0);
        a.deposit(10000);
        a.withdraw(3000);
        b.deposit(5000);
        b.withdraw(100000);
        a.print();
        b.print();
    }
}
