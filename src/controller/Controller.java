package controller;

import domain.SchedulingAlgorithm;
import entity.PCB;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Controller {


    public StackPane stackPane;

    public Button RR_Button;
    public BorderPane borderPane;
    public TableView<PCBData> addTable;
    public Label title;
    public TextArea resultArea;
    public Button addBtn;
    public Button deleteBtn;
    public Button rewriteBtn;
    public Button submitBtn;

    public TableColumn<PCBData, String> processNameCol;
    public TableColumn<PCBData, String> arriveTimeCol;
    public TableColumn<PCBData, String> serveTimeCol;
    public TableColumn<PCBData, String> priorityCol;
    public ChoiceBox<String> choiceBox;

    public Timeline delay;
    public static Timeline aniTimeline;
    public TableView<QueueData> addQueueTable;
    public TableColumn<QueueData, String> queueNameCol;
    public TableColumn<QueueData, String> queuePriorityCol;
    public TableColumn<QueueData, String> timeSliceNumCol;
    public Button addQueueBtn;
    public Button deleteQueueBtn;
    public Button rewriteQueueBtn;
    public Button submitQueueBtn;

    private final ObservableList<PCBData> data = FXCollections.observableArrayList();
    private final ObservableList<QueueData> queueData = FXCollections.observableArrayList();
    public Button aniBtn;

    private String[] msg = {};
    private int num = 0;
    private int cnt = 0;

    public void init(String msg1) {
        if (delay != null) {
            delay.stop();
        }
        title.setText(msg1);
        title.setDisable(false);
        addBtn.setDisable(false);
        deleteBtn.setDisable(false);
        rewriteBtn.setDisable(false);
        submitBtn.setDisable(false);
        aniBtn.setDisable(false);
        addTable.setDisable(false);
        resultArea.setDisable(false);
        resultArea.setText("");


        addQueueBtn.setDisable(true);
        deleteQueueBtn.setDisable(true);
        rewriteQueueBtn.setDisable(true);
        submitQueueBtn.setDisable(true);
        addQueueTable.setDisable(true);
        submitBtn.setVisible(true);
        msg = new String[]{};

        delay = new Timeline();
        num = 0;

        queueData.clear();
        addQueueTable.setItems(queueData);

        data.clear();
        data.addAll(
                new PCBData("A", 0, 4, 0),
                new PCBData("B", 1, 3, 1),
                new PCBData("C", 2, 4, 2),
                new PCBData("D", 3, 2, 2),
                new PCBData("E", 4, 4, 3)

        );

        processNameCol.setPrefWidth(100);
        processNameCol.setCellValueFactory(new PropertyValueFactory("processName"));
        processNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        processNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PCBData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setProcessName(t.getNewValue())
        );

        arriveTimeCol.setPrefWidth(100);
        arriveTimeCol.setCellValueFactory(new PropertyValueFactory("arriveTime"));
        arriveTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        arriveTimeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PCBData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setArriveTime(Double.toString(Double.parseDouble(t.getNewValue())))
        );


        serveTimeCol.setPrefWidth(100);
        serveTimeCol.setCellValueFactory(new PropertyValueFactory("serveTime"));
        serveTimeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serveTimeCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PCBData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setServeTime(Double.toString(Double.parseDouble(t.getNewValue())))
        );

        priorityCol.setPrefWidth(100);
        priorityCol.setCellValueFactory(new PropertyValueFactory("priority"));
        priorityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        priorityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<PCBData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPriority(Integer.toString(Integer.parseInt(t.getNewValue())))
        );
        addTable.setItems(data);


    }

    public void init2() {
        queueData.clear();
        queueData.addAll(
                new QueueData("就绪队列1", 0, 1),
                new QueueData("就绪队列2", 1, 2),
                new QueueData("就绪队列3", 2, 4)
        );

        queueNameCol.setPrefWidth(100);
        queueNameCol.setCellValueFactory(new PropertyValueFactory("queueName"));
        queueNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        queueNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<QueueData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setQueueName(t.getNewValue())
        );

        queuePriorityCol.setPrefWidth(100);
        queuePriorityCol.setCellValueFactory(new PropertyValueFactory("priority"));
        queuePriorityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        queuePriorityCol.setOnEditCommit(
                (TableColumn.CellEditEvent<QueueData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPriority(Integer.toString(Integer.parseInt(t.getNewValue())))
        );


        timeSliceNumCol.setPrefWidth(100);
        timeSliceNumCol.setCellValueFactory(new PropertyValueFactory("timeSliceNum"));
        timeSliceNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeSliceNumCol.setOnEditCommit(
                (TableColumn.CellEditEvent<QueueData, String> t) -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setTimeSliceNum(Integer.toString(Integer.parseInt(t.getNewValue())))
        );
        addQueueTable.setItems(queueData);
    }


    public void RR(ActionEvent actionEvent) {
        init("时间片轮转调度算法");
        choiceBox.setVisible(false);
        priorityCol.setVisible(false);
    }

    public void HRN(ActionEvent actionEvent) {
        init("高响应比调度算法");
        choiceBox.setVisible(false);
        priorityCol.setVisible(false);
    }

    public void PSA(ActionEvent actionEvent) {
        init("优先权调度算法");
        choiceBox.setVisible(true);
        choiceBox.setItems(FXCollections.observableArrayList(
                "静态(非抢占式)", "静态(抢占式)", "动态(非抢占式)", "动态(抢占式)"));
        priorityCol.setVisible(true);

    }

    public void RRMF(ActionEvent actionEvent) {
        init("多级反馈队列调度算法");
        init2();
        choiceBox.setVisible(false);
        priorityCol.setVisible(false);
        submitBtn.setDisable(true);
        submitBtn.setVisible(false);
        aniBtn.setDisable(true);
        addQueueBtn.setDisable(false);
        deleteQueueBtn.setDisable(false);
        rewriteQueueBtn.setDisable(false);
        submitQueueBtn.setDisable(false);
        addQueueTable.setDisable(false);

    }

    public void addButtonOnAction(ActionEvent mouseEvent) {
        data.add(new PCBData("请输入信息", 0.0, 0.0, 0));
        addTable.setItems(data);
    }

    public void delButtonOnAction(ActionEvent actionEvent) {
        try {
            TablePosition pos = addTable.getSelectionModel().getSelectedCells().get(0);
            data.remove(pos.getRow());
            addTable.setItems(data);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("");
            alert.setContentText("删除错误，请重试");

            alert.showAndWait();

        }

    }

    public void reButtonOnAction(ActionEvent actionEvent) {
        delay.stop();
        resultArea.setText("");

        data.clear();
        data.addAll(
                new PCBData("A", 0, 4, 0),
                new PCBData("B", 1, 3, 1),
                new PCBData("C", 2, 4, 2),
                new PCBData("D", 3, 2, 2),
                new PCBData("E", 4, 4, 3)

        );
        addTable.setItems(data);
    }

    public void addQueueButtonOnAction(ActionEvent actionEvent) {
        queueData.add(new QueueData("就绪队列" + (queueData.size() + 1),
                queueData.size(),
                (int) Math.pow(2, queueData.size())));
        addQueueTable.setItems(queueData);
    }

    public void delQueueButtonOnAction(ActionEvent actionEvent) {
        try {
            queueData.remove(queueData.size() - 1);
            addQueueTable.setItems(queueData);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("");
            alert.setContentText("删除错误，请重试");

            alert.showAndWait();

        }

    }

    public void reQueueButtonOnAction(ActionEvent actionEvent) {
        delay.stop();
        resultArea.setText("");
        queueData.clear();
        queueData.addAll(
                new QueueData("就绪队列1", 0, 1),
                new QueueData("就绪队列2", 1, 2),
                new QueueData("就绪队列3", 2, 4)
        );
        addQueueTable.setItems(queueData);
    }

    public void sbmQueueButtonOnAction(ActionEvent actionEvent) {
        boolean flag = false;
        num = 0;
        delay.stop();
        delay = new Timeline();
        resultArea.setText("");

        List<PCB> list = new ArrayList<>();
        ObservableList<PCBData> observableList = addTable.getItems();
        for (int i = 0; i < observableList.size(); i++) {
            PCBData pcbData = observableList.get(i);
            list.add(new PCB(pcbData.getProcessName(),
                    Double.parseDouble(pcbData.getArriveTime()),
                    Double.parseDouble(pcbData.getServeTime()),
                    Integer.parseInt(pcbData.getPriority())));
            if (pcbData.getProcessName().contains("请"))
                flag = true;

        }


        ObservableList<QueueData> queueObservableList = addQueueTable.getItems();
        int[] timeSliceNum = new int[queueObservableList.size()];
        for (int i = 0; i < queueObservableList.size(); i++) {
            QueueData qData = queueObservableList.get(i);
            timeSliceNum[i] = Integer.parseInt(qData.getTimeSliceNum());
        }

        if (list.size() == 0 || timeSliceNum.length == 0 || flag) {
            error("请先输入完整信息");
        }

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("多级反馈队列调度算法");
        dialog.setHeaderText("计算时间片长度需要系统响应时间");
        dialog.setContentText("请输入系统响应时间（默认为进程个数）");


        final double[] _requireTime = {list.size()};
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().equals(""))
                _requireTime[0] = Double.parseDouble(result.get());
        } else
            return;

        msg = SchedulingAlgorithm.RoundRobinWithMultipleFeedback(list, _requireTime[0], timeSliceNum).split("CPU");
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), event -> {
            if (num < msg.length)
                setText(msg[num++] + '\n');
        });
        delay.setCycleCount(msg.length);
        delay.getKeyFrames().add(keyFrame);
        delay.play();
    }

    public void sbmButtonOnAction(ActionEvent actionEvent) {
        boolean flag = false;
        delay.stop();
        delay = new Timeline();
        resultArea.setText("");
        num = 0;
        List<PCB> list = new ArrayList<>();
        ObservableList<PCBData> observableList = addTable.getItems();
        for (int i = 0; i < observableList.size(); i++) {
            PCBData pcbData = observableList.get(i);
            list.add(new PCB(pcbData.getProcessName(),
                    Double.parseDouble(pcbData.getArriveTime()),
                    Double.parseDouble(pcbData.getServeTime()),
                    Integer.parseInt(pcbData.getPriority())));
            if (pcbData.getProcessName().contains("请"))
                flag = true;
        }

        if (list.size() == 0 || flag) {
            error("请先输入完整信息");
            return;
        }

        switch (title.getText()) {
            case "时间片轮转调度算法":
                RR(list);
                break;
            case "高响应比调度算法":
                msg = SchedulingAlgorithm.HRN(list).split("CPU");
                break;
            case "优先权调度算法":
                PSA(list);
                break;
        }


        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), event -> {
            if (num < msg.length)
                setText(msg[num++] + '\n');
        });
        delay.setCycleCount(msg.length);
        delay.getKeyFrames().add(keyFrame);
        delay.play();


    }

    private void RR(List<PCB> list) {
        final double[] _requireTime = {list.size()};
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("RR算法");
        dialog.setHeaderText("计算时间片长度需要系统响应时间");
        dialog.setContentText("请输入系统响应时间（默认为进程个数）");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(""))
                _requireTime[0] = Double.parseDouble(result.get());
        } else
            return;


        msg = SchedulingAlgorithm.RoundRobin(list, _requireTime[0]).split("CPU");
    }

    private void PSA(List<PCB> list) {


        try {

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("PSA算法");

            dialog.setContentText("请输入系统响应时间（默认为进程个数）");
            dialog.setHeaderText("计算时间片长度需要系统响应时间");

            final double[] _requireTime = {list.size()};
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (!result.get().equals(""))
                    _requireTime[0] = Double.parseDouble(result.get());
            } else {
                return;
            }

            switch (choiceBox.getSelectionModel().getSelectedItem()) {
                case "静态(非抢占式)":
                    msg = SchedulingAlgorithm.SPSA(list, _requireTime[0], false).split("CPU");
                    break;
                case "静态(抢占式)":
                    msg = SchedulingAlgorithm.SPSA(list, _requireTime[0], true).split("CPU");
                    break;
                case "动态(非抢占式)":
                    msg = SchedulingAlgorithm.DPSA(list, _requireTime[0], false).split("CPU");
                    break;
                case "动态(抢占式)":
                    msg = SchedulingAlgorithm.DPSA(list, _requireTime[0], true).split("CPU");
                    break;
            }
        } catch (NullPointerException e) {
            error("请选择一种PSA算法");
        }


    }

    private void RRMF(List<PCB> list) {


//        int[] time = {1,2,4,8};
//        msg = SchedulingAlgorithm.RoundRobinWithMultipleFeedback(list, 6, time).split("CPU");
    }

    public void setText(String msg) {

        resultArea.appendText(msg);
    }

    public void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void aniButtonOnAction(ActionEvent actionEvent) {
        aniTimeline = new Timeline();
        cnt = 0;
        if(msg.length <=1){
            error("请先开始调度算法");
            return;
        }
        Stage newWin = new Stage();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("../view/animation.fxml"));
            newWin.setTitle("调度算法模拟");
            newWin.setScene(new Scene(root));
            newWin.show();

        } catch (Exception e) {

        }
        List<String> finalTimes = new ArrayList<>();
        List<List<String>> aMsg = new ArrayList<>();
        List<String> letters;

        for (String string : msg) {
            if (string.contains("选中运行")) {
                letters = new ArrayList<>();

                String[] tmp = string.split("'");

                for (int i = 0; i < tmp.length; i++) {
                    if(i %2 != 0){
                        letters.add(tmp[i]);
                    }
                }

                String[] timeTmp = string.split("Time：");

                finalTimes.add( timeTmp[1].split("，")[0]);
                aMsg.add(letters);

            }
        }



        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), event -> {
            if(cnt == aMsg.size()){
                Animation.setTime("Time："+finalTimes.get(cnt-1)+"\n结束");
                Animation.setMsg(new ArrayList<>());

            }else{
                Animation.setTime("Time："+finalTimes.get(cnt));
                Animation.setMsg(aMsg.get(cnt));

                cnt++;
            }



        });
        aniTimeline.setCycleCount(aMsg.size()+1);
        aniTimeline.getKeyFrames().add(keyFrame);
        aniTimeline.play();

        newWin.setOnCloseRequest((e)->{
            aniTimeline.stop();
        });
