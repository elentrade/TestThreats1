package com.company;

import java.util.Arrays;

public class Main {
    static  final int size = 10000000;
    static float [] global_arr = new float[size];
    static float [] arr1 =new float[size/2];
    static float [] arr2 =new float[size/2];
    private static Object mon= new Object();

//////////////////////////////////////////////////////////
public static void fillArrayWithUnit (float [] arr){
    for (int i=0;i<size;i++ ) {
       arr[i] =1;
    }

}
///////////////////////////////////////////////////////////
    public static void fillArrayWithMath (float [] arr){
    for (int i=0;i<arr.length;i++ ) {
            arr[i] =(float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) {
//////////////////1-st method////////////////
    fillArrayWithUnit(global_arr);
    long start_time = System.currentTimeMillis();
    fillArrayWithMath(global_arr);
    System.out.println(System.currentTimeMillis()-start_time);
//////////////////2-nd method/////////////
    long start_time1 = System.currentTimeMillis();
    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (mon){
                System.arraycopy(global_arr,0,arr1,0,size/2);
                fillArrayWithMath(arr1);
            }

        }
    });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon){
                    System.arraycopy(global_arr,size/2,arr2,0,size/2);
                    fillArrayWithMath(arr2);
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.arraycopy(arr1,0,global_arr,0,size/2);
        System.arraycopy(arr2,0,global_arr,size/2,size/2);
        System.out.println(System.currentTimeMillis()-start_time1);

    }
}
