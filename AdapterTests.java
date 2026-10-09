import java.math.BigDecimal;

public class AdapterTests {
    private static int checks;

    private static MedievalPaymentAdapter adapter(long gold, long silver,
            String goldRate, String silverRate) {
        return new MedievalPaymentAdapter(new MedievalCoinPurse(gold, silver),
            new BigDecimal(goldRate), new BigDecimal(silverRate));
    }

    private static void equal(long expected, long actual) {
        if (expected != actual) throw new AssertionError(expected + " != " + actual);
        checks++;
    }

    private static void fails(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable error) {
            if (!type.isInstance(error)) throw new AssertionError(error);
            checks++;
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    public static void main(String[] args) {
        MedievalPaymentAdapter a = adapter(2, 3, "1000.505", "100.255");
        equal(2302, a.remainingCents());
        Main.makePayment(a, 500);
        Main.makePayment(a, 500);
        equal(1302, a.remainingCents());
        fails(IllegalStateException.class, () -> a.charge(1303));
        equal(1302, a.remainingCents());
        fails(IllegalArgumentException.class, () -> a.charge(0));
        fails(IllegalArgumentException.class, () -> a.charge(-1));
        equal(1302, a.remainingCents());
        a.charge(1302);
        equal(0, a.remainingCents());
        fails(IllegalStateException.class, () -> a.charge(1));
        equal(0, adapter(0, 0, "1", "1").remainingCents());
        equal(10, adapter(1, 0, "10.49", "1").remainingCents());
        equal(11, adapter(1, 0, "10.50", "1").remainingCents());
        equal(1, adapter(1, 1, "0.49", "0.49").remainingCents());
        fails(IllegalArgumentException.class, () -> new MedievalCoinPurse(-1, 0));
        fails(IllegalArgumentException.class, () -> adapter(1, 0, "0", "1"));
        fails(IllegalArgumentException.class, () -> adapter(1, 0, "1", "-1"));
        fails(NullPointerException.class, () -> new MedievalPaymentAdapter(
            null, BigDecimal.ONE, BigDecimal.ONE));
        fails(ArithmeticException.class, () -> adapter(Long.MAX_VALUE, 0, "2", "1"));
        CrystalPaymentAdapter c = new CrystalPaymentAdapter(new CrystalWallet(4));
        Main.makePayment(c, 500);
        Main.makePayment(c, 500);
        equal(0, c.remainingCents());
        fails(IllegalStateException.class, () -> c.charge(1));
        fails(IllegalArgumentException.class, () -> new CrystalWallet(-1));
        fails(ArithmeticException.class, () -> new CrystalPaymentAdapter(
            new CrystalWallet(Long.MAX_VALUE)));
        System.out.println("PASS: " + checks + " checks");
    }
}
