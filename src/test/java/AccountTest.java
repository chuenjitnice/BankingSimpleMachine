import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @org.junit.jupiter.api.Test
    void InitAccountTest() {
        String name = "Nice";
        Account account = new Account(name);
        assertEquals(name, account.getName());
        assertEquals(0.0, account.getBalance());
        assertNull(account.getTransactionHistories());
    }

    @org.junit.jupiter.api.Test
    void AccountDepositTest() {
        String name = "AB";
        Account account = new Account(name);
        account.transactionAction(Operation.Credit, 100);
        assertEquals(100, account.getBalance());
        assertEquals(1, account.getTransactionHistories().length);

        Transaction transaction = account.getTransactionHistories()[0];
        assertEquals(100, transaction.getAmount());
        assertEquals(100, transaction.getBalance());
    }
    @org.junit.jupiter.api.Test
    void AccountWithdrawTest() {
        String name = "AB";
        Account account = new Account(name);
        account.transactionAction(Operation.Credit, 100);
        account.transactionAction(Operation.Debit, 100);
        assertEquals(0, account.getBalance());
        assertEquals(2, account.getTransactionHistories().length);

        Transaction transaction = account.getTransactionHistories()[1];
        assertEquals(100, transaction.getAmount());
        assertEquals(0, transaction.getBalance());
    }

}