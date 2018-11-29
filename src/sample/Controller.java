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

import java.util.ArrayList;
import java.util.List;

public class Controller
{
    private boolean deleteMode = false;
    private boolean addingMode = false;
    private boolean addingLine = false;
    private boolean addingArrow = false;

    private Line line = new Line();

    private boolean lineStarted = false;

    private List<Node> joints;
    private List<Node> lines;

    List<Node> linesToDrag;
    List<Boolean> startPoint;

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

    //Events
    public void setJoint(MouseEvent event)
    {
        //create lists for nodes
        if(joints == null)
        {
            joints = new ArrayList<>();
            lines = new ArrayList<>();
        }
        if(addingMode)
        {
            Circle circle = new Circle();

            createCircle(event,circle);

            circle.setOnMouseClicked((MouseEvent event2) ->
            {
                System.out.println("YOU CLICKED ON JOINT");
                //getAllNodesInfo();
                if(deleteMode)
                {
                    checkIfJointContainsLine(circle);
                    myPane.getChildren().remove(circle);
                    joints.remove(circle);
                }
                else if(lineStarted)
                {
                    setEndPositionFormJoint(circle);
                }
                else if(addingLine && !lineStarted)
                {
                    setStartPositionFromJoint(circle);
                }
            });

            circle.setOnMouseDragged((MouseEvent event3)->
            {
                if(allModesIsOff())
                {
                    whatLinesToDrag(circle,event3);
                }
            });

            myPane.getChildren().add(circle);
            //myPane.getChildren().add(joints.size(),circle);
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

    public void whileMouseMoving(MouseEvent event)
    {
        if(this.lineStarted)
        {
            line.setEndX(event.getX()-5);
            line.setEndY(event.getY()-5);
            line.setVisible(true);
        }
    }

    //methods
    public void setStartPositionFromJoint(Circle circle)
    {
        line = new Line();
        myPane.getChildren().add(line);

        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());

        this.lineStarted = true;
    }

    public void setEndPositionFormJoint(Circle circle)
    {
        if(this.lineStarted)
        {
            line.setEndX((getMiddleValue(circle.getBoundsInParent().getMinX(),circle.getBoundsInParent().getMaxX())));
            line.setEndY((getMiddleValue(circle.getBoundsInParent().getMinY(),circle.getBoundsInParent().getMaxY())));

            line.setId(String.valueOf("line "+ lines.size()));
            lines.add(line);
            this.lineStarted = false;
        }
    }

    public void getAllNodesInfo()
    {
        System.out.println("Nodes in Pane");
        for(Node n:myPane.getChildren())
        {
            System.out.println(n.getId());

            //Circle[centerX=362.0, centerY=367.0, radius=20.0, fill=0x008000ff, stroke=0x000000ff, strokeWidth=2.0]
            //Line[startX=362.0, startY=367.0, endX=233.0, endY=189.0, stroke=0x000000ff, strokeWidth=1.0]
        }

        System.out.println("Nodes in joints list");
        for(Node n: joints)
        {
            System.out.println(n.getId());
        }

        System.out.println("Nodes in lines list");
        for(Node n: lines)
        {
            System.out.println(n.getId());
        }
    }

    private void createCircle(MouseEvent event,Circle circle)
    {
        circle.setCenterX(event.getX());
        circle.setCenterY(event.getY());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setRadius(20.0f);
        circle.setFill(Color.GREEN);

        circle.setId(String.valueOf("joint " + joints.size()));
        joints.add(circle);
    }

    private void checkIfJointContainsLine(Circle circle)
    {
        List<Node> linesForDelete = new ArrayList<>();

        for(Node line:lines)
        {
            if(line.contains(circle.getCenterX(),circle.getCenterY()))
            {
                myPane.getChildren().remove(line);
                linesForDelete.add(line);
            }
        }

        for(Node l: linesForDelete)
        {
            lines.remove(l);
        }
        linesForDelete.clear();
    }

    private double getMiddleValue(double first,double second)
    {
        return ((first+second)/2);
    }

    private void whatLinesToDrag(Circle circle,MouseEvent mouseEvent)
    {
        linesToDrag = new ArrayList<>();
        startPoint = new ArrayList<>();//false start, true end

        for(Node l: lines)
        {
            if(l.contains(circle.getCenterX(),circle.getCenterY()))
            {
                if(startOrEndPointInLine(l,circle) == 0)
                {
                    linesToDrag.add(l);
                    startPoint.add(false);
                }
                else if(startOrEndPointInLine(l,circle) == 1)
                {
                    linesToDrag.add(l);
                    startPoint.add(true);
                }
                else
                {
                    System.out.println("Couldn't find right point");
                }
            }
        }

        /*for(int i =0;i<linesToDrag.size();i++)
        {
            System.out.println(linesToDrag.get(i).getId() +"|"+ linesToDrag.get(i).toString() + " Start?: " + startPoint.get(i).toString());
        }*/

        dragEverything(circle,mouseEvent);
    }

    private int startOrEndPointInLine(Node tmpLine,Circle circle)
    {
        System.out.println(tmpLine.toString().trim());

        double startX,endX,startY,endY;

        String[] values = tmpLine.toString().split("=");

        for(int i =0;i<values.length;i++)
        {
            values[i] = values[i].replaceAll("[^\\d.]", "");
        }
        startX = Double.parseDouble(values[2]);
        startY =  Double.parseDouble(values[3]);
        endX =Double.parseDouble(values[4]);
        endY =  Double.parseDouble(values[5]);

        if (endX == Double.valueOf(circle.getCenterX()) && endY == Double.valueOf(circle.getCenterY()))
        {
            return 0;
        }
        else if (startX == Double.valueOf(circle.getCenterX()) && startY == Double.valueOf(circle.getCenterY()))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    private void dragEverything(Circle circle,MouseEvent mouseEvent)
    {
        System.out.println("PANE SHIT");
        for(int i=0;i<linesToDrag.size();i++)
        {
            circle.setCenterX(mouseEvent.getX());
            circle.setCenterY(mouseEvent.getY());
            if(startPoint.get(i))
            {
                for (Node currentNode : myPane.lookupAll("Line"))
                {
                    if(currentNode.getId() == linesToDrag.get(i).getId())
                    {
                        ((Line) currentNode).setStartX(mouseEvent.getX());
                        ((Line) currentNode).setStartY(mouseEvent.getY());
                        circle.setCenterX(mouseEvent.getX());
                        circle.setCenterY(mouseEvent.getY());
                    }
                }
            }
            else if(!startPoint.get(i))
            {
                for (Node currentNode : myPane.lookupAll("Line"))
                {
                    if(currentNode.getId() == linesToDrag.get(i).getId())
                    {
                        ((Line) currentNode).setEndX(mouseEvent.getX());
                        ((Line) currentNode).setEndY(mouseEvent.getY());
                        circle.setCenterX(mouseEvent.getX());
                        circle.setCenterY(mouseEvent.getY());
                    }
                }
            }
        }
    }

    private boolean allModesIsOff()
    {
        if (!deleteMode && !addingMode && !addingLine && !addingArrow)
        {
            return true;
        }
        else {
            return false;
        }
    }
}