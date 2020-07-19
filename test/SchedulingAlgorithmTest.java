import domain.SchedulingAlgorithm;
import entity.PCB;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SchedulingAlgorithmTest {

    @Test
    public void RoundRobin(){
        List<PCB> pcbs = Arrays.asList(new PCB("A", 0, 4.2),
                new PCB("B", 1, 3),
                new PCB("C", 2, 4),
                new PCB("D", 3, 2),
                new PCB("E",4,4)
        );
        SchedulingAlgorithm.RoundRobin(pcbs,5);
    }

    @Test
    public void HRN(){
        List<PCB> pcbs = Arrays.asList(new PCB("A", 0, 4),
                new PCB("B", 1, 3),
                new PCB("C", 2, 4),
                new PCB("D", 3, 2),
                new PCB("E",4,4)
        );
        SchedulingAlgorithm.HRN(pcbs);

    }

    @Test
    public void RoundRobinWithMultipleFeedback(){
        List<PCB> pcbs = Arrays.asList(new PCB("A", 0, 4),
                new PCB("B", 1, 3),
                new PCB("C", 2, 4),
                new PCB("D", 3, 2),
                new PCB("E",4,4),
                new PCB("F",15,3)
        );
        int[] time = {1,2,4,8};
        System.out.println(SchedulingAlgorithm.RoundRobinWithMultipleFeedback(pcbs,5,time));
    }

    @Test
    public void SPSA(){
        List<PCB> pcbs = Arrays.asList(new PCB("A", 0, 4,0),
                new PCB("B", 1, 3,2),
                new PCB("C", 2, 4,1),
                new PCB("D", 3, 2,2),
                new PCB("E",4,4,3)
        );
//        SchedulingAlgorithm.SPSA(pcbs,5);
    }

    @Test
    public void DPsa(){
        List<PCB> pcbs = Arrays.asList(
                new PCB("A", 0, 4,0),
                new PCB("B", 1, 3,2),
                new PCB("C", 2, 4,1),
                new PCB("D", 3, 2,2),
                new PCB("E",4,4,3)
        );
        SchedulingAlgorithm.DPSA(pcbs,5,false);
    }
}
