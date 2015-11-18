package irc;/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javax.crypto.KeyGenerator;
import java.net.*;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

  private class ChatClient{
    public String name = null;
    public Socket socket = null;
    public String key = null;

  }

  private PrivateKey privateKey;
  public static PublicKey publicKey;
  private List<ChatClient> clients;
  public int port;
  private ServerSocket serverSocket;
  private List<String> chatText;


  public EchoServer(int port) {
    SecureRandom random = null;
    KeyPairGenerator keyGen = null;
    this.chatText = new ArrayList<>();
    try {
      keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
      random = SecureRandom.getInstance("SHA1PRNG", "SUN");
    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
      e.printStackTrace();
    }
    if (keyGen == null) throw new AssertionError();
    keyGen.initialize(1024,random);
    KeyPair keyPair = keyGen.generateKeyPair();
    this.privateKey = keyPair.getPrivate();
    publicKey = keyPair.getPublic();
    this.port = port;

    try {
      this.serverSocket = new ServerSocket(this.port);
      System.out.println(this.serverSocket.getLocalPort());
      System.out.println(this.serverSocket.getLocalSocketAddress().toString());
      System.out.println(this.serverSocket.getInetAddress().toString());
      try (Socket clientSocket = this.serverSocket.accept()) {
        try (PrintWriter out =
                 new PrintWriter(clientSocket.getOutputStream(), true)) {
          try (BufferedReader in = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
              System.out.println(inputLine);
              out.println(inputLine);
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port "
          + this.port + " or listening for a connection");
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException {

    if (args.length != 1) {
      System.err.println("Usage: java EchoServer <port number>");
      System.exit(1);
    }

    int portNumber = 2304;
    //int portNumber = Integer.parseInt(args[0]);

    try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
      System.out.println(serverSocket.getLocalPort());
      System.out.println(serverSocket.getLocalSocketAddress().toString());
      System.out.println(serverSocket.getInetAddress().toString());
      try (Socket clientSocket = serverSocket.accept()) {
        try (PrintWriter out =
                 new PrintWriter(clientSocket.getOutputStream(), true)) {
          try (BufferedReader in = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
              System.out.println(inputLine);
              out.println(inputLine);
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port "
          + portNumber + " or listening for a connection");
      System.out.println(e.getMessage());
    }
  }
}
