public class CrystalWallet {
    private final long crystals;

    public CrystalWallet(long crystals) {
        if (crystals < 0) {
            throw new IllegalArgumentException("Crystal count cannot be negative");
        }
        this.crystals = crystals;
    }

    public long countCrystals() { return crystals; }
}
