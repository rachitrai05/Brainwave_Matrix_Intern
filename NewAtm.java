import java.util.Scanner;
class MyUserAccount{
    private String name;
    private String password;
    private long accountNo;
    private int pin;
    private double balance = 10000.00;

    // Constructor
    public MyUserAccount() {}

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public long getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void addBalance(double amount) {
        balance += amount;
    }

    public boolean deductBalance(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Registration method
    public void register(Scanner sc) {
        //reading input from same scanner 'sc' which define in main Method
        System.out.println("\n----- User Registration -----");
        System.out.print("Enter Name: ");
        this.name = sc.nextLine();
        System.out.print("Create Password: ");
        this.password = sc.nextLine();
        System.out.print("Set Account Number: ");
        this.accountNo = sc.nextLong();
        System.out.print("Set 4-digit ATM PIN: ");
        this.pin = sc.nextInt();
        sc.nextLine(); // Clear buffer
        System.out.println("✅ Registration successful!\n");
    }

    // Login method
    public boolean login(Scanner sc) {
        System.out.println("----- User Login -----");
        System.out.print("Enter Account Number: ");
        long acc = sc.nextLong();
        System.out.print("Enter ATM PIN: ");
        int enteredPin = sc.nextInt();
        sc.nextLine(); // Clear buffer
        if (acc == this.accountNo && enteredPin == this.pin) {
            System.out.println("✅ Login successful! Welcome, " + this.name);
            return true;
        } else {
            System.out.println("❌ Incorrect credentials. Try again.");
            return false;
        }
    }
}

class MyTransaction {
    private MyUserAccount account;
    private Scanner sc;

    public MyTransaction(MyUserAccount account, Scanner sc) {
        this.account = account;
        this.sc = sc;
    }

    public void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        if (amount > 0) {
            account.addBalance(amount);
            System.out.println("✅ Deposited ₹" + amount);
        } else {
            System.out.println("❌ Invalid amount.");
        }
    }

    public void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (account.deductBalance(amount)) {
            System.out.println("✅ Withdrawn ₹" + amount);
        } else {
            System.out.println("❌ Insufficient balance.");
        }
    }

    public void transfer() {
        System.out.print("Enter recipient account number: ");
        long recipientAcc = sc.nextLong();
        sc.nextLine(); // Consume the leftover newline
        System.out.print("Enter recipient name: ");
        String recipientName=sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();
        System.out.print("Enter your PIN for verification: ");
        int pin = sc.nextInt();

        if (pin == account.getPin()) {
            if (account.deductBalance(amount)) {
                System.out.println("✅ Transferred ₹" + amount + " to "+recipientName+ " on this account no " + recipientAcc);
            } else {
                System.out.println("❌ Insufficient balance.");
            }
        } else {
            System.out.println("❌ Incorrect PIN.");
        }
    }

    public void balanceEnquiry() {
        System.out.println("💰 Your current balance: ₹" + account.getBalance());
    }
}

public class NewAtm{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyUserAccount user = new MyUserAccount();
        // Step 1: Register
        user.register(sc);
        // Step 2: Login loop
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            isLoggedIn = user.login(sc);
        }

        // Step 3: Transaction Menu
        MyTransaction txn = new MyTransaction(user, sc);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n------ ATM MENU ------");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Balance Enquiry");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1: txn.deposit(); break;
                case 2: txn.withdraw(); break;
                case 3: txn.transfer(); break;
                case 4: txn.balanceEnquiry(); break;
                case 5:
                    System.out.println("👋 Thank you for using the ATM. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}


