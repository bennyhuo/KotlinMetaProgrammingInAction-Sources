package com.bennyhuo.list3_5_6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

// 3-6
public class CmdLineProxy {

  private static final Map<String, Integer> users = new HashMap<>() {
    {
      put("bennyhuo", 1);
      put("admin", 2);
      put("root", 3);
    }
  };

  private static final Map<String, Integer> methods = new HashMap<>() {
    {
      put("run", 1);
      put("runAsAdmin", 2);
      put("runAsRoot", 3);
    }
  };

  public static CmdLine newProxy(CmdLine delegate) {
    return (CmdLine) Proxy.newProxyInstance(
      CmdLine.class.getClassLoader(),
      new Class[]{CmdLine.class},
      new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          checkPrivilege(method.getName());
          return method.invoke(delegate, args);
        }
      });
  }

  private static void checkPrivilege(String methodName)
    throws IllegalAccessException {
    int level = getCurrentUserLevel();
    int methodLevel = methods.getOrDefault(methodName, 1);
    if (level < methodLevel) {
      throw new IllegalAccessException("Cannot access to method: " + methodName);
    }
  }

  private static int getCurrentUserLevel() {
    return users.getOrDefault(getCurrentUser(), 1);
  }

  private static String getCurrentUser() {
    return "bennyhuo";
  }

  public static void main(String[] args) {
    CmdLine cmdLine = CmdLineProxy.newProxy(new CmdLine.CmdLineImpl());
    cmdLine.run("java -version");
//    cmdLine.runAsAdmin("sudo apt remove openjdk-11-jdk");
    cmdLine.runAsRoot("rm -rf /");
  }
}
