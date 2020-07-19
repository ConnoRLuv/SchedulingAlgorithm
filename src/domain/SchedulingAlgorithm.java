package domain;

import entity.CPU;
import entity.PCB;
import entity.Queue;

import java.util.ArrayList;
import java.util.List;

public class SchedulingAlgorithm {
    private static Queue<PCB> pcbQueue;
    private static Queue<PCB> finishQueue;
    private static Queue<PCB> readyQueue;
    private static List<Queue<PCB>> queues;

    private static void initialize(List<PCB> PCBs) {
        PCBs.sort((o1, o2) -> {
            Double arriveT1 = o1.getArriveTime();
            Double arriveT2 = o2.getArriveTime();
            if (arriveT1.doubleValue() == arriveT2.doubleValue()) {
                Double serve1 = o1.getServeTime();
                Double serve2 = o2.getServeTime();
                return serve1.compareTo(serve2);
            }
            return arriveT1.compareTo(arriveT2);
        });
        pcbQueue = new Queue<>(PCBs);
        finishQueue = new Queue<>();
        readyQueue = new Queue<>();

    }

    private static boolean addNewPCB(double time) {
        List<PCB> tmpList = new ArrayList<>();
        for (int i = 0; i < pcbQueue.size(); i++) {
            if (pcbQueue.get(i).getArriveTime() <= time) {
                pcbQueue.get(i).setTimeSliceNum(readyQueue.getTimeSliceNum());
                tmpList.add(pcbQueue.get(i));
            }
        }
        for (PCB pcb : tmpList) {
            pcbQueue.remove(pcb);
        }

        readyQueue.getQueue().addAll(Math.max(readyQueue.size() - 1, 0),tmpList);

        return tmpList.size() == 0;
    }

    private static void sortByPCBPriority() {


        readyQueue.getQueue().sort((o1, o2) -> {
            Integer priority1 = o1.getPriority();
            Integer priority2 = o2.getPriority();
            if (priority1.intValue() == priority2.intValue()) {
                Double arrive1 = o1.getArriveTime();
                Double arrive2 = o2.getArriveTime();
                if(arrive1.doubleValue() == arrive2.doubleValue()){
                    Double serve1 = o1.getServeTime();
                    Double serve2 = o2.getServeTime();
                    return serve1.compareTo(serve2);
                }
                return arrive1.compareTo(arrive2);
            }
            return priority1.compareTo(priority2);
        });

    }

    private static void sortByPCBResponseRatio(double time) {
        for (int i = 0; i < readyQueue.size(); i++) {
            PCB tmp = readyQueue.get(i);
            tmp.setWaitingTime(time - tmp.getArriveTime() > 0 ? time - tmp.getArriveTime() : 0);
            tmp.setResponseRatio((tmp.getWaitingTime() + tmp.getServeTime()) / tmp.getServeTime());
        }

        readyQueue.getQueue().sort((o1, o2) -> {
            Double responseRatio1 = o1.getResponseRatio();
            Double responseRatio2 = o2.getResponseRatio();
            if (responseRatio1.doubleValue() == responseRatio2.doubleValue()) {
                Double arrive1 = o1.getArriveTime();
                Double arrive2 = o2.getArriveTime();
                if(arrive1.doubleValue() == arrive2.doubleValue()){
                    Double serve1 = o1.getServeTime();
                    Double serve2 = o2.getServeTime();
                    return serve1.compareTo(serve2);
                }
                return arrive1.compareTo(arrive2);
            }

            return responseRatio2.compareTo(responseRatio1);
        });

    }

    private static PCB preemptionForPSA(double time, PCB runningPCB) {
        PCB rtn = runningPCB;
        for (int i = 0; i < pcbQueue.size(); i++) {
            if (pcbQueue.get(i).getArriveTime() < time && pcbQueue.get(i).getPriority() < rtn.getPriority())
                rtn = pcbQueue.get(i);
        }
        return rtn;
    }

    private static String readyQueueToStringForRR() {
        String msg = "";
        for (int i = 0; i < readyQueue.size(); i++) {
            msg += "PCB{" +
                    "进程名：'" + readyQueue.get(i).getProcessName() + '\'' +
                    ", 到达时间：" + readyQueue.get(i).getArriveTime() +
                    ", 服务时间：" + readyQueue.get(i).getServeTime() +
                    ", 运行时间：" + readyQueue.get(i).getRunningTime() +
                    ", 剩余时间：" + readyQueue.get(i).getRemainTime() +
                    "}\n";
        }
        return msg;
    }

