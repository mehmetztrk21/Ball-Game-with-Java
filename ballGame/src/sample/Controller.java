package sample;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;

public class Controller extends Application {
    public static double yon = 3;
    public static double x = 3;
    public static double y = 3;
    public static int adet = 0;
    public static int can = 0;
    public static int control = 0;
    public static Label label = new Label();
    public static Label label2 = new Label();
    public static Pane pane = new Pane();
    public static Random r=new Random();

    String now2 = LocalDateTime.now().toString();


    @Override
    public void start(Stage primaryStage) {

        pane.setStyle("-fx-background-color:#FFCDB2;");

        Scene scene = new Scene(pane, 800, 800);


        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        ImageView imageView = new ImageView("lab.png");
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        pane.getChildren().add(imageView);

        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        label.setStyle("-fx-border-color:gray; -fx-background-color: black;");
        label.setTextFill(Color.TOMATO);
        label.setLayoutX(330);

        label.setMinWidth(100);

        Label label3=new Label();
        label3.setStyle("-fx-background-color: black;");
        label3.setMinWidth(800);
        label3.setMinHeight(800);
        pane.getChildren().add(label3);
        pane.getChildren().add(label);


        FadeTransition ft = new FadeTransition(Duration.millis(3000), label3);
        ft.setFromValue(0.7);
        ft.setToValue(0.3);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play(); // Start animation

        Circle circle = new Circle(14, Color.TOMATO);
        circle.relocate(100, 100);

        final TranslateTransition transition = new TranslateTransition(Duration.seconds(0.01), circle);
        pane.getChildren().addAll(circle);

        circlerun(circle);
        moveCircleOnKeyPress(scene, circle);
        moveCircleOnMousePress(scene, circle, transition);


    }

    public static void main(String[] args) {
        launch(args);
    }

    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isControlDown()) {
                    circle.relocate(event.getSceneX(), event.getSceneY());

                } else {
                    transition.setToX(event.getSceneX() - circle.getCenterX() + 10);
                    transition.setToY(event.getSceneY() - circle.getCenterY() + 10);
                    transition.playFromStart();
                }
            }
        });
    }

    private void moveCircleOnKeyPress(Scene scene, Circle circle) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                switch (event.getCode()) {
                    case UP:
                        circle.setLayoutY(circle.getLayoutY() - yon);
                        break;
                    case RIGHT:
                        circle.setLayoutX(circle.getLayoutX() + yon);
                        break;
                    case DOWN:
                        circle.setLayoutY(circle.getLayoutY() + yon);
                        break;
                    case LEFT:
                        circle.setLayoutX(circle.getLayoutX() - yon);
                        break;
                }
                Bounds bounds = pane.getBoundsInLocal();
                boolean atRightBorder = circle.getLayoutX() >= (bounds.getMaxX() - (circle.getRadius()));
                boolean atLeftBorder = circle.getLayoutX() <= (bounds.getMinX() + (circle.getRadius()));
                boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - (circle.getRadius()));
                boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + (circle.getRadius()));

                if (atRightBorder || atLeftBorder ||  atTopBorder ||atBottomBorder) {
                    circlerun(circle);
                    atRightBorder = false;
                    atLeftBorder = false;
                    atBottomBorder = false;
                    atTopBorder = false;
                }
            }
        });

    }

    private void circlerun(Circle circle) {
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent t) {
                circle.setLayoutX(circle.getLayoutX() + x);
                circle.setLayoutY(circle.getLayoutY() + y);

                Bounds bounds = pane.getBoundsInLocal();
                boolean atRightBorder = circle.getLayoutX() >= (bounds.getMaxX() - (circle.getRadius()));
                boolean atLeftBorder = circle.getLayoutX() <= (bounds.getMinX() + (circle.getRadius()));
                boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - (circle.getRadius()));
                boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + (circle.getRadius()));
                if ((atRightBorder || atLeftBorder) || (atBottomBorder || atTopBorder)) {
                    adet += 1;
                }
                if (atRightBorder) {
                    atRightBorder = false;
                    x += -2;
                } else if (atLeftBorder) {
                    atLeftBorder = false;
                    x += 2;
                } else if (atBottomBorder) {
                    atBottomBorder = false;
                    y += -2;
                } else if (atTopBorder) {
                    atTopBorder = false;
                    y += 2;
                } else {
                }
                can = adet / 3;
                can();
            }
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

    }

    private void can() {
        label.setText(("***    Can: " + (5 - can)+"   ***"));
        if (5 - can == 0) {
            if (control == 0) {
                String now = LocalDateTime.now().toString();
                String[] a = now.split(":");
                double finish = Float.parseFloat(a[2]);
                String[] x = now2.split(":");
                double start = Float.parseFloat(x[2]);
                double f_d=Float.parseFloat(a[1]);
                double s_d=Float.parseFloat(x[1]);
                double k=(f_d-s_d)*60;
                gameOver(k-(start - finish));
                control += 1;
            }
        }
        }


    private void gameOver(final double score) {

        label2.setText("                Canın kalmadı.  " + (int) (Math.abs(Math.floor(score))) + " saniye dayanabildin.       \n        ***********************");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        label2.setStyle("-fx-border-color:black; -fx-background-color: black;");
        label2.setTextAlignment(TextAlignment.CENTER);
        label2.setMinWidth(800);
        label2.setMinHeight(800);
        pane.getChildren().add(label2);
        label2.setTextFill(Color.TOMATO);

        ImageView imageView = new ImageView("img.jpg");
        pane.getChildren().add(imageView);
        imageView.setFitWidth(1000);
        for(int i=10;i<4000;i+=10){
            Circle circle = new Circle(Math.sqrt(Math.sqrt(i+8000)), Color.TOMATO);
            circle.relocate(r.nextInt(i), r.nextInt(i));
            pane.getChildren().addAll(circle);
            circlerun(circle);

        }
        // Create a path transition
        PathTransition pt = new PathTransition(Duration.millis(10000),
                new Line(400, 0, 400, 1000), imageView);
        pt.setAutoReverse(true);
        pt.setCycleCount(10);
        pt.play(); // Start animation








    }
}