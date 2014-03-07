import java.math.*;
import java.security.*;

class ZKP {
    BigInteger s;
    BigInteger n;
    BigInteger v;
    int size;
    SecureRandom sr;
    BigInteger[] rounds;
    int[] subsetA;
    BigInteger[] subsetK;
    BigInteger[] subsetJ;

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

    public void genSubsetA() {
        subsetA = new int[(rounds.length/2) + 1];
        int subsetLoc = 0;
        for (int i=0; i<rounds.length; i+=2) {
            subsetA[subsetLoc] = i;
        }
    }

    public void calcSubsetK() {
        subsetK = new BigInteger[subsetA.length];
        for (int i=0; i<subsetA.length; i++) {
            subsetK[i] = s.multiply(rounds[subsetA[i]]).mod(n);
        }
    }

    public boolean checkSubsetK() {
        int k = 0;
        for (int i=0 ; i<rounds.length; i += 2) {
            BigInteger a1 = rounds[i].multiply(v).mod(n);
            if (!a1.equals(subsetK[k])) {
                return false;
            }
            k++;
        }
        return true;
    }

    public void calcSubsetJ() {
        subsetJ = new BigInteger[rounds.length/2];
        int j = 0;
        for (int i=1; i<rounds.length; i+=2) {
            subsetJ[j] = rounds[i].mod(n);
            j++;
        }
    }

    public boolean checkSubsetJ() {
        int j = 0;
        for (int i=1 ; i<rounds.length; i += 2) {
            if (!rounds[i].equals(subsetJ[j])) {
                return false;
            }
            j++;
        }
        return true;
    }

    public boolean checkSubsets() {
        return checkSubsetK() && checkSubsetJ();
    }

    public static void main(String[] args) {
        ZKP a =  new ZKP(7);
    }
}
