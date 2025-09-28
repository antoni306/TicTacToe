import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TicTacToeFrame extends JFrame {
    GenerateMap generateMap=new GenerateMap();
    private static int gameCounter=0;
    private JPanel cardLayoutHaver;
    CardLayout cardLayout=new CardLayout();
    public void countGameCounter(){
        try{
            BufferedReader reader=new BufferedReader(new FileReader("savedGames.txt"));
            int counter=1;
            String line;
            while((line= reader.readLine())!=null){
                counter++;
            }
            reader.close();
            gameCounter=counter/39;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadGame(int gameCounter){
        generateMap.resetStats();
        try{
            BufferedReader reader=new BufferedReader(new FileReader("savedGames.txt"));
            String line;
            int turn = -1;
            int whichrow=0;
            int[] column=new int[3];
            Point nextBoard=new Point(-1,-1);
            Point previousBoard=new Point(-1,-1);
            Point GameCoordinates=new Point(-1,-1);
            while((line=reader.readLine())!=null){
                if(line.startsWith("Game id: "+gameCounter)){
                    for(int i=0;i<37;i++){
                        line=reader.readLine();
                        if(i==0){
                            int k=line.length()-1;
                            turn=line.charAt(k)-'0';
                            Square.setTurn(turn);

                        }else if(i==1){
                            nextBoard.x=line.charAt(12)-'0';
                            nextBoard.y=line.charAt(14)-'0';
                            previousBoard.x=line.charAt(16)-'0';
                            previousBoard.y=line.charAt(18)-'0';
                        }else if(line.startsWith("Game coordinates: ")){
                            GameCoordinates=new Point(line.charAt(line.length()-3)-'0',line.charAt(line.length()-1)-'0');

                        }else{
                            column[0]=line.charAt(0)-'0';
                            column[1]=line.charAt(2)-'0';
                            column[2]=line.charAt(4)-'0';
                            generateMap.getTicTacToePanels()[GameCoordinates.x][GameCoordinates.y].getSquares()[whichrow][0].setOwner(column[0]);
                            generateMap.getTicTacToePanels()[GameCoordinates.x][GameCoordinates.y].getSquares()[whichrow][1].setOwner(column[1]);
                            generateMap.getTicTacToePanels()[GameCoordinates.x][GameCoordinates.y].getSquares()[whichrow][2].setOwner(column[2]);
                            whichrow++;
                            if(whichrow==3){
                                whichrow=0;
                            }
                        }
                    }
                }
            }
            Square.setTurn(turn);
            Square.setNextSquare(nextBoard);
            Square.setPreviousSquare(previousBoard);
            reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        int BigSquareWinner=generateMap.getTicTacToePanels()[i][j].checkWinner();
                        if(BigSquareWinner!=0){
                            generateMap.getTicTacToePanels()[i][j].setWin(BigSquareWinner);
                            generateMap.getTicTacToePanels()[i][j].removeAll();
                            generateMap.getTicTacToePanels()[i][j].repaint();
                            generateMap.getTicTacToePanels()[i][j].setEnabled(false);
                        }else{
                            generateMap.getTicTacToePanels()[i][j].getSquares()[k][l].repaint();
                        }
                    }
                }
            }
        }
        generateMap.selectNext();

        cardLayout.show(cardLayoutHaver,"panel2");

    }
    public void SaveGame(TicTacToePanel[][] games){
        countGameCounter();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("savedGames.txt",true))) {
            int turn=Square.getTurn();
            writer.write("Game id: "+(++gameCounter)+"\n");
            writer.write("Turn: "+turn+"\n");
            Point nextBoard=Square.getNextSquare();
            Point previousBoard=Square.getNextSquare();
            writer.write("next board: "+nextBoard.x+" "+nextBoard.y+" "+previousBoard.x+" "+previousBoard.y+"\n");
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    Square[][] Igame=games[i][j].getSquares();
                    writer.write("Game coordinates: "+i+" "+j+"\n");
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            writer.write(Igame[k][l].getOwner()+" ");
                        }
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public TicTacToeFrame(){
        countGameCounter();
        cardLayoutHaver=new JPanel(cardLayout);
        this.setSize(new Dimension(2880,1800));

        JPanel MenuPanel=new JPanel(new GridLayout(4,1));
//        MenuPanel.setPreferredSize(new Dimension(2880,1800));

        JButton buttonHotSeat=new JButton("Hot Seat");
        buttonHotSeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generateMap.checkWinner()!=0){

                    generateMap.newGame();
                }
                cardLayout.show(cardLayoutHaver,"panel2");
            }
        });

        MenuPanel.add(buttonHotSeat);

        JButton buttonAi=new JButton("Ai");
        MenuPanel.add(buttonAi);


        JButton buttonLoadGame=new JButton("Load Game");
        buttonLoadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameCounter>0)
                    cardLayout.show(cardLayoutHaver,"panel3");
            }
        });

        MenuPanel.add(buttonLoadGame);
        JPanel availableGamesPanel=new JPanel();
        for(int i=1;i<=gameCounter;i++){
            JButton button=new JButton("game: "+i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadGame(button.getText().charAt(button.getText().length()-1)-'0');
                }
            });
            availableGamesPanel.add(button);
        }
        cardLayoutHaver.add(availableGamesPanel,"panel3");


        JButton buttonSaveGame=new JButton("Save Game");
        buttonSaveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveGame(generateMap.getTicTacToePanels());
            }
        });
        MenuPanel.add(buttonSaveGame);
        MenuPanel.setBackground(Color.BLACK);
        cardLayoutHaver.add(MenuPanel,"panel1");


        JPanel BoardPanel = new JPanel();
        JPanel lastMovePanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawString("Big Square: "+Square.getPreviousSquare().x+" , "+Square.getPreviousSquare().y,10,10);
                g.drawString( "Small Square: "+Square.getNextSquare().x+" , "+Square.getNextSquare().y,10,30);
            }
        };
        generateMap.setStatsREference(lastMovePanel);
        lastMovePanel.setPreferredSize(new Dimension(100,100));

        JButton showMenuButton=new JButton("Menu");
        showMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardLayoutHaver,"panel1");
            }
        });
        showMenuButton.setSize(new Dimension(60,30));
        JPanel MenuButtonAndLastMovePanel= new JPanel(new BorderLayout());
        MenuButtonAndLastMovePanel.add(showMenuButton,BorderLayout.LINE_START);
        MenuButtonAndLastMovePanel.add(lastMovePanel,BorderLayout.CENTER);
        BoardPanel.add(generateMap);
//        JPanel TicTacGame=new JPanel(new GridLayout(1,2));

        JSplitPane TicTacGame = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, MenuButtonAndLastMovePanel, BoardPanel);
        TicTacGame.setResizeWeight(0.3); // lewa część = 30%, prawa = 70%


//        TicTacGame.add(MenuButtonAndLastMovePanel);
//        TicTacGame.add(BoardPanel);
        cardLayoutHaver.add(TicTacGame,"panel2");


        cardLayout.show(cardLayoutHaver,"panel1");

        //koniec menu
        this.add(cardLayoutHaver);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

