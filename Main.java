import java.math.BigDecimal;

public class Main {
    public static void makePayment(PaymentProcessor processor, long cents) {
        processor.charge(cents);
    }

    public static void main(String[] args) {
        System.out.println("Medieval coins: 2 gold, 3 silver");
        PaymentProcessor medieval = new MedievalPaymentAdapter(
            new MedievalCoinPurse(2, 3),
            new BigDecimal("1000.505"), new BigDecimal("100.255"));
        makePayment(medieval, 500);
        makePayment(medieval, 500);
        try {
            makePayment(medieval, 2000);
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        try {
            makePayment(medieval, -100);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        System.out.println("Crystal wallet: 4 crystals, 250 cents each");
        PaymentProcessor crystals = new CrystalPaymentAdapter(new CrystalWallet(4));
        makePayment(crystals, 500);
        makePayment(crystals, 500);
    }
}
