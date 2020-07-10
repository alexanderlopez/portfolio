package com.google.sps.data;

import java.math.BigInteger;

public class RSA {
    public static final int KEY_SIZE = 258;

    private int keySize;
    private static final BigInteger e = new BigInteger("65537");
    private BigInteger d;
    private BigInteger totientN;
    private BigInteger n;

    public RSA() {
        keySize = KEY_SIZE;
    }

    public RSA(int keySize) {
        this.keySize = keySize;
    }

    public RSA(BigInteger n, BigInteger d, BigInteger e) {
        this.n = n;
        this.d = d;
        this.e = e;
        keySize = n.bitCount();
    }

    public void generateKey() {
        BigInteger p;
        BigInteger q;

        do {
            p = BigInteger.probablePrime(Math.ceil(KEY_SIZE/2.0));
            q = BigInteger.probablePrime(Math.ceil(KEY_SIZE/2.0));
        } while(p != q)

        n = p.multiply(q);
        totientN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        d = e.modPow(new BigInteger("-1"), totientN);
    }

    public void getKeySize() {
        return keySize;
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger cipher) {
        return cipher.modPow(d, n);
    }

    public BigInteger getPublicModulus() {
        return n;
    }

    public BigInteger getPublicExponent() {
        return e;
    }

    public BigInteger getPrivateExponent() {
        return d;
    }
}