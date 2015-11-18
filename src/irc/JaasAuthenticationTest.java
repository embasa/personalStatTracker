package irc;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class JaasAuthenticationTest {

	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "/home/bruno/IdeaProjects/personalStatTracker/src/irc/jaas.conf");

		String name = "myName";
		String password = "myPassword";

		try {
			LoginContext lc = new LoginContext("Test", new TestCallbackHandler(name, password));
			lc.login();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}