public class MedievalCoinPurse {
    private final long goldPieces;
    private final long silverPieces;

    public MedievalCoinPurse(long goldPieces, long silverPieces) {
        if (goldPieces < 0 || silverPieces < 0) {
            throw new IllegalArgumentException("Coin counts cannot be negative");
        }
        this.goldPieces = goldPieces;
        this.silverPieces = silverPieces;
    }

    public long countGoldPieces() { return goldPieces; }
    public long countSilverPieces() { return silverPieces; }
}