    private static String readyQueueToStringForHRN() {
        String msg = "";
        for (int i = 0; i < readyQueue.size(); i++) {
            msg += "PCB{" +
                    "进程名：'" + readyQueue.get(i).getProcessName() + '\'' +
                    ", 到达时间：" + readyQueue.get(i).getArriveTime() +
                    ", 服务时间：" + readyQueue.get(i).getServeTime() +
                    ", 运行时间：" + readyQueue.get(i).getRunningTime() +
                    ", 等待时间：" + readyQueue.get(i).getWaitingTime() +
                    ", 响应比：" + readyQueue.get(i).getResponseRatio() +

                    "}\n";
        }
        return msg;
    }

    private static String readyQueueToStringForPSA() {
        String msg = "";
        for (int i = 0; i < readyQueue.size(); i++) {
            msg += "PCB{" +
                    "进程名：'" + readyQueue.get(i).getProcessName() + '\'' +
                    ", 到达时间：" + readyQueue.get(i).getArriveTime() +
                    ", 服务时间：" + readyQueue.get(i).getServeTime() +
                    ", 运行时间：" + readyQueue.get(i).getRunningTime() +
                    ", 剩余时间：" + readyQueue.get(i).getRemainTime() +
                    ", 优先级：" + readyQueue.get(i).getPriority() +
                    "}\n";
        }
        return msg;
    }

    private static String readyQueueToStringForRRMF(Queue<PCB> queue) {
        String msg = "";
        for (PCB pcb:queue.getQueue()) {
            msg += "PCB{" +
                    "进程名：'" + pcb.getProcessName() + '\'' +
                    ", 到达时间：" + pcb.getArriveTime() +
                    ", 服务时间：" + pcb.getServeTime() +
                    ", 运行时间：" + pcb.getRunningTime() +
                    ", 剩余时间：" + pcb.getRemainTime() +
                    ", 时间片数：" + pcb.getTimeSliceNum() +
                    "}\n";
        }
        return msg;
    }

    private static String finishQueueToString(){
        String msg = "";
        double cntTurnaroundTime = 0;
        double cntWeightedTurnaroundTime = 0;
        for (PCB pcb:finishQueue.getQueue()){
            pcb.setTurnaroundTime(pcb.getFinishTime() - pcb.getArriveTime());
            pcb.setWeightedTurnaroundTime(pcb.getTurnaroundTime()/pcb.getServeTime());
            cntTurnaroundTime += pcb.getTurnaroundTime();
            cntWeightedTurnaroundTime+=pcb.getWeightedTurnaroundTime();
            msg += "PCB{" +
                    "进程名：'" + pcb.getProcessName() + '\'' +
                    ", 到达时间：" + pcb.getArriveTime() +
                    ", 服务时间：" + pcb.getServeTime() +
                    ", 完成时间：" + pcb.getFinishTime() +
                    ", 周转时间：" + pcb.getTurnaroundTime() +
                    ", 带权周转：" + pcb.getWeightedTurnaroundTime() +
                    "}\n";
        }

        cntTurnaroundTime /= finishQueue.size();
        cntWeightedTurnaroundTime /= finishQueue.size();
        msg += "平均周转时间为：" + cntTurnaroundTime +"\n平均带权周转时间为："+cntWeightedTurnaroundTime;
        return msg;
    }
    public static String RoundRobin(List<PCB> PCBs, double _requireTime) {
        String msg = "";
        initialize(PCBs);
//        PCB pcb = pcbQueue.remove(0);


        double q = _requireTime / PCBs.size();
        int cnt = 0;
        double time = 0;
        msg += "时间片大小为" + q + "\n\n";

        while (true) {
            if (addNewPCB(time))
                msg += ("此时无新进程\n");
            if (!readyQueue.isEmpty()) {
                cnt++;

                msg += ("Time：" + time + "，第" + cnt + "个时间片开始\n" +
                        readyQueueToStringForRR() +
                        "选中运行的进程：" + readyQueue.get(0).getProcessName() + '\n');
                PCB runningProcess = readyQueue.deQueue();

                if (runningProcess.getRemainTime() - q <= 0) {
                    CPU.processing(q);
                    msg = msg + "CPU";
                    time = time + runningProcess.getRemainTime();
                    runningProcess.setFinishTime(time);
                    runningProcess.setRunningTime(runningProcess.getRunningTime()
                            + runningProcess.getRemainTime());
                    runningProcess.setRemainTime(0);
                    finishQueue.add(runningProcess);


                } else {
                    CPU.processing(q);
                    msg = msg + "CPU";
                    time += q;
                    runningProcess.setRemainTime(runningProcess.getRemainTime() - q);
                    runningProcess.setRunningTime(runningProcess.getRunningTime() + q);

                    readyQueue.add(runningProcess);

                }
            } else {
                msg += ("Time：" + time + ",无进程运行\n\n");

                if(pcbQueue.isEmpty()){
                    msg += finishQueueToString();
                    return msg;
                }else{
                    time = pcbQueue.get(0).getArriveTime();
                }



            }
        }
    }


