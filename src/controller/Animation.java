package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Animation implements Initializable {

    public HBox hbox;
    public AnchorPane pane;
    public AnchorPane pane2;
    public StackPane stackPane;

    public static ObservableList<String> msg = FXCollections.observableArrayList();
    public static String times = "";
    public List<String> Msg = new ArrayList<>();
    public Button playBtn;
    public Button pauseBtn;
    public Text timeText;


    public static void setMsg(List<String> list) {
        msg.clear();
        msg.addAll(list);
    }

    public static  void setTime(String time){
        times = time;
    }

    public void play() {
        try {
            hbox.getChildren().clear();
            timeText.setText(times);
            Rectangle rec1 = new Rectangle(50, 50, Color.WHITE);
            rec1.setStrokeWidth(1);
            rec1.setStroke(Color.BLACK);
            rec1.setStrokeType(StrokeType.CENTERED);

            stackPane.getChildren().add(rec1);
            stackPane.getChildren().add(new Text(msg.get(0)));

            for (int i = 1; i < msg.size(); i++) {
                StackPane stackPane1 = new StackPane();
                stackPane1.setShape(new Rectangle(50, 50));
                Rectangle rec = new Rectangle(50, 50, Color.WHITE);
                rec.setStrokeWidth(1);
                rec.setStroke(Color.BLACK);
                rec.setStrokeType(StrokeType.CENTERED);
                Text text = new Text(msg.get(i));
                text.setFont(new Font(14));

                stackPane1.getChildren().add(rec);
                stackPane1.getChildren().add(text);
                hbox.getChildren().add(stackPane1);


            }

        } catch (Exception e) {

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        msg.addListener((ListChangeListener<String>) c -> {

            play();


        });
    }

    public void playBtnOnAction(ActionEvent actionEvent) {
        Controller.aniTimeline.play();
    }

    public void pauseBtnOnAction(ActionEvent actionEvent) {
        Controller.aniTimeline.pause();
    }

    public void refreshBtnOnAction(ActionEvent actionEvent) {
        Controller.aniTimeline.stop();
        Controller.aniTimeline.play();
    }
}
