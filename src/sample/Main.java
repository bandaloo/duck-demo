package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import javafx.scene.paint.Color;

public class Main extends Application {
    public static final double MAX_SIZE = 300.0;
    public static final double MIN_SIZE = 20.0;
    Image duck, smile, sad;
    ImageView imageView;
    ImageView curImageView;
    Rectangle2D rect;
    BorderPane border;
    Scene root;
    VBox sidebar;
    Pane iconPane;
    Label sideLabel = new Label("Duck Controls");
    Label editLabel = new Label("Edit Controls");
    Button buttonDuck, buttonSmile, buttonSad, buttonBigger, buttonSmaller;
    double clickX, clickY;

    @Override
    public void start(Stage primaryStage) throws Exception{
        duck = new Image("icons/duck.png");
        smile = new Image("icons/smile.png");
        sad = new Image("icons/sad.png");
        buttonDuck = new Button("duck");
        buttonSmile = new Button("smile");
        buttonSad = new Button("sad");
        buttonDuck.setOnAction(event -> addIcon(event));
        buttonSmile.setOnAction(event -> addIcon(event));
        buttonSad.setOnAction(event -> addIcon(event));
        buttonDuck.setStyle("-fx-base: #ffff7c;");
        buttonSmile.setStyle("-fx-base: #ff729a;");
        buttonSad.setStyle("-fx-base: #72a1ff;");
        buttonBigger = new Button("make bigger");
        buttonSmaller = new Button("make smaller");
        buttonBigger.setOnAction(event -> sizeIcon(event));
        buttonSmaller.setOnAction(event -> sizeIcon(event));
        rect = new Rectangle2D(0, 0, 400, 400);
        border = new BorderPane();
        sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #bfd7ff;-fx-padding: 10px;");
        iconPane = new Pane();
        border.setCenter(iconPane);
        border.setLeft(sidebar);
        sidebar.setSpacing(3.0);
        sidebar.getChildren().addAll(sideLabel, buttonDuck, buttonSmile, buttonSad, editLabel, buttonBigger, buttonSmaller);
        root = new Scene(border, 640, 480);

        primaryStage.setTitle("Duck Viewer");
        primaryStage.setScene(root);
        primaryStage.show();
    }

    public void setClickPosition(MouseEvent event) {
        if (curImageView != null) {
            curImageView.setEffect(null);
        }
        curImageView = (ImageView) event.getSource();
        curImageView.setEffect(new DropShadow(20, Color.GREEN));
        clickX = event.getX();
        clickY = event.getY();
    }

    public void setDragPosition(MouseEvent event) {
        curImageView.setTranslateX(event.getSceneX() - iconPane.getLayoutX() - iconPane.getTranslateX() - clickX);
        curImageView.setTranslateY(event.getSceneY() - iconPane.getLayoutY() - iconPane.getTranslateY() - clickY);
    }

    public void addIcon(ActionEvent event) {
        ImageView imageView = new ImageView();
        imageView.setOnMousePressed(e -> setClickPosition(e));
        imageView.setOnMouseDragged(e -> setDragPosition(e));
        if (event.getSource() == buttonDuck) {
            imageView.setImage(duck);
        }
        else if (event.getSource() == buttonSmile) {
            imageView.setImage(smile);
        }
        else if (event.getSource() == buttonSad) {
            imageView.setImage(sad);
        }
        imageView.setFitWidth(imageView.getImage().getWidth());
        imageView.setFitHeight(imageView.getImage().getHeight());
        iconPane.getChildren().add(imageView);
    }

    public void sizeIcon(ActionEvent event) {
        double scalar = (event.getSource() == buttonBigger) ? 1.0 : -1.0;
        if (curImageView != null) {
            double fitWidth = curImageView.getFitWidth() + 10 * scalar;
            double fitHeight = curImageView.getFitHeight() + 10 * scalar;
            if (fitWidth <= MAX_SIZE && fitHeight >= MIN_SIZE) {
                curImageView.setFitWidth(fitWidth);
                curImageView.setFitHeight(fitHeight);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
