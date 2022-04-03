import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SimpleBankMachine {
    public static void main(String[] args) {
        Scanner readInput = new Scanner(System.in);
        MachineAction machine = new MachineAction();
        char option;
        System.out.print("Input your name: ");
        String name = readInput.next();
        machine.CreateAccount(name);
        machine.ShowWelcome();
        do{
            machine.ShowOptions();
            System.out.print("==========\nEnter option: ");
            option = readInput.next().charAt(0);
            double amount = 0;
            switch (option){
                case '1':
                    // Deposit
                    System.out.print("----------\nEnter amount: ");
                    amount = readInput.nextDouble();
                    machine.Deposit(amount);
                    machine.ShowBalance();
                    break;
                case '2':
                    // Withdraw
                    System.out.print("----------\nEnter amount: ");
                    amount = readInput.nextDouble();
                    machine.Withdraw(amount);
                    machine.ShowBalance();
                    break;
                case '3':
                    // Check balance
                    machine.ShowBalance();
                    break;
                case '4':
                    // Transaction history
                    machine.ShowTransactionHistory();
                    break;
                case '5':
                    // Previous transaction
                    machine.ShowPreviousTransaction();
                    break;
                case '0':
                    machine.ShowExit();
                    break;
                default:
                    System.out.println("Wrong option. Please try again.");
                    break;
            }
        }while(option != '0');
    }
}
class MachineAction{
    private Account account;

    public MachineAction() {}
    public void CreateAccount(String name){
        account = new Account(name);
    }
    public void ShowOptions(){
        System.out.println("==========\nOptions\n==========");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. Transaction history");
        System.out.println("5. Previous transaction");
        System.out.println("0. Exit");
    }
    public void Deposit(double amount){
        account.transactionAction(Operation.Credit, amount);
    }
    public void Withdraw(double amount){
        account.transactionAction(Operation.Debit, amount);
    }
    public void ShowPreviousTransaction(){
        Transaction previousTransaction = account.getPreviousTransaction();
        if (previousTransaction == null)
            System.out.println("----------\nNo transaction occur\n----------");
        else
            System.out.println("----------\nPrevious transaction: " +previousTransaction.getOperationOnBook()+ " " +previousTransaction.getAmount()+ " Balance: " +previousTransaction.getBalance()+"\n----------");
    }
    public void ShowTransactionHistory(){
        Transaction[] transactionHistory = account.getTransactionHistories();
        if (transactionHistory == null)
            System.out.println("----------\nNo transaction occur\n----------");
        else
            System.out.println(Arrays.toString(transactionHistory).replace(',','\n'));
    }
    public void ShowWelcome(){
        System.out.println("----------\nWelcome "+account.getName()+"\n----------");
    }
    public void ShowExit(){
        System.out.println("----------\nGood bye "+account.getName()+"\n----------");
    }
    public void ShowBalance(){
        System.out.println("----------\nBalance: "+account.getBalance()+"\n----------");
    }
}
class Account{
    private String name;
    private double balance;
    private Transaction previousTransaction;
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
    public Transaction getPreviousTransaction() {
        return previousTransaction;
    }
    public Transaction[] getTransactionHistories(){
        return transactionHistories;
    }
    public void transactionAction(Operation operation, double amount){
        balance = (operation.equals(Operation.Credit))? balance + amount : balance - amount;
        previousTransaction = new Transaction(operation, amount, balance);
        transactionHistories = addTransactionHistory(previousTransaction);
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

    public Operation getOperation() {
        return operation;
    }
    public String getOperationOnBook() {
        return (operation.equals(Operation.Credit)) ? "Deposited" : "Withdrawed";
    }
    public double getAmount() {
        return amount;
    }
    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return " " + getOperationOnBook() +" "+ amount +" Balance: "+ balance;
    }
}
enum Operation{
    Credit, Debit
}