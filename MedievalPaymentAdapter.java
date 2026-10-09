import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class MedievalPaymentAdapter implements PaymentProcessor {
    private final CentBalance balance;

    public MedievalPaymentAdapter(MedievalCoinPurse purse,
            BigDecimal goldRate, BigDecimal silverRate) {
        Objects.requireNonNull(purse, "purse");
        Objects.requireNonNull(goldRate, "goldRate");
        Objects.requireNonNull(silverRate, "silverRate");
        if (goldRate.signum() <= 0 || silverRate.signum() <= 0) {
            throw new IllegalArgumentException("Rates must be positive");
        }
        BigDecimal total = goldRate.multiply(
            BigDecimal.valueOf(purse.countGoldPieces()))
            .add(silverRate.multiply(
                BigDecimal.valueOf(purse.countSilverPieces())));
        long cents = total.setScale(0, RoundingMode.HALF_UP).longValueExact();
        balance = new CentBalance(cents);
    }

    @Override
    public void charge(long amountInCents) { balance.charge(amountInCents); }

    public long remainingCents() { return balance.remaining(); }
}
