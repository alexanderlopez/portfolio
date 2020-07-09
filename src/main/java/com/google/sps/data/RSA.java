package com.google.sps.data;

public class RSA {
    public static final int KEY_SIZE = 258;

    private int keySize;

    public RSA() {
        keySize = KEY_SIZE;
    }

    public RSA(int keySize) {
        this.keySize = keySize;
    }

    
}