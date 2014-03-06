import java.math.*;
import java.security.*;

class ZKP {
    BigInteger s;
    BigInteger n;
    BigInteger v;
    int size;

    public ZKP () {
        size = 1024;
        SecureRandom sr = new SecureRandom();
        s = new BigInteger(size, sr);
        n = new BigInteger(size, sr);
        v = s.modPow(new BigInteger("2"), n);
    }

    public static void main(String[] args) {
        ZKP a =  new ZKP();
    }
}
