package entity;

import java.util.ArrayList;
import java.util.List;

public class Queue<T>{
    private final List<T> queue = new ArrayList<>();
    private int priority;
    private int timeSliceNum;

    public Queue(){
        priority = 0;
    }

    public Queue(int priority) {
        this.priority = priority;
    }

    public Queue(List<T> list) {
        priority = 0;
        timeSliceNum = 1;
        queue.addAll(list);
    }

    public Queue(int priority,int timeSliceNum) {
        this.priority = priority;
        this.timeSliceNum = timeSliceNum;

    }

    public List<T> getQueue(){
        return queue;
    }



    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean add(T element){
        return queue.add(element);
    }

    public  void add(int index,T element){
        queue.add(index,element);
    }

    public boolean addAll(List<T> list){
        return queue.addAll(list);
    }

    public boolean addAll(List<T> list,int index){
        for (int i = 0; i < list.size(); i++) {
            queue.add(0,list.get(list.size()-1-i));
        }
        return true;
    }

    public boolean remove(T element){
        return queue.remove(element);
    }

    public T remove(int index){
        return queue.remove(index);
    }

    public T deQueue(){
            return queue.remove(0);
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){
        return queue.size();
    }

    public T get(int index){
        return queue.get(index);
    }

    public int getTimeSliceNum() {
        return timeSliceNum;
    }

    public void setTimeSliceNum(int timeSliceNum) {
        this.timeSliceNum = timeSliceNum;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (T tmp : queue) {
            toString.append(tmp.toString()).append('\n');

        }
        return toString.toString();
    }



}