//        Animation.play();
    }

    public static class PCBData {
        private final SimpleStringProperty processName;
        private final SimpleStringProperty arriveTime;
        private final SimpleStringProperty serveTime;
        private final SimpleStringProperty priority;


        private PCBData(String processName, double arriveTime, double serveTime, int priority) {
            this.processName = new SimpleStringProperty(processName);
            this.arriveTime = new SimpleStringProperty(Double.toString(arriveTime));
            this.serveTime = new SimpleStringProperty(Double.toString(serveTime));
            this.priority = new SimpleStringProperty(Integer.toString(priority));
        }

        public String getProcessName() {
            return processName.get();
        }

        public SimpleStringProperty processNameProperty() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName.set(processName);
        }

        public String getArriveTime() {
            return arriveTime.get();
        }

        public SimpleStringProperty arriveTimeProperty() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime.set(arriveTime);
        }

        public String getServeTime() {
            return serveTime.get();
        }

        public SimpleStringProperty serveTimeProperty() {
            return serveTime;
        }

        public void setServeTime(String serveTime) {
            this.serveTime.set(serveTime);
        }

        public String getPriority() {
            return priority.get();
        }

        public SimpleStringProperty priorityProperty() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority.set(priority);
        }
    }

    public static class QueueData {
        private final SimpleStringProperty queueName;
        private final SimpleStringProperty priority;
        private final SimpleStringProperty timeSliceNum;

        public QueueData(String queueName, int priority, int timeSliceNum) {
            this.queueName = new SimpleStringProperty(queueName);
            this.priority = new SimpleStringProperty(Integer.toString(priority));
            this.timeSliceNum = new SimpleStringProperty(Integer.toString(timeSliceNum));
        }

        public String getQueueName() {
            return queueName.get();
        }

        public SimpleStringProperty queueNameProperty() {
            return queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName.set(queueName);
        }

        public String getPriority() {
            return priority.get();
        }

        public SimpleStringProperty priorityProperty() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority.set(priority);
        }

        public String getTimeSliceNum() {
            return timeSliceNum.get();
        }

        public SimpleStringProperty timeSliceNumProperty() {
            return timeSliceNum;
        }

        public void setTimeSliceNum(String timeSliceNum) {
            this.timeSliceNum.set(timeSliceNum);
        }


    }
}


