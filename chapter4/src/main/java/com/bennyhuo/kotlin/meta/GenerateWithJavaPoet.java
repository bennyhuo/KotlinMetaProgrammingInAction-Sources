package com.bennyhuo.kotlin.meta;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

/**
 * Created by benny.
 */
public class GenerateWithJavaPoet {
  public static void main(String[] args) throws IOException {
    String text = "Hello World1111111111111111111111eweadfasdfasdfasdfasfasdfasdfasdfa11111111111111111111111";
//    CodeBlock codeBlock = CodeBlock.of("System.out.println(\"" + text + "\")");
    CodeBlock codeBlock = CodeBlock.of("System.out.println(\"$L\")", text);
    System.out.println(codeBlock);

    JavaFile.builder("com.test", TypeSpec.classBuilder("Hello")
        .addMethod(MethodSpec.methodBuilder("foo")
          .addCode(codeBlock)
          .build())
      .build())
      .build().writeTo(System.out);
  }
}
