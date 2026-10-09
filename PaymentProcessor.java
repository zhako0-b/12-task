public interface PaymentProcessor {
    void charge(long amountInCents);
}
