import java.io.*;
import java.math.*;
import java.security.*;

class DiffieHellmanExchange {
    int keysize;
    DHKey key;
    BigInteger x, x_pub, s_secret;

    public DiffieHellmanExchange () {  this.keysize = 512;  }

    // Get the numbers p,g from file (in "key" object)
    // Generate a secure random number and create a public key from p,g
    public BigInteger getDHParmMakePublicKey (String filename) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream oin = new ObjectInputStream(fis);
        key = (DHKey)oin.readObject();
        oin.close();
        SecureRandom sr = new SecureRandom();  // Get a secure random number
        x = new BigInteger(keysize, sr);  // Generate the secure secret key
        x_pub = key.g.modPow(x, key.p);   // Compute the public key from p,g
        return x_pub;
    }

    public BigInteger getSecret (String mKey) {
        BigInteger bi = new BigInteger(mKey, 32);
        s_secret = bi.modPow(x, key.p);
        return s_secret;
    }
}
