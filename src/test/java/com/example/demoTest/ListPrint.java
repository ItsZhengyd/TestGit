package com.example.demoTest;

public class ListPrint {
    public static void main(String[] args) {

    }
    public void ForToPrint(){
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
        }
    }
    public void zhangyi(){
        System.out.println("zhangyi");
    }

    public  void yangahojie() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + (j * i) + " ");
            }
            System.out.println();
        }
    }

    public void zhaomengsha(){
        System.out.println("Let's go!");

    }
}
