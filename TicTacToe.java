package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe extends Application {

    public static final String CIRCLE = "circle";
    public static final String EMPTY = "empty";
    public static final String CROSS = "cross";
    private final Image imageBack = new Image("file/grey1Sample.png");
    private final Image crossImage = new Image("file/cross tile.png", 210, 210, true, true);
    private final Image circleImage = new Image("file/ogreen.png", 210, 210, true, true);
    private final Image emptyImage = new Image("file/empty tile.png", 210, 210, true, true);
    private final Button start = new Button("START");
    private final Button reset = new Button("CLEAR ALL");
    private final Label status = new Label();
    private final Label player = new Label();
    private final List<Tile> tiles = new ArrayList<>();
    private final ResultCalculator resultCalculator = new ResultCalculator();


    public void drawBoard(GridPane board) {

        for (int i = 0; i < 3; i++) {
            Tile tile = createEmptyTile();
            tiles.add(i, tile);
            board.add(tile.getImageView(), i, 0);
        }
        for (int i = 3; i < 6; i++) {
            Tile tile = createEmptyTile();
            tiles.add(i, tile);
            board.add(tile.getImageView(), i - 3, 1);
        }
        for (int i = 6; i < 9; i++) {
            Tile tile = createEmptyTile();
            tiles.add(i, tile);
            board.add(tile.getImageView(), i - 6, 2);
        }
    }

    public boolean drawCross(List<Tile> tiles,int index) {
        Tile tile = tiles.get(index);
        tile.getImageView().setImage(crossImage);
        tile.setCurrentValue(CROSS);
        System.out.println("X" + index);
        return true;
    }


    public boolean drawCircle(List<Tile> tiles) {
        Random generator = new Random();
        int index;

        do index = generator.nextInt(8);
        while (tiles.get(index).getImageView().getImage().equals(crossImage) ||
                tiles.get(index).getImageView().getImage().equals(circleImage));
        {
            Tile tile = tiles.get(index);
            tile.getImageView().setImage(circleImage);
            tile.setCurrentValue(CIRCLE);

            System.out.println("O" + index);
        }

        result();
        return true;
    }

    public void drawCircleDelay() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(900),
                ae -> drawCircle(tiles)));
        timeline.play();
    }

    public boolean result() {
        String winner = resultCalculator.calculate(tiles);
        if (winner != null) {
            status.setText(winner + " won!");
            status.setTextFill(Color.web("#009900"));
            return true;
        }
        return false;
    }

    private Tile createEmptyTile() {
        return new Tile(new ImageView(emptyImage), EMPTY);
    }

    public void clearAll (List<Tile> tiles) {
        for (int i = 0; i < 9; i++) {
            Image image = tiles.get(i).getImageView().getImage();
            if (image.equals(crossImage) ||
                    image.equals(circleImage)) {
                Tile tile = tiles.get(i);
                tile.getImageView().setImage(emptyImage);
                tile.setCurrentValue(EMPTY);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(100, 100, 100, 100));
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setBackground(background);

        GridPane board = new GridPane();
        grid.add(board, 0, 0, 2, 3);
        board.setPadding(new Insets(100, 100, 100, 100));
        board.setHgap(10);
        board.setVgap(10);

        GridPane buttonsPlan = new GridPane();
        grid.add(buttonsPlan, 4, 0);
        buttonsPlan.setPadding(new Insets(100, 50, 50, 20));
        grid.setAlignment(Pos.TOP_CENTER);
        buttonsPlan.setHgap(40);
        buttonsPlan.setVgap(40);

        start.setPrefSize(140, 70);
        start.setStyle("-fx-background-color: #8C7853");
        reset.setPrefSize(140, 70);
        reset.setStyle("-fx-background-color: #8C7853");

        status.setText("Status");
        status.setFont(new Font("Arial", 100));
        status.setAlignment(Pos.TOP_CENTER);
        status.setTextFill(Color.web("#009900"));


        player.setText("");
        player.setAlignment(Pos.CENTER);
        player.setFont(new Font("", 70));
        player.setTextFill(Color.web("BLACK"));

        buttonsPlan.add(status, 2, 1, 5, 2);
        buttonsPlan.add(reset, 2, 6);
        buttonsPlan.add(start, 2, 7);
        buttonsPlan.add(player, 2, 10, 5, 2);

        DropShadow shadow = new DropShadow(120, Color.BLUE);
        board.setEffect(shadow);
        drawBoard(board);

        for (int index = 0; index < 9; index++) {
            int n = index;
            tiles.get(index).getImageView().setOnMouseClicked(event -> {
                if (tiles.get(n).getImageView().getImage().equals(emptyImage) && (!result())) {
                    if (!tiles.get(n).getImageView().getImage().equals(crossImage)
                            && (!tiles.get(n).getImageView().getImage().equals(circleImage))) {
                        drawCross(tiles, n);
                        drawCircleDelay();
                        result();
                    }
                }
            });
        }

        reset.setOnAction(event -> {
            clearAll(tiles);
            player.setText("");
            status.setText("New game");
            status.setTextFill(Color.web("#009900"));
            player.setText("Start cross");
        });

        start.setOnMouseClicked(e -> {
            clearAll(tiles);
            drawCircle(tiles);
            player.setText("Start circle ");
        });

        Scene scene = new Scene(grid, 1600, 900, Color.BEIGE);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}