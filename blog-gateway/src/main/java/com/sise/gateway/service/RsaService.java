package com.sise.gateway.service;

/**
 * @Description: rsa加密解密接口
 * @Author: xzw
 * @Date: 2023/1/5 21:16
 */
public interface RsaService {

    /***
     * RSA解密
     *
     * @param encryptData
     * @return
     * @throws Exception
     */
    public String RSADecryptDataPEM(String encryptData, String prvKey) throws Exception;

    /***
     * RSA解密
     *
     * @param encryptBytes
     * @return
     * @throws Exception
     */
    public String RSADecryptDataBytes(byte[] encryptBytes, String prvKey) throws Exception;

    /***
     * RSA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String RSAEncryptDataPEM(String data, String pubKey) throws Exception;

    public String getRsaAlgorithm();
}
