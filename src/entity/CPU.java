package entity;

import domain.SchedulingAlgorithm;

import java.util.Timer;
import java.util.TimerTask;

public class CPU {

    public static void processing(){
        try{
            Thread.sleep(000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processing1(){
        try{
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void processing(double q){
        try{
            Thread.sleep((long) (000*q));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
