package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

public class Controller
{
    private boolean deleteMode = false;
    private boolean addingMode = false;
    private boolean addingLine = false;
    private boolean addingArrow = false;

    private Line line = new Line();

    private boolean lineStarted = false;

    //@FXML
    //private Pane grandPane;

    @FXML
    private Pane myPane;

    @FXML
    private ImageView deleteModeId;

    @FXML
    private ImageView jointButtonId;

    @FXML
    private ImageView addLine;

    @FXML
    private ImageView addArrow;

    public void setJoint(MouseEvent event)
    {
        if(addingMode)
        {
            Circle circle = new Circle();

            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setRadius(20.0f);
            circle.setFill(Color.GREEN);

            circle.setOnMouseClicked((MouseEvent event2) -> {
                System.out.println("YOU CLICKED ON JOINT");
                getAllNodes();
                if(deleteMode)
                {
                    myPane.getChildren().remove(circle);
                }
                else if(this.lineStarted)
                {
                    setEndPositionFormJoint(circle);
                }
                else if(addingLine && !lineStarted)
                {
                    setStartPositionFromJoint(circle);
                }
            });

            myPane.getChildren().add(circle);
        }
    }

    public void setAddingMode(MouseEvent event)
    {
        if(!addingMode)
        {
            if(deleteMode)setDeleteMode(event);
            if(addingArrow)setArrowAdding(event);
            if(addingLine)setLineAdding(event);

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
            if(addingMode) setAddingMode(event);
            if(addingArrow)setArrowAdding(event);
            if(addingLine)setLineAdding(event);

            this.deleteMode = true;
            deleteModeId.setImage(new Image("/photo/circleAfterClickRed.png"));
        }
        else {
            this.deleteMode = false;
            deleteModeId.setImage(new Image("/photo/add-button-inside-black-circle.png"));
        }
    }

    public void setLineAdding(MouseEvent event)
    {
        if(!addingLine)
        {
            if(addingMode) setAddingMode(event);
            if(addingArrow)setArrowAdding(event);
            if(deleteMode) setDeleteMode(event);

            this.addingLine = true;
            addLine.setImage(new Image("/photo/lineAfterClick.png"));
        }
        else {
            this.addingLine = false;
            addLine.setImage(new Image("/photo/substract.png"));
        }
    }

    public void setArrowAdding(MouseEvent event)
    {
        if(!addingArrow)
        {
            if(addingMode) setAddingMode(event);
            if(addingLine)setLineAdding(event);
            if(deleteMode) setDeleteMode(event);

            this.addingArrow = true;
            addArrow.setImage(new Image("/photo/arrowAfterClick.png"));
        }
        else {
            this.addingArrow = false;
            addArrow.setImage(new Image("/photo/left-arrow.png"));
        }
    }

    //line from joint
    public void setStartPositionFromJoint(Circle circle)
    {
        line = new Line();
        myPane.getChildren().add(line);

        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());

        this.lineStarted = true;
    }

    public void whileMouseMoving(MouseEvent event)
    {
        if(this.lineStarted)
        {
            line.setEndX(event.getX()-5);
            line.setEndY(event.getY()-5);
            line.setVisible(true);
        }
    }

    public void setEndPositionFormJoint(Circle circle)
    {
        if(this.lineStarted)
        {
            line.setEndX(circle.getCenterX());
            line.setEndY(circle.getCenterY());

            this.lineStarted = false;
        }
    }

    public void getAllNodes()
    {
        for(Node n:myPane.getChildren())
        {
            //Circle[centerX=362.0, centerY=367.0, radius=20.0, fill=0x008000ff, stroke=0x000000ff, strokeWidth=2.0]
            //Line[startX=362.0, startY=367.0, endX=233.0, endY=189.0, stroke=0x000000ff, strokeWidth=1.0]
        }
    }


    /*public void setStartPosition(MouseEvent event)
    {
        if(addingLine)
        {
            line = new Line();
            line.setVisible(false);
            line.setStartX(event.getX());
            line.setStartY(event.getY());

            myPane.getChildren().add(line);
        }
    }*/

    /*public void refreshWhileDragged(MouseEvent event)
    {
        if(addingLine && (line.getEndX()!= 0))
        {
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            line.setVisible(true);
        }
    }*/

    /*public void setEndPosition(MouseEvent event)
    {
        if(addingLine)
        {
            System.out.println("END : ");
            System.out.println("END X: " + String.valueOf(event.getX()));
            System.out.println("END Y: " + String.valueOf(event.getX()));

            line.setEndX(event.getX());
            line.setEndY(event.getY());
        }
    }*/
}