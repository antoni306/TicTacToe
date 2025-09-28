import javax.swing.*;
import java.awt.*;



public class TicTacToePanel extends JPanel {
    private Square[][] squares=new Square[3][3];
    private GenerateMap parentPanel;
    private Point coordinates;
    private int win=0;
    public int getWin(){
        return win;
    }
    public void setWin(int win){
        this.win=win;
    }
    public Point getCoordinates(){
        return coordinates;
    }
    public int checkWinner(){

        for (int i = 0; i < 3; i++) {
            if (squares[i][0].getOwner() != 0 && squares[i][0].getOwner() == squares[i][1].getOwner() && squares[i][1].getOwner() == squares[i][2].getOwner()) {
                return squares[i][0].getOwner();
            }
        }
        for (int j = 0; j < 3; j++) {
            if (squares[0][j].getOwner() != 0 && squares[0][j].getOwner() == squares[1][j].getOwner() && squares[1][j].getOwner() == squares[2][j].getOwner()) {
                return squares[0][j].getOwner();
            }
        }
        if (squares[0][0].getOwner() != 0 && squares[0][0].getOwner() == squares[1][1].getOwner() && squares[1][1].getOwner() == squares[2][2].getOwner()) {
            return squares[0][0].getOwner();
        }
        if (squares[0][2].getOwner() != 0 && squares[0][2].getOwner() == squares[1][1].getOwner() && squares[1][1].getOwner() == squares[2][0].getOwner()) {
            return squares[0][2].getOwner();
        }
        return 0;
    }
    public Square[][] getSquares(){
        return squares;
    }
    public void setNewSource(GenerateMap newSource){
        this.parentPanel=newSource;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(win==1){
            this.setBackground(Color.RED);

            for(int i=0;i< 300;i+=5){
                g.drawString("X",i,i+5);
            }
            for(int j=300;j>=0;j-=5){
                g.drawString("X",j,300-j);
            }
        }else if(win==2){
            this.setBackground(Color.BLUE);
            g.drawRoundRect(60,60,180,180,40,40);
        }
    }
    public TicTacToePanel(Point coordinates){
        this.coordinates=coordinates;

        this.setBorder(BorderFactory.createLineBorder(Color.orange));
        GridLayout gridLayout=new GridLayout(3,3);
        this.setLayout(gridLayout);
        this.setPreferredSize(new Dimension(300,300));
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Square square=new Square(new Point(i,j));
                square.setParentPanel(this);
                this.add(square);
                squares[i][j]=square;
            }
        }
    }
    public void childrenPanelClicked(){
        int winner=checkWinner();
        if(winner!=0){
            this.win=winner;
            this.removeAll();
            this.setEnabled(false);
            this.repaint();
        }
        parentPanel.selectNext();

    }


}