    public static String HRN(List<PCB> PCBs) {
        initialize(PCBs);
        double time = 0;
        String msg = "";

        while (true) {
            if (addNewPCB(time)) {
                msg += ("此时无新进程\n");
            }
            if (!readyQueue.isEmpty()) {
                sortByPCBResponseRatio(time);
                msg += ("Time：" + time + "，调用HRN调度算法\n");
                msg += readyQueueToStringForHRN();

                PCB runningProcess = readyQueue.deQueue();

                readyQueue.remove(runningProcess);
                msg += ("选中运行运行的进程：" + runningProcess.getProcessName() + '\n');

                CPU.processing(runningProcess.getServeTime());
                msg = msg + "CPU";
                time += runningProcess.getServeTime();
                runningProcess.setFinishTime(time);
                runningProcess.setRunningTime(runningProcess.getServeTime());
                finishQueue.add(runningProcess);


            } else {
                msg += ("Time：" + time + ",无进程运行\n\n");

                if(pcbQueue.isEmpty()){
                    msg += finishQueueToString();
                    return msg;
                }else{
                    time = pcbQueue.get(0).getArriveTime();
                }

            }

        }

    }

    public static String SPSA(List<PCB> PCBs, double _requireTime, boolean _preemption) {
        String msg = "";
        initialize(PCBs);

        double q = _requireTime / PCBs.size();
        int cnt = 0;
        double time = 0;
        msg += "时间片大小为" + q + "\n\n";
        while (true) {
            if (addNewPCB(time)) {
                msg += ("此时无新进程\n");
            }

            if (!readyQueue.isEmpty()) {
                sortByPCBPriority();
                cnt++;

                msg += ("Time：" + time + "，第" + cnt + "个时间片开始，调用静态优先权调度算法\n");
                msg += readyQueueToStringForPSA();

                PCB runningProcess = readyQueue.deQueue();
                msg += ("选中运行的进程：" + runningProcess.getProcessName() + '\n');

                if (runningProcess.getRemainTime() - q <= 0) {
                    CPU.processing(q);
                    msg += "running...\nCPU";
                    time = time + runningProcess.getRemainTime();
                    PCB pcb = preemptionForPSA(time, runningProcess);
                    if (_preemption
                            && !pcbQueue.isEmpty()
                            && pcbQueue.get(0).getArriveTime() < time
                            && pcb.getPriority() < runningProcess.getPriority()) {

                        double tmp = pcb.getArriveTime()
                                - time
                                + runningProcess.getRemainTime();
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                        runningProcess.setRemainTime(time - pcb.getArriveTime());

                        time = pcb.getArriveTime();

                        readyQueue.add(runningProcess);

                    } else {
                        runningProcess.setFinishTime(time);
                        runningProcess.setRunningTime(runningProcess.getRunningTime()
                                + runningProcess.getRemainTime());
                        runningProcess.setRemainTime(0);
                        finishQueue.add(runningProcess);

                    }


                } else {
                    CPU.processing(q);
                    msg += "running...\nCPU";
                    time += q;
                    PCB pcb = preemptionForPSA(time, runningProcess);
                    if (_preemption
                            && !pcbQueue.isEmpty()
                            && pcbQueue.get(0).getArriveTime() < time
                            && pcb.getPriority() < runningProcess.getPriority()) {

                        double tmp = pcb.getArriveTime()
                                - time
                                + q;
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                        runningProcess.setRemainTime(runningProcess.getRemainTime() - tmp);

                        time = pcb.getArriveTime();

//                        cnt += Math.ceil(tmp / q);


                    } else {
                        runningProcess.setRemainTime(runningProcess.getRemainTime() - q);
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + q);


                    }
                    readyQueue.add(runningProcess);
                }
            } else {
                msg += ("Time：" + time + ",无进程运行\n\n");

                if(pcbQueue.isEmpty()){
                    msg += finishQueueToString();
                    return msg;
                }else{
                    time = pcbQueue.get(0).getArriveTime();
                }

            }

        }
    }


    public static String DPSA(List<PCB> PCBs, double _requireTime, boolean _preemption) {
        String msg = "";
        initialize(PCBs);

        double q = _requireTime / PCBs.size();
        int cnt = 0;
        double time = 0;
        msg += "时间片大小为" + q + "\n\n";
        while (true) {
            if (addNewPCB(time)) {
                msg += ("此时无新进程\n");
            }

            if (!readyQueue.isEmpty()) {

                sortByPCBPriority();
                cnt++;
                msg += ("Time：" + time + "，第" + cnt + "个时间片开始，调用动态优先权调度算法\n");
                msg += readyQueueToStringForPSA();

                PCB runningProcess = readyQueue.deQueue();
                msg += ("选中运行的进程：" + runningProcess.getProcessName() + '\n');

                for (int i = 0; i < readyQueue.size(); i++) {
                    readyQueue.get(i).setWaitingTime(readyQueue.get(i).getWaitingTime() + q);
                    int priority = readyQueue.get(i).getPriority();
                    double waitingTime = readyQueue.get(i).getWaitingTime();
                    int new_priority = Math.max(priority - (int) (waitingTime / (3 * q)), 0);
                    readyQueue.get(i).setPriority(new_priority);
                }

                if (runningProcess.getRemainTime() - q <= 0) {
                    CPU.processing(q);
                    msg += "running...\nCPU";
                    time = time + runningProcess.getRemainTime();
                    PCB pcb = preemptionForPSA(time, runningProcess);
                    if (_preemption
                            && !pcbQueue.isEmpty()
                            && pcbQueue.get(0).getArriveTime() < time
                            && pcb.getPriority() < runningProcess.getPriority()) {

                        double tmp = pcb.getArriveTime()
                                - time
                                + runningProcess.getRemainTime();
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                        runningProcess.setRemainTime(time - pcb.getArriveTime());
                        runningProcess.setPriority(runningProcess.getPriority() + 3);
                        runningProcess.setWaitingTime(0);
                        time = pcb.getArriveTime();

                        readyQueue.add(runningProcess);

                    } else {

                        runningProcess.setFinishTime(time);
                        runningProcess.setRunningTime(runningProcess.getRunningTime()
                                + runningProcess.getRemainTime());
                        runningProcess.setRemainTime(0);
                        finishQueue.add(runningProcess);
                    }

                } else {
                    CPU.processing(q);
                    msg += "running...\nCPU";
                    time += q;
                    PCB pcb = preemptionForPSA(time, runningProcess);
                    if (_preemption
                            && !pcbQueue.isEmpty()
                            && pcbQueue.get(0).getArriveTime() < time
                            && pcb.getPriority() < runningProcess.getPriority()) {

                        double tmp = pcb.getArriveTime()
                                - time
                                + q;
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                        runningProcess.setRemainTime(runningProcess.getRemainTime() - tmp);

                        time = pcb.getArriveTime();

//                        cnt += Math.ceil(tmp / q);

                    } else {
                        runningProcess.setRemainTime(runningProcess.getRemainTime() - q);
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + q);
                    }

                    runningProcess.setPriority(runningProcess.getPriority() + 3);
                    runningProcess.setWaitingTime(0);
                    readyQueue.add(runningProcess);

                }


            } else {
                msg += ("Time：" + time + ",无进程运行\n\n");

                if(pcbQueue.isEmpty()){
                    msg += finishQueueToString();
                    return msg;
                }else{
                    time = pcbQueue.get(0).getArriveTime();
                }

            }

        }
    }

    public static String RoundRobinWithMultipleFeedback(List<PCB> PCBs, double _requireTime, int[] _time) {
        String msg = "";
        initialize(PCBs);


        queues = new ArrayList<>();
        readyQueue.setTimeSliceNum(_time[0]);
        queues.add(0, readyQueue);
        for (int i = 1; i < _time.length; i++) {
            queues.add(new Queue<>(i, _time[i]));
        }

        double q = _requireTime / PCBs.size();
        int cnt = 1;
        double time = 0;
        msg += "时间片大小为" + q + "\n\n";
        while (true) {
            if (addNewPCB(time)) {
                msg += ("此时无新进程\n");
            }

            for (int i = 0; i < queues.size(); i++) {
                Queue<PCB> rQueue = queues.get(i);
                if (!rQueue.isEmpty()) {

//                    System.out.println("Time：" + time + ",第" + cnt + "个时间片开始");
//
//                    System.out.print("开始运行的进程：");
//                    System.out.println("就绪队列" + (i + 1) + "：" + rQueue.toString());
                    msg += ("Time：" + time +"，第" + cnt + "个时间片开始\n");
                    for (Queue<PCB> oQueue : queues) {
                        msg += ("就绪队列" + (queues.indexOf(oQueue)+1) + "：");
                        if (oQueue.size() == 0)
                            msg += "无进程\n";
                        else
                            msg += readyQueueToStringForRRMF(oQueue);
                    }
                    PCB runningProcess = rQueue.deQueue();
                    msg += ("选中运行的进程：" + runningProcess.getProcessName() + '\n');

                    if (runningProcess.getRemainTime() - q * runningProcess.getTimeSliceNum() <= 0) {
                        CPU.processing(runningProcess.getRemainTime());
                        msg += "running...\nCPU";
                        time = time + runningProcess.getRemainTime();
                        if (i > 0 && !pcbQueue.isEmpty() && pcbQueue.get(0).getArriveTime() < time) {
                            double tmp = pcbQueue.get(0).getArriveTime()
                                    - time
                                    + runningProcess.getRemainTime();
                            runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                            runningProcess.setRemainTime(time - pcbQueue.get(0).getArriveTime());
                            if (i < queues.size() - 1)
                            runningProcess.setTimeSliceNum((int) (runningProcess.getTimeSliceNum() - tmp / q));

                            time = pcbQueue.get(0).getArriveTime();
                            if (runningProcess.getTimeSliceNum() == 0) {
                                runningProcess.setTimeSliceNum(queues.get(i + 1).getTimeSliceNum());
                                queues.get(i + 1).add(runningProcess);
                            } else {
                                queues.get(i).add(runningProcess);
                            }

                            cnt += Math.ceil(tmp / q);
                            break;
                        }
                        cnt += Math.ceil(runningProcess.getRemainTime() / q);
                        runningProcess.setFinishTime(time);
                        runningProcess.setRunningTime(runningProcess.getRunningTime()
                                + runningProcess.getRemainTime());
                        runningProcess.setRemainTime(0);
                        finishQueue.add(runningProcess);

                    } else {
                        CPU.processing(q * runningProcess.getTimeSliceNum());
                        msg += "running...\nCPU";
                        time += q * runningProcess.getTimeSliceNum();
                        if (i > 0 && !pcbQueue.isEmpty() && pcbQueue.get(0).getArriveTime() < time) {
                            double tmp = pcbQueue.get(0).getArriveTime()
                                    - time
                                    + q * runningProcess.getTimeSliceNum();
                            runningProcess.setRunningTime(runningProcess.getRunningTime() + tmp);
                            runningProcess.setRemainTime(runningProcess.getRemainTime() - tmp);
                            if (i < queues.size() - 1)
                                runningProcess.setTimeSliceNum((int) (runningProcess.getTimeSliceNum() - tmp / q));


                            if (runningProcess.getTimeSliceNum() == 0) {
                                runningProcess.setTimeSliceNum(queues.get(i + 1).getTimeSliceNum());
                                queues.get(i + 1).add(runningProcess);
                            } else {
                                queues.get(i).add(runningProcess);
                            }

                            time = pcbQueue.get(0).getArriveTime();
                            cnt += Math.ceil(tmp / q);
                            break;
                        }
                        cnt += runningProcess.getTimeSliceNum();
                        runningProcess.setRemainTime(runningProcess.getRemainTime() - q * runningProcess.getTimeSliceNum());
                        runningProcess.setRunningTime(runningProcess.getRunningTime() + q * runningProcess.getTimeSliceNum());

                        if (i != queues.size() - 1) {
                            runningProcess.setTimeSliceNum(queues.get(i + 1).getTimeSliceNum());
                            queues.get(i + 1).add(runningProcess);
                        } else {
                            runningProcess.setTimeSliceNum(queues.get(i).getTimeSliceNum());
                            queues.get(queues.size() - 1).add(runningProcess);
                        }


                    }
                    break;
                } else if (i == queues.size() - 1) {
                    msg += ("Time：" + time + ",无进程运行\n\n");

                    if(pcbQueue.isEmpty()){
                        msg += finishQueueToString();
                        return msg;
                    }else{
                        time = pcbQueue.get(0).getArriveTime();
                    }
                }
            }
        }

    }


}
