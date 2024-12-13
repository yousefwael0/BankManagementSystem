package models.account;
import models.user.Client;

public class CurrentAccount extends Account {
    private static final double MINIMUM_BALANCE = 3000.0;
    private static final double PENALTY_FEE = 100.0;

    public CurrentAccount(String accountNumber, double balance, Client client)
            throws InvalidAmountException {
        super(accountNumber, "Current", balance, 0.0, client);
    }

    public void checkMinimumBalance() throws InvalidAmountException, AccountClosedException, InsufficientBalanceException {
        if (getBalance() < MINIMUM_BALANCE) {
            System.out.println("Balance below minimum requirement. Applying penalty fee.");
            withdraw(PENALTY_FEE);
        }
    }

    public void applyFees() throws InvalidAmountException, AccountClosedException, InsufficientBalanceException {
        checkMinimumBalance();
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException,
            AccountClosedException {
        super.withdraw(amount);

        if (getBalance() < MINIMUM_BALANCE) {
            System.out.println("Balance below minimum requirement. Applying penalty fee.");
            try {
                super.withdraw(PENALTY_FEE);
            } catch (InsufficientBalanceException e) {
                System.out.println("Warning: Could not apply penalty fee due to insufficient balance");
            }
        }
    }
}