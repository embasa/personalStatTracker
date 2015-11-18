import sun.security.krb5.EncryptionKey;
import sun.security.krb5.internal.KerberosTime;

import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosKey;
import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.Inet4Address;

/**
 * Randome shit testing
 * <p>
 * Created by bruno on 11/17/15.
 */
public class Randomness {
  public static void main(String[] args) throws IOException {

    Randomness r = new Randomness();

    r.initializeKerb();
    KerberosPrincipal kerberosPrincipal;
    kerberosPrincipal = new KerberosPrincipal("bruno@CS166");
    System.out.println(kerberosPrincipal.getName());
    System.out.println(kerberosPrincipal.getRealm());

    Randomness.checkHosts("192.168.5");

    Subject subject = new Subject();
    LoginContext lc;

    try {
      lc = new LoginContext("EntryName", subject, new MyCallbackHandler());
      lc.login();

    } catch (LoginException e) {
      // If an exception is caused here, it's likely the ~/.java.login.config file is wrong
    }

  }

  public static void checkHosts(String subnet) throws IOException {


    int timeout = 1000;
    for (int i = 1; i < 255; i++) {

      String host = subnet + "." + i;
      if (Inet4Address.getByName(host).isReachable(timeout)) {
        System.out.println(host + " reachable");
        System.out.println(Inet4Address.getByName(host));
      }
    }
  }

  public void initializeKerb() {
    KerberosTime time;
    time = new KerberosTime(System.currentTimeMillis());
    System.out.println(time);

  }

}
