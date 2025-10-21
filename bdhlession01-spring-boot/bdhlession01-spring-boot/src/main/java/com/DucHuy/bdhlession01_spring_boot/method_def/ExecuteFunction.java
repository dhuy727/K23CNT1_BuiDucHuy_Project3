package com.DucHuy.bdhlession01_spring_boot.method_def;
import java.util.Arrays;
@FunctionalInterface
interface ExecuteFunction {
    public int excute(int a,int b);
}
class MathUtils{
    public MathUtils(){}
    public MathUtils(String str){
        System.out.println("MathUtils:"+str);
    }
    public static int sum(int a, int b){
        return a+b;
    }
    public static int minus(int a, int b){
        return a-b;
    }
    public static int multiply(int a, int b){
        return a*b;
    }
}
