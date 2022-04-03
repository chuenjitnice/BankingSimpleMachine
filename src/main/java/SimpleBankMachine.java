import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SimpleBankMachine {
    public static void main(String[] args) {
        MachineAction machine = new MachineAction();
        machine.Process();
    }
}
class MachineAction{
    private Account account;

    public MachineAction() {}
    public void Process(){
        Scanner readInput = new Scanner(System.in);
        String option;
        System.out.print("Input your name: ");
        String name = readInput.next();
        CreateAccount(name);
        ShowWelcome();
        do{
            ShowOptions();
            System.out.print("==========\nEnter option [0-4]: ");
            option = readInput.next();
            double amount = 0;
            switch (option){
                case "1":
                    // Deposit
                    System.out.print("----------\nEnter amount: ");
                    amount = readInput.nextDouble();
                    Deposit(amount);
                    ShowBalance();
                    PressEnterToContinue();
                    break;
                case "2":
                    // Withdraw
                    System.out.print("----------\nEnter amount: ");
                    amount = readInput.nextDouble();
                    Withdraw(amount);
                    ShowBalance();
                    PressEnterToContinue();
                    break;
                case "3":
                    // Check balance
                    ShowBalance();
                    PressEnterToContinue();
                    break;
                case "4":
                    // Transaction history
                    ShowTransactionHistory();
                    PressEnterToContinue();
                    break;
                case "0":
                    ShowExit();
                    break;
                default:
                    System.out.println("----------\nWrong option. Please try again.\n----------");
                    break;
            }
        }while(!option.equals("0"));
    }
    protected void CreateAccount(String name){
        account = new Account(name);
    }
    protected void ShowOptions(){
        System.out.println("==========\nOptions\n==========");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. Transaction history");
        System.out.println("0. Exit");
    }
    protected void Deposit(double amount){
        account.transactionAction(Operation.Credit, amount);
    }
    protected void Withdraw(double amount){
        if(account.getBalance() < amount)
            System.out.println("----------\nSorry, your balance not enough.");
        else
            account.transactionAction(Operation.Debit, amount);
    }
    protected void ShowTransactionHistory(){
        Transaction[] transactionHistory = account.getTransactionHistories();
        if (transactionHistory == null)
            System.out.println("----------\nNo transaction occur\n----------");
        else {
            System.out.println("----------\nTransaction History :");
            System.out.println(" " + Arrays.toString(transactionHistory).replace(',', '\n').replaceAll("[\\[\\]]", ""));
            System.out.println("----------");
        }
    }
    protected void ShowWelcome(){
        System.out.println("----------\nWelcome "+account.getName()+"\n----------");
    }
    protected void ShowExit(){
        System.out.println("----------\nGood bye "+account.getName()+"\n----------");
    }
    protected void ShowBalance(){
        System.out.println("----------\nBalance: "+account.getBalance()+"\n----------");
    }
    protected void PressEnterToContinue(){
        System.out.println("Press enter key to back to option menu...");
        try {
            System.in.read();
        }
        catch (Exception e){}
    }
}
class Account{
    private String name;
    private double balance;
    private Transaction[] transactionHistories;

    public Account(String name) {
        this.name = name;
        this.balance = 0;
    }

    public String getName() {
        return name;
    }
    public double getBalance(){
        return balance;
    }
    public Transaction[] getTransactionHistories(){
        return transactionHistories;
    }
    public void transactionAction(Operation operation, double amount){
        balance = (operation.equals(Operation.Credit))? balance + amount : balance - amount;
        transactionHistories = addTransactionHistory(new Transaction(operation, amount, balance));
    }
    private Transaction[] addTransactionHistory(Transaction newTransaction){
        if(transactionHistories == null)
            return new Transaction[]{newTransaction};
        else {
            List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(transactionHistories));
            transactions.add(newTransaction);
            return transactions.toArray(transactionHistories);
        }
    }
}
class Transaction{
    private Operation operation;
    private double amount;
    private double balance;

    public Transaction(Operation operation, double amount, double balance) {
        this.operation = operation;
        this.amount = amount;
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }
    public double getBalance() {
        return balance;
    }

    public String getOperationOnBook() {
        return (operation.equals(Operation.Credit)) ? "Deposited" : "Withdrawed";
    }

    @Override
    public String toString() {
        return getOperationOnBook() +" "+ amount +" Balance: "+ balance;
    }
}
enum Operation{
    Credit, Debit
}