package entity;

public class PCB{
    private String processName;                     //进程名字
    private double arriveTime;                      //到达时间
    private double serveTime;                       //服务时间
    private double remainTime;                      //剩余服务时间
    private double finishTime;                      //结束时间
    private double runningTime;                     //运行时间
    private int priority;                        //优先级
    private double turnaroundTime;                  //周转时间
    private double weightedTurnaroundTime;          //带权周转时间
    private double waitingTime;                     //作业等待时间
    private double responseRatio;                   //响应比
    private int timeSliceNum;                       //时间片数

    public PCB(String processName, double arriveTime, double serveTime) {
        this.processName = processName;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        remainTime = serveTime;
    }

    public PCB(String processName, double arriveTime, double serveTime, int priority) {
        this.processName = processName;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.priority = priority;
        remainTime = serveTime;
    }



    public PCB() {
        priority = 99999;
        serveTime = 0;
        responseRatio = 0;
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }

    public double getServeTime() {
        return serveTime;
    }

    public void setServeTime(double serveTime) {
        this.serveTime = serveTime;
    }

    public double getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(double remainTime) {
        this.remainTime = remainTime;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(double runningTime) {
        this.runningTime = runningTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public double getWeightedTurnaroundTime() {
        return weightedTurnaroundTime;
    }

    public void setWeightedTurnaroundTime(double weightedTurnaroundTime) {
        this.weightedTurnaroundTime = weightedTurnaroundTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getResponseRatio() {
        return responseRatio;
    }

    public void setResponseRatio(double responseRatio) {
        this.responseRatio = responseRatio;
    }

    public int getTimeSliceNum() {
        return timeSliceNum;
    }

    public void setTimeSliceNum(int timeSliceNum) {
        this.timeSliceNum = timeSliceNum;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "进程名：'" + processName + '\'' +
                ", 到达时间：" + arriveTime +
                ", 服务时间：" + serveTime +
                ", 结束时间：" + finishTime +
                ", 运行时间：" + runningTime +
                ", 优先级：" + priority +
                ", 周转时间：" + turnaroundTime +
                ", 带权周转：" + weightedTurnaroundTime +
                '}';
    }


}
