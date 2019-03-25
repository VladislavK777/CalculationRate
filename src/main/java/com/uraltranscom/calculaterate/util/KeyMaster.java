package com.uraltranscom.calculaterate.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 19.07.2018
 */

public final class KeyMaster {
    private static Logger logger = LoggerFactory.getLogger(KeyMaster.class);
        private static final int iterationCount = 10;
        private static final String algorithm = "PBEWithMD5AndDES";
        private static final String coding = "UTF8";
        private static final byte[] salt = new byte[]{100, 105, 109, 97, 107, 118, 111, 110};

        private KeyMaster() {
        }

        public static SecretKey genSecretKey(String key) throws Exception {
            KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iterationCount);
            return SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        }

        public static String secretKeyToString(SecretKey secretKey) {
            return new String(Base64.encodeBase64(secretKey.getEncoded()), StandardCharsets.UTF_8);
        }

        private static SecretKey getSecretKey(String stringKey) {
            return new SecretKeySpec(Base64.decodeBase64(stringKey), algorithm);
        }

        public static String dec(String hash, String stringKey) throws Exception {
            SecretKey secretKey = getSecretKey(stringKey);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount );
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, secretKey, paramSpec);
            byte[] decHash = Base64.decodeBase64(hash);
            return new String(cipher.doFinal(decHash), coding);
        }

        public static String enc(String text, String stringKey) throws Exception {
            SecretKey secretKey = getSecretKey(stringKey);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, secretKey, paramSpec);
            byte[] encText = cipher.doFinal(text.getBytes(coding));
            return new String(Base64.encodeBase64(encText), StandardCharsets.UTF_8);
        }
}
