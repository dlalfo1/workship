package com.gdu.workship.util;

import java.security.MessageDigest;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

  public String preventXSS(String str) {
    str = str.replace("<", "&lt;");
    str = str.replace(">", "&gt;");
    return str;
  }

  public String getRandomString(int count, boolean letters, boolean numbers) {
    return RandomStringUtils.random(count, letters, numbers);
  }

  public String getSha256(String str) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str.getBytes());
    } catch(Exception e) {
      e.printStackTrace();
    }
    byte[] b = messageDigest.digest();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < b.length; i++) {
      sb.append( String.format("%2X", b[i]) );
    }
    return sb.toString();
  }
  
}