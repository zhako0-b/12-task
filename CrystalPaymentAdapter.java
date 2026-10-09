import java.util.Objects;

public class CrystalPaymentAdapter implements PaymentProcessor {
    private final CentBalance balance;

    public CrystalPaymentAdapter(CrystalWallet wallet) {
        Objects.requireNonNull(wallet, "wallet");
        balance = new CentBalance(Math.multiplyExact(wallet.countCrystals(), 250L));
    }

    @Override
    public void charge(long amountInCents) { balance.charge(amountInCents); }

    public long remainingCents() { return balance.remaining(); }
}
