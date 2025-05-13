package com.example.demogame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView bg1, bg2, player, enemy;

    @FXML
    private Label pauseLabel, LabelLose;

    private final int BG_WIDTH = 711;

    private ParallelTransition parallelTransition;
    private TranslateTransition enemyTransition;

    public static boolean jump = false;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean isPause = false;

    private int playerSpeed = 3, jumpDownSpeed = 5;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (jump && player.getLayoutY() > 90f) {
                player.setLayoutY(player.getLayoutY() - playerSpeed);
            } else if (player.getLayoutY() <= 220f) {
                jump = false;
                player.setLayoutY(player.getLayoutY() + jumpDownSpeed);
            }
            if (right && player.getLayoutX()<450f) {
                player.setLayoutX(player.getLayoutX() + playerSpeed);
            }
            if (left && player.getLayoutX()>10f) {
                player.setLayoutX(player.getLayoutX() - playerSpeed);
            }
            if (isPause && !pauseLabel.isVisible()){
                playerSpeed = 0;
                jumpDownSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                pauseLabel.setVisible(true);
            }
            else if (!isPause && pauseLabel.isVisible()) {
                pauseLabel.setVisible(false);
                playerSpeed = 3;
                jumpDownSpeed = 5;
                parallelTransition.play();
                enemyTransition.play();
            }


            if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                LabelLose.setVisible(true);
                playerSpeed = 0;
                jumpDownSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();

            }

        }
    };

    @FXML
    void initialize() {

        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTransition.setFromX(0);
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);

        enemyTransition = new TranslateTransition(Duration.millis(3500), enemy);
        enemyTransition.setFromX(0);
        enemyTransition.setToX((BG_WIDTH * -1) - 100);
        enemyTransition.setInterpolator(Interpolator.LINEAR);
        enemyTransition.setCycleCount(Animation.INDEFINITE);
        enemyTransition.play();

        parallelTransition = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();
    }

}