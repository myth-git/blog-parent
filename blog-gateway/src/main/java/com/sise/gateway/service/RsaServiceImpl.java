package com.sise.gateway.service;

import com.sise.gateway.rsa.Base64Utils;
import com.sise.gateway.rsa.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/5 21:17
 */
@Service("RsaService")
public class RsaServiceImpl implements RsaService {
    /**
     * rsa私钥解密
     * @param encryptData
     * @param prvKey
     * @return
     * @throws Exception
     */
    @Override
    public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception {
        byte[] encryptBytes = encryptData.getBytes();
        byte[] byPrivateKey = RSA.decryptByPrivateKey(Base64Utils.decode(encryptData), prvKey);
        System.out.println("byPrivateKey======="+ byPrivateKey);

        String outString = new String(byPrivateKey, "UTF-8");
        System.out.println("outString========" + outString);
        return outString;
    }

    @Override
    public String RSADecryptDataBytes(byte[] encryptBytes, String prvKey) throws Exception {
        byte[] prvdata = RSA.decryptByPrivateKey(encryptBytes, prvKey);
        String outString = new String(prvdata, "utf-8");
        return outString;
    }

    /**
     * RSA公钥加密
     * @param data
     * @param pubKey
     * @return
     * @throws Exception
     */
    @Override
    public String RSAEncryptDataPEM(String data, String pubKey) throws Exception {
        byte[] byPublicKey = RSA.encryptByPublicKey(data.getBytes("UTF-8"), pubKey);
        String outString = new String(Base64Utils.encode(byPublicKey));

        return outString;
    }

    @Override
    public String getRsaAlgorithm() {
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return keyFactory.getAlgorithm();
    }
}
