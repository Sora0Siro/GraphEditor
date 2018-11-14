package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

public class Controller
{
    private boolean deleteMode = false;
    private boolean addingMode = false;

    @FXML
    private Pane myPane;

    @FXML
    private ImageView deleteModeId;

    @FXML
    private ImageView jointButtonId;

    public void setJoint(MouseEvent event)
    {
        if(addingMode)
        {
            Circle circle = new Circle();

            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            circle.setRadius(20.0f);
            circle.setFill(Color.GREEN);

            circle.setOnMouseClicked((MouseEvent event2) -> {
                if(this.deleteMode) myPane.getChildren().remove(circle);
            });

            myPane.getChildren().add(circle);
        }
    }

    public void setAddingMode(MouseEvent event)
    {
        if(!addingMode)
        {
            if(deleteMode)setDeleteMode(event);

            this.addingMode = true;
            jointButtonId.setImage(new Image("/photo/circleAfterClickGreen.png"));

        }
        else {
            this.addingMode = false;
            jointButtonId.setImage(new Image("/photo/add-button-inside-black-circle.png"));
        }
    }

    public void setDeleteMode(MouseEvent event)
    {
        if(!deleteMode)
        {
            if(addingMode)setAddingMode(event);

            this.deleteMode = true;
            deleteModeId.setImage(new Image("/photo/circleAfterClickRed.png"));
        }
        else {
            this.deleteMode = false;
            deleteModeId.setImage(new Image("/photo/add-button-inside-black-circle.png"));
        }
    }
}