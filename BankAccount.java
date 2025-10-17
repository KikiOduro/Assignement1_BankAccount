
public class BankAccount {
    // Constants
    private static final double CURRENT_ACCT_MIN_BALANCE = 1000.0;
    private static final double SAVINGS_ACCT_MIN_BALANCE = 500.0;
    private static final double SAVINGS_ACCT_INTEREST_RATE = 0.05;
    private static final double CURRENT_ACCT_MAINTENANCE_FEE = 20.0;
    private static final int SAVINGS_WITHDRAWAL_LIMIT = 2;

    // Member variables
    private AccountType acctType;
    private String acctID;
    private double balance;
    private int numWithdrawals;
    private boolean inTheRed;
    private double minBalance;
    private double interestRate;
    private double maintenanceFee;
    private int withdrawalLimit;

    // Constructor 1:Input parameters AccountType type, String id
    public BankAccount(AccountType type, String id) {
        this.acctType = type;
        this.acctID = id;
        this.balance = 0.0;
        this.numWithdrawals = 0;

        if (type == AccountType.CURRENT) {
            this.minBalance = CURRENT_ACCT_MIN_BALANCE;
            this.interestRate = 0.0;
            this.maintenanceFee = CURRENT_ACCT_MAINTENANCE_FEE;
            this.withdrawalLimit = -1; // No limit
        } else { // SAVINGS
            this.minBalance = SAVINGS_ACCT_MIN_BALANCE;
            this.interestRate = SAVINGS_ACCT_INTEREST_RATE;
            this.maintenanceFee = 0.0;
            this.withdrawalLimit = SAVINGS_WITHDRAWAL_LIMIT;
        }

        // Checks if account is in the red
        this.inTheRed = (this.balance < this.minBalance);
    }

    // Constructor 2:Input parameters AccountType type, String id, double
    // openingBalance
    public BankAccount(AccountType type, String id, double openingBalance) {
        this.acctType = type;
        this.acctID = id;
        this.balance = openingBalance;
        this.numWithdrawals = 0;

        if (acctType == AccountType.CURRENT) {
            this.minBalance = CURRENT_ACCT_MIN_BALANCE;
            this.interestRate = 0.0;
            this.maintenanceFee = CURRENT_ACCT_MAINTENANCE_FEE;
            this.withdrawalLimit = -1;
        } else {
            this.minBalance = SAVINGS_ACCT_MIN_BALANCE;
            this.interestRate = SAVINGS_ACCT_INTEREST_RATE;
            this.maintenanceFee = 0.0;
            this.withdrawalLimit = SAVINGS_WITHDRAWAL_LIMIT;
        }

        // Checks if account is in the red
        this.inTheRed = (this.balance < this.minBalance);
    }

    // Getter methods:
    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return acctType;
    }

    public String getAccountID() {
        return acctID;
    }

    public double getMinBalance() {
        return minBalance;
    }

    // Withdraw method
    public boolean withdraw(double amount) {
        // Checks if withdrawal limit exceeded if there is a limit
        if (withdrawalLimit != -1 && numWithdrawals >= withdrawalLimit) {
            System.out.println("Sorry, could not perform withdrawal: Withdrawal limit exceeded.");
            return false;
        }

        // Checks if account is in the red
        if (inTheRed) {
            System.out.println("Sorry, could not perform withdrawal: Account is in the red.");
            return false;
        }

        // Check if sufficient balance remains
        if (balance - amount < minBalance) {
            System.out.println("Sorry, could not perform withdrawal: Insufficient balance.");
            return false;
        }

        // Perform withdrawal
        balance -= amount;
        numWithdrawals++;
        return true;
    }

    // Deposit method adding a specific amount to the balance
    public void deposit(double amount) {
        balance += amount;
    }

    // PerformMonthlyMaintenance Method
    public void performMonthlyMaintenance() {
        double earnedInterest = (balance * (interestRate / 12));
        // Adds the interest and maintenance fees to the balance
        balance += earnedInterest;
        balance -= maintenanceFee;

        inTheRed = balance < minBalance;
        // Resets the number of windrawals
        numWithdrawals = 0;
        // Summary
        System.out.println("Earned Interest: " + earnedInterest);
        System.out.println("Maintenance Fee: " + maintenanceFee);
        System.out.println("Updated Balance: " + balance);

        // Warning for accounts inTheRed
        if (inTheRed) {
            System.out.println("WARNING: This account is in the red");
        }
    }

    // Transfer method
    public boolean transfer(boolean transferTo, BankAccount otherAccount, double amount) {
        if (transferTo) {
            // Transfer from this account to other account
            if (this.withdraw(amount)) {
                otherAccount.deposit(amount);
                return true;
            } else {
                return false;
            }
        } else {
            // Transfer from other account TO this account
            if (otherAccount.withdraw(amount)) {
                this.deposit(amount);
                return true;
            } else {
                return false;
            }
        }
    }

}