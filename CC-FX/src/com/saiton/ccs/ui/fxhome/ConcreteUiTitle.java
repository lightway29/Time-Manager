package com.saiton.ccs.ui.fxhome;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class ConcreteUiTitle extends HBox implements UiTitle{

    private final ImageView imgA;
    public ConcreteUiTitle(){
        super(); // init hbox
        setMinSize(FxHome.LEFT_MIN_WIDTH, 90.0);
        setAlignment(Pos.CENTER);
        Image img = new Image(this.getClass().getResourceAsStream("/com/saiton/ccs/res/logo.png"));

        imgA = new ImageView(img);

        img = new Image(this.getClass().getResourceAsStream("/com/saiton/ccs/res/SaitonlogoTitleNew.png"));
        ImageView imgV = new ImageView(img);
        //getChildren().addAll(imgA,imgV);
    }
    @Override
    public Node getUiTitle() {
        return  this;
    }

    @Override
    public void animate() {
        createRotator(imgA).play();
    }
    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.seconds(2), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0.0);
        rotator.setToAngle(45.0);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setAutoReverse(true);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
