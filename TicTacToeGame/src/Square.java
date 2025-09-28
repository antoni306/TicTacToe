import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Square extends JButton {
    private TicTacToePanel parentPanel;
    private static Point nextSquare=new Point(0,0);
    private static Point previousSquare=new Point(0,0);
    private Point coordinates;
    private static int turn=1;
    private int owner=0;

    public static Point getPreviousSquare(){
        return previousSquare;
    }
    public static int getTurn(){
        return turn;
    }
    public void setOwner(int owner){
        this.owner=owner;
    }
    public static void setNextSquare(Point newSquare){
        nextSquare=newSquare;
    }
    public static void setPreviousSquare(Point newSquare){
        previousSquare=newSquare;
    }
    public static void setTurn(int Turn){
        turn=Turn;
    }
    public void setParentPanel(TicTacToePanel parentPanel){
        this.parentPanel=parentPanel;
    }
    public int getOwner(){
        return owner;
    }
    public static Point getNextSquare(){
        return nextSquare;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(owner==1){
            for(int i=0;i< 100;i+=5){
                g.drawString("X",i,i+5);
                this.setBackground(Color.RED);
            }
            for(int j=100;j>=0;j-=5){
                g.drawString("X",j,100-j);
            }
        }else if(owner==2){
            g.drawRoundRect(20,20,60,60,40,40);
            this.setBackground(Color.BLUE);
        }

    }
    public Square(Point coordinates) {
        this.coordinates=coordinates;

        this.setBackground(Color.CYAN);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(100, 100));

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MouseClickedAction();
            }
        });

    }
    public void MouseClickedAction(){
        if(this.owner==0){
            if (turn == 1) {
                this.owner=1;
                turn=2;
            }else {
                this.owner=2;
                turn=1;
            }
            nextSquare=coordinates;
            this.setEnabled(false);
            setPreviousSquare(parentPanel.getCoordinates());
            //Square.smallSquareCoordinates=this.coordinates;
            parentPanel.childrenPanelClicked();
        }

    }
}
