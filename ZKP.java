import java.math.*;
import java.security.*;
import java.util.StringTokenizer;

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

    public void saveAuthSet(String msg) {
        StringTokenizer st = new StringTokenizer(msg);
        String token = "";
        boolean read = false;
        int roundLoc = 0;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (token.equals("AUTHORIZE_SET")) {
                read = true;
                continue;
            } else if (token.equals("REQUIRE:")) {
                return;
            }

            if (read) {
                rounds[roundLoc] = new BigInteger(token, 32);
                roundLoc++;
            }
        }
    }

    public void genSubsetA() {
        if (rounds.length % 2 == 0) {
            subsetA = new int[(rounds.length/2)];
        } else {
            subsetA = new int[(rounds.length/2 + 1)];
        }
        int subsetLoc = 0;
        for (int i=0; i<rounds.length; i+=2) {
            subsetA[subsetLoc] = i;
            subsetLoc++;
        }
    }

    public void calcSubsetK() {
        subsetK = new BigInteger[subsetA.length];
        for (int i=0; i<subsetA.length; i++) {
            subsetK[i] = s.multiply(rounds[subsetA[i]]).mod(n);
        }
    }

    public boolean checkSubsetK() {
        System.out.println("starting subset k");
        System.out.println(subsetK[0]);
        System.out.println("done checking subset k");
        int k = 0;
        for (int i=0 ; i<rounds.length; i += 2) {
            BigInteger a1 = rounds[i].multiply(v).mod(n);
            if (!a1.equals(subsetK[k])) {
                System.out.println("subset k failed");
                return false;
            }
            k++;
        }
        System.out.println("subset k passed");
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
        System.out.println("starting subset j");
        int j = 0;
        for (int i=1 ; i<rounds.length; i += 2) {
            if (!rounds[i].equals(subsetJ[j])) {
                System.out.println("subset j failed");
                return false;
            }
            j++;
        }
        System.out.println("subset j passed");
        return true;
    }

    public boolean checkSubsets() {
        boolean check = checkSubsetK() && checkSubsetJ();
        return check;
    }

    public static void main(String[] args) {
        ZKP a =  new ZKP(8);
    }
}
