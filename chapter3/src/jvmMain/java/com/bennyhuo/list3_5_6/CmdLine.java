package com.bennyhuo.list3_5_6;

// 3-5
public interface CmdLine {
  void run(String cmd);

  void runAsAdmin(String cmd);

  void runAsRoot(String cmd);

  public static class CmdLineImpl implements CmdLine {

    @Override
    public void run(String cmd) {
      System.out.println("run: " + cmd);
    }

    @Override
    public void runAsAdmin(String cmd) {
      System.out.println("runAsAdmin: " + cmd);
    }

    @Override
    public void runAsRoot(String cmd) {
      System.out.println("runAsRoot: " + cmd);
    }
  }
}
