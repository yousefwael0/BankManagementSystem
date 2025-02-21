package models.account;


public class CurrentAccount extends Account {
    private static final double MINIMUM_BALANCE = 3000.0;
    private static final double PENALTY_FEE = 100.0;

    public CurrentAccount(double balance, String clientId) throws IllegalArgumentException {
        super("CURRENT", balance, 0.0, clientId, null);
    }
    public CurrentAccount() {
        super();
    }

    public void checkMinimumBalance() throws IllegalArgumentException {
        if (getBalance() < MINIMUM_BALANCE) {
            System.out.println("Balance below minimum requirement. Applying penalty fee.");
            withdraw(PENALTY_FEE);
        }
    }

    public void applyFees() throws IllegalArgumentException {
        checkMinimumBalance();
    }

    @Override
    public void withdraw(double amount) throws IllegalArgumentException {
        super.withdraw(amount);

        if (getBalance() < MINIMUM_BALANCE) {
            System.out.println("Balance below minimum requirement. Applying penalty fee.");
            try {
                super.withdraw(PENALTY_FEE);
            } catch (IllegalArgumentException e) {
                System.out.println("Warning: Could not apply penalty fee due to insufficient balance");
            }
        }
    }
}