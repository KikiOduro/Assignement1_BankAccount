public class TestBankAccount {

    // Shows Account details
    private static void displayAccountInfo(String label, BankAccount account) {
        System.out.printf("[%s] Account ID: %s | Type: %s | Min Balance: %.2f | Current Balance: %.2f%n",
                label, account.getAccountID(), account.getAccountType(),
                account.getMinBalance(), account.getBalance());
    }

    public static void main(String[] args) {
        System.out.println("Testing BankAccount Class");
        // Creating bank acccounts
        BankAccount savingsAccount = new BankAccount(AccountType.SAVINGS, "SavingsAcc101-11", 200.0);
        BankAccount currentAccount = new BankAccount(AccountType.CURRENT, "CurrentAcc102.-12", 150.0);
        BankAccount lowBalanceAccount = new BankAccount(AccountType.CURRENT, "CurrentAcc102-00", 80.0);

        System.out.println("Savings Account: " + savingsAccount);
        System.out.println("Cuurent Account: " + currentAccount);
        System.out.println("Current Low Balance Account: " + lowBalanceAccount);
        System.out.println("\n-------------------------------------------------------------\n");

        // Getter Methods
        System.out.println("Savings Account ID: " + savingsAccount.getAccountID());
        System.out.println("Savings Account Type: " + savingsAccount.getAccountType());
        System.out.println("Savings Minimum Balance: " + savingsAccount.getMinBalance());
        System.out.println("Savings Current Balance: " + savingsAccount.getBalance());
        System.out.println("\n-------------------------------------------------------------\n");

        // Withdrawal Methods
        System.out.println("Withdraw 100 from Savings : " + savingsAccount.withdraw(100.0));
        displayAccountInfo("Savings after withdrawal", savingsAccount);

        System.out.println("Withdraw 60 from Current: " + currentAccount.withdraw(60.0));
        displayAccountInfo("Current after withdrawal attempt", currentAccount);

        System.out.println("Withdraw 10 from Low Balance : " + lowBalanceAccount.withdraw(10.0));
        displayAccountInfo("Low balance account after withdrawal attempt", lowBalanceAccount);
        System.out.println("\n-------------------------------------------------------------\n");

        // Testing withdrawal limits
        BankAccount limitedSavingsAccount = new BankAccount(AccountType.SAVINGS, "SavingsAcc102-12", 120.0);
        displayAccountInfo("Limited Savings Start", limitedSavingsAccount);
        System.out.println("1st withdrawal (20): " + limitedSavingsAccount.withdraw(20.0));
        System.out.println("2nd withdrawal (20): " + limitedSavingsAccount.withdraw(20.0));
        System.out.println("3rd withdrawal (20) - would fail (limit 2): " + limitedSavingsAccount.withdraw(20.0));
        displayAccountInfo("Limited Savings after attempts", limitedSavingsAccount);
        System.out.println();

        // Deposit Methods
        System.out.println("Deposit 50 into Current Account");
        currentAccount.deposit(50.0);
        displayAccountInfo("Current after deposit", currentAccount);
        System.out.println("\n-------------------------------------------------------------\n");

        // Monthly Maintenance Test
        System.out.println(" Monthly Maintenance Tests");
        System.out.println("Savings Account: (earns interest, no fee)");
        savingsAccount.performMonthlyMaintenance();
        displayAccountInfo("Savings after maintenance", savingsAccount);

        System.out.println("Current Account: (no interest, has fee)");
        currentAccount.performMonthlyMaintenance();
        displayAccountInfo("Current Account after maintenance", currentAccount);

        System.out.println("Limited Savings: (resets withdrawals after maintenance)");
        limitedSavingsAccount.performMonthlyMaintenance();
        System.out.println("Withdraw after maintenance reset (20): " + limitedSavingsAccount.withdraw(20.0));
        displayAccountInfo("Limited Savings after maintenance + new withdrawal", limitedSavingsAccount);
        System.out.println("\n-------------------------------------------------------------\n");

        // Transfer Methods Test
        System.out.println("Transfer Tests");
        displayAccountInfo("Before Transfer - Savings", savingsAccount);
        displayAccountInfo("Before Transfer - Current", currentAccount);

        System.out.println("Transfer 30 from Savings to Current (transferTo = true): " +
                savingsAccount.transfer(true, currentAccount, 30.0));
        displayAccountInfo("Savings after transfer", savingsAccount);
        displayAccountInfo("Current after transfer", currentAccount);

        System.out.println("Transfer 200 from Current to Savings (would fail, insufficient balance): " +
                currentAccount.transfer(true, savingsAccount, 200.0));
        displayAccountInfo("Current after failed transfer", currentAccount);
        displayAccountInfo("Savings after failed transfer", savingsAccount);

        System.out.println("Transfer 25 from Savings to Current using transferTo = false (withdraw from other): " +
                currentAccount.transfer(false, savingsAccount, 25.0));
        displayAccountInfo("Current after transfer (false)", currentAccount);
        displayAccountInfo("Savings after transfer (false)", savingsAccount);
        System.out.println("\n-------------------------------------------------------------\n");
    }

}
