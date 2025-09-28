import javax.swing.*;
import java.awt.*;
public class GenerateMap extends JPanel {
    private TicTacToePanel[][] ticTacToePanels=new TicTacToePanel[3][3];
    public TicTacToePanel[][] getTicTacToePanels(){
        return ticTacToePanels;
    }
    private JPanel statsReference;
    public void setStatsREference(JPanel statsReference){
        this.statsReference=statsReference;
    }
    public int GameOver(){
        statsReference.repaint();
        if(checkWinner()!=0){
            if(checkWinner()==1){
                statsReference.setBackground(Color.RED);
            }else if(checkWinner()==2){
                statsReference.setBackground(Color.BLUE);
            }
            EndGame();
            statsReference.repaint();
            return 1;
        }else {
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(ticTacToePanels[i][j].getWin()!=0)
                        continue;
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            if(ticTacToePanels[i][j].getSquares()[k][l].getOwner()==0){
                                return 0;
                            }

                        }
                    }
                }
            }
            statsReference.setLayout(new GridLayout(2,1));
            JPanel blue=new JPanel();
            blue.setBackground(Color.BLUE);
            JPanel red=new JPanel();
            red.setBackground(Color.red);
            statsReference.add(blue);
            statsReference.add(red);
            statsReference.revalidate();
            EndGame();

        }
        return 0;
    }
    public void EndGame(){
        for(int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                for(int j=0;j<3;j++){
                    for(int l=0;l<3;l++){
                        ticTacToePanels[i][k].getSquares()[j][l].setEnabled(false);
                    }
                }
            }
        }
    }
    public void selectNext(){
        if(GameOver()==1){
            return;
        }
        TicTacToePanel tnext=ticTacToePanels[Square.getNextSquare().x][Square.getNextSquare().y];

        boolean tnextAvailable=false;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(tnext.getSquares()[i][j].getOwner()==0){
                    tnextAvailable=true;
                    break;
                }
            }
            if(tnextAvailable){
                break;
            }
        }
        boolean paintAllGreen=false;
        if(tnext.getWin()!=0||!tnextAvailable){
            paintAllGreen=true;
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                TicTacToePanel gamePanel=ticTacToePanels[i][j];
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        if(gamePanel.getWin()==0){
                            if(paintAllGreen){
                                if(gamePanel.getSquares()[k][l].getOwner()==0){
                                    gamePanel.getSquares()[k][l].setEnabled(true);
                                    gamePanel.getSquares()[k][l].setBackground(Color.GREEN);
                                    gamePanel.getSquares()[k][l].repaint();
                                }
                            } else if(tnext.getCoordinates()==ticTacToePanels[i][j].getCoordinates()){

                                if(gamePanel.getSquares()[k][l].getOwner()==0){
                                    gamePanel.getSquares()[k][l].setEnabled(true);
                                    gamePanel.getSquares()[k][l].setBackground(Color.GREEN);
                                }
                            }else {
                                gamePanel.getSquares()[k][l].setEnabled(false);
                                gamePanel.getSquares()[k][l].setBackground(Color.CYAN);
                            }
                        }

                    }
                }

            }
        }
    }
    public int checkWinner(){
        for (int i = 0; i < 3; i++) {
            if (ticTacToePanels[i][0].getWin() != 0 && ticTacToePanels[i][0].getWin() == ticTacToePanels[i][1].getWin() && ticTacToePanels[i][1].getWin() == ticTacToePanels[i][2].getWin()) {
                return ticTacToePanels[i][0].getWin();
            }
        }
        for (int j = 0; j < 3; j++) {
            if (ticTacToePanels[0][j].getWin() != 0 && ticTacToePanels[0][j].getWin() == ticTacToePanels[1][j].getWin() && ticTacToePanels[1][j].getWin() == ticTacToePanels[2][j].getWin()) {
                return ticTacToePanels[0][j].getWin();
            }
        }
        if (ticTacToePanels[0][0].getWin() != 0 && ticTacToePanels[0][0].getWin() == ticTacToePanels[1][1].getWin() && ticTacToePanels[1][1].getWin() == ticTacToePanels[2][2].getWin()) {
            return ticTacToePanels[0][0].getWin();
        }
        if (ticTacToePanels[0][2].getWin() != 0 && ticTacToePanels[0][2].getWin() == ticTacToePanels[1][1].getWin() && ticTacToePanels[1][1].getWin() == ticTacToePanels[2][0].getWin()) {
            return ticTacToePanels[0][2].getWin();
        }
        return 0;
    }
    public GenerateMap() {
        TicTacToePanel panel;
        this.setLayout(new GridLayout(3, 3));
        this.setSize(new Dimension(1000, 1000));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel = new TicTacToePanel(new Point(i,j));
                panel.setNewSource(this);
                ticTacToePanels[i][j] = panel;
                this.add(panel);
            }
        }
    }
    public void resetStats(){
        statsReference.removeAll();
        statsReference.setLayout(null);
        statsReference.setBackground(this.getBackground());
        statsReference.revalidate();
        statsReference.repaint();
    }
    public void newGame(){
        resetStats();
        this.removeAll();
        TicTacToePanel panel;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel = new TicTacToePanel(new Point(i,j));
                panel.setNewSource(this);
                ticTacToePanels[i][j] = panel;
                this.add(panel);
            }
        }

    }
}



