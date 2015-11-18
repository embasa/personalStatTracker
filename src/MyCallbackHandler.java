import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * Blahness
 * Created by bruno on 11/17/15.
 */
class MyCallbackHandler implements CallbackHandler {
  private String username;
  private String password;
  public void handle(Callback[] callbacks)
      throws IOException, UnsupportedCallbackException {

    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof NameCallback) {
        NameCallback nc = (NameCallback)callbacks[i];
        nc.setName(username);
      } else if (callbacks[i] instanceof PasswordCallback) {
        PasswordCallback pc = (PasswordCallback)callbacks[i];
        pc.setPassword(password.toCharArray());
      } else throw new UnsupportedCallbackException
          (callbacks[i], "Unrecognised callback");
    }
    char passwordchars[] = password.toCharArray();
    for (int i = 0; i < password.length(); i++) passwordchars[i] = '*';
  }
}

