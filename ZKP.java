import java.math.*;
import java.security.*;

class ZKP {
    BigInteger s;
    BigInteger n;
    BigInteger v;
    int size;
    BigInteger[] rounds;
    SecureRandom sr;

    public ZKP (int numRounds) {
        size = 1024;
        rounds = new BigInteger[numRounds];
        sr = new SecureRandom();
        s = new BigInteger(size, sr);
        n = new BigInteger(size, sr);
        v = s.modPow(new BigInteger("2"), n);
    }

    public void doRounds() {
        for (int i=0; i < rounds.length; i++) {
            rounds[i] = new BigInteger(size, sr).modPow(new BigInteger("2"), n);
        }
    }

    public static void main(String[] args) {
        ZKP a =  new ZKP(7);
        a.doRounds();
    }
}
