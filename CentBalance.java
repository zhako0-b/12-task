public class CentBalance {
    private long cents;

    public CentBalance(long cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.cents = cents;
    }

    public void charge(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment must be positive");
        }
        if (amount > cents) {
            throw new IllegalStateException("Insufficient funds");
        }
        cents -= amount;
        System.out.println("Paid: " + amount + " cents; remaining: " + cents);
    }

    public long remaining() { return cents; }
}
