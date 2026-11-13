package com.loanledger.loan_service.loan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoanTest {

    @Test
    void newLoanBalanceEqualsAmountBorrowed() {
        Loan loan = new Loan(10_000);

        assertEquals(10_000, loan.getBalance());
    }

    @Test
    void paymentReducesBalanceByExactAmount() {
        Loan loan = new Loan(10_000);

        loan.acceptPayment(3_000);

        assertEquals(7_000, loan.getBalance());
    }

    @Test
    void severalPaymentsInARowAddUpCorrectly() {
        Loan loan = new Loan(10_000);

        loan.acceptPayment(3_000);
        loan.acceptPayment(2_000);
        loan.acceptPayment(1_500);

        assertEquals(3_500, loan.getBalance());
    }

    @Test
    void payingExactRemainingBalanceMakesLoanPaidOff() {
        Loan loan = new Loan(10_000);

        loan.acceptPayment(10_000);

        assertEquals(0, loan.getBalance());
        assertTrue(loan.isPaid());
    }

    @Test
    void openingLoanForZeroIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Loan(0));
    }

    @Test
    void paymentOfZeroIsRejected() {
        Loan loan = new Loan(10_000);

        assertThrows(IllegalArgumentException.class, () -> loan.acceptPayment(0));
    }

    @Test
    void negativePaymentIsRejected() {
        Loan loan = new Loan(10_000);

        assertThrows(IllegalArgumentException.class, () -> loan.acceptPayment(-500));
    }

    @Test
    void paymentLargerThanBalanceIsRejected() {
        Loan loan = new Loan(10_000);

        assertThrows(IllegalArgumentException.class, () -> loan.acceptPayment(10_001));
        assertEquals(10_000, loan.getBalance());
    }

    @Test
    void payingAlreadyPaidOffLoanIsRejected() {
        Loan loan = new Loan(10_000);
        loan.acceptPayment(10_000);

        assertThrows(IllegalStateException.class, () -> loan.acceptPayment(1));
        assertEquals(0, loan.getBalance());
    }
}
