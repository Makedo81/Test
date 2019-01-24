package game;

import javafx.scene.image.ImageView;

public class Tile {

    private String currentValue;
    private ImageView imageView;

    public Tile(ImageView imageView,String currentValue) {

        this.imageView = imageView;
        this.currentValue= currentValue;
    }

    public ImageView getImageView() {

        return imageView;
    }

    public Tile(String currentValue) {
        this.currentValue = currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

}
