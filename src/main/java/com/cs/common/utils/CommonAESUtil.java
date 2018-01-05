package com.cs.common.utils;

import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CommonAESUtil {
	
	private static final String iv = "www.cscx.com1234";
	private static final String WEB_SERVICE = "com.cscx.www*+_-";
	
	
    /** 
     * @author miracle.qu 
     * @see AES算法加密明文 
     * @param data 明文 
     * @param key 密钥，长度16 
     * @param iv 偏移量，长度16 
     * @return 密文 
     */  
      public static String encryptAES(String data,String appointmentDate) throws Exception {  
            try {
            	String key = "Cscx"+appointmentDate+"+*";
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
                int blockSize = cipher.getBlockSize();  
                byte[] dataBytes = data.getBytes();  
                int plaintextLength = dataBytes.length;  
                if (plaintextLength % blockSize != 0) {  
                    plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));  
                }  
                byte[] plaintext = new byte[plaintextLength];  
                System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);  
                SecretKey keyspec = new SecretKeySpec(key.getBytes(), "AES");  
                IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  
                cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);  
                byte[] encrypted = cipher.doFinal(plaintext);  
                return new String(new Base64().encode(encrypted)).trim();  
            } catch (Exception e) {  
                e.printStackTrace();  
                return null;  
            }  
        }  
      	
      public static String encryptAES(String data) throws Exception {
    	  try {
          	  String key = "Cscx"+DateUtil.getDayStr(new Date())+"+*";
              Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
              int blockSize = cipher.getBlockSize();  
              byte[] dataBytes = data.getBytes("UTF-8");
//              byte[] dataBytes = data.getBytes();
              int plaintextLength = dataBytes.length;  
              if (plaintextLength % blockSize != 0) {  
                  plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));  
              }  
              byte[] plaintext = new byte[plaintextLength];  
              System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);  
              SecretKey keyspec = new SecretKeySpec(key.getBytes(), "AES");  
              IvParameterSpec ivspec = new IvParameterSpec(WEB_SERVICE.getBytes());  
              cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);  
              byte[] encrypted = cipher.doFinal(plaintext);  
              return new String(new Base64().encode(encrypted)).trim();  
          } catch (Exception e) {  
              e.printStackTrace();  
              return null;  
          }
      }
      
        /** 
         * @author miracle.qu 
         * @see AES算法解密密文 
         * @param data 密文 
         * @param key 密钥，长度16 
         * @param iv 偏移量，长度16 
         * @return 明文 
         */ 
        public static String decryptAES(String data,String appointmentDate) throws Exception {  
            try  
            {  
            	String key = "Cscx"+appointmentDate+"+*";
                byte[] encrypted1 = new Base64().decode(data); 
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
                SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");  
                IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  
                cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original);  
                return originalString.trim();  
            }  
            catch (Exception e) {  
                e.printStackTrace();  
                return null;  
            }  
        }  
        
        public static String decryptAES(String data) throws Exception {
        	try  
            {  
            	String key = "Cscx"+DateUtil.getDayStr(new Date())+"+*";
                byte[] encrypted1 = new Base64().decode(data.getBytes("UTF-8"));
//                byte[] encrypted1 = new Base64().decode(data); 
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
                SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");  
                IvParameterSpec ivspec = new IvParameterSpec(WEB_SERVICE.getBytes());  
                cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original,"UTF-8");
//                String originalString = new String(original);
                return originalString.trim();  
            }  
            catch (Exception e) {  
                e.printStackTrace();  
                return null;  
            }  
        }
        
        public static void main(String[] args) throws Exception {
			System.out.println(decryptAES("4MKdQjfdcMk/MmlLQTRaoeeOLk+lPyKrq+pYNT597GyEFmX6cobuLm5rItGKrLQg+3eN3KwAaXcfyLDNo7GH8UoGrrQ/Q6gxk/u4M1QWcXfLFg/vnmCYtg5VgcgzWTzTirWydchyx1KTAMm1NwcrtQa11QvOWjSrIwSxztvZjvISoVjWmTLUXXil3RS2j9+B9anADqmhvf5NSl2pWNzQQfz9kpOqvy7aHYEgnuujexYrIcUiJdIJeumN1S/ls/TqQ+eaBgqL1YF6ALS37fUWkTaDENNXoYsiPCNx9XdN7kEhLTq5BB9h3+OOfsVXID2E6e7Eacar2iiS2tNwwMb5RY8hzT9eLz/u+cZrEykK15ZYWl1dKXWd0SMAkU8DVlSsMfLNLbekwr7LEq6lkWIM8D6RUjn+otuFoAXhYkJZkPb/ItTI0/uae8QSPTRUM6PNfTJLo80HJWSLqFGCdzFo7z8O6OjiBPmb9un3oJ1XqCixBDoV7gr2BbMLbWuurLzlf/SvQOU3nCLkPV5Sy0hY4Dx56tkH1Qg+XxsekhszAr12u6Q2bPafHGqpAP8BdJ7aVfzd7up+p72WDddomDEDvlpVX5kPp9inD5zsd12ZHs8ihfgL078nvHr3wZJPkxXvVCkIZC5Q4OjuiqOjVM8s1+LjmT1op78IRGqp7/BpoU1xKPvTMcMo7j9ED6Y0TazN"));
		}
}
