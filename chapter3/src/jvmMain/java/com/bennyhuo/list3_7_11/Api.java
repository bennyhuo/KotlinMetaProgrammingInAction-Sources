package com.bennyhuo.list3_7_11;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

// 3-7
@Service(url = "https://www.bennyhuo.com/api")
public interface Api {
  // 3-9
  User getUser(
    @Param("id") long id,
    @Param("clientId") String clientId,
    @Param("token") String token
  );

  public static class ApiImpl implements Api {

    @Override
    public User getUser(long id, String clientId, String token) {
      return null;
    }
  }

  public static void main(String[] args) throws NoSuchMethodException {
    // 3-8
    System.out.println(Api.class.getAnnotation(Service.class).url());

    //region 3-10
    Annotation[][] parameterAnnotations = Api.class.getDeclaredMethod(
        "getUser", long.class, String.class, String.class
      ).getParameterAnnotations();

    for (Annotation[] annotations : parameterAnnotations) {
      for (Annotation annotation : annotations) {
        if (annotation instanceof Param) {
          System.out.println(((Param) annotation).value());
        }
      }
    }
    //endregion

    //region 3-11 需要 -parameters
    Parameter[] parameters = Api.class.getDeclaredMethod(
      "getUser", long.class, String.class, String.class
    ).getParameters();

    for (Parameter parameter : parameters) {
      System.out.println(parameter.getName() + ": " + parameter.getType());
    }
    //endregion
  }

}
