package irc;

public class Utils {

  public static void printHex(byte[] ba) {
    //convert the byte to hex format method 2
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < ba.length; i++) {
      String hex = Integer.toHexString(0xff & ba[i]);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    System.out.println(hexString.toString());
  }

}
