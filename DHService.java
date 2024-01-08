import java.math.*;
import java.security.*;

public class DHService {
    // attributes of this class are: a, q, ya, yb, key
    public BigInteger a;
    public BigInteger q;
    public BigInteger ya;
    public BigInteger yb;
    public BigInteger xa;
    public BigInteger xb;
    public BigInteger key;
    public SecureRandom sr;
    public static int keyLength = 128;

    // constructor
    public DHService() {
        sr = new SecureRandom();
    }

    // generate q, a, xa and ya for the client
    public void generateForClient() {
        q = new BigInteger(keyLength, 10, sr);
        a = new BigInteger(keyLength - 1, sr);
        xa = new BigInteger(keyLength - 1, sr);
        ya = a.modPow(xa, q);
    }

    // generate yb and xb for the server and accept q, a, ya and assign them
    public void generateForServer(BigInteger newQ, BigInteger newA, BigInteger newYA) {
        a = newA;
        q = newQ;
        ya = newYA;
        xb = new BigInteger(q.bitLength() - 1, sr);
        yb = a.modPow(xb, q);
    }

    // assign yb for client
    public void acceptYB(BigInteger newYB) {
        yb = newYB;
    }

    // generate key for client
    public void generateKeyForClient() {
        key = yb.modPow(xa, q);
    }

    // generate key for server
    public void generateKeyForServer() {
        key = ya.modPow(xb, q);
    }


}
