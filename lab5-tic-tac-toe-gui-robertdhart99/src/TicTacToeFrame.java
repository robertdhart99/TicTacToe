import javax.rmi.CORBA.Tie;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    Font gameBoardFont = new Font("Helvatica", Font.BOLD, 20);
    Font XOButtons = new Font("Helvatica", Font.BOLD, 30);
    Font mainLabelFont = new Font("Helvatica", Font.BOLD, 48);
    TicTacToe game = new TicTacToe();
    TicTacToeTile[][] board = game.getBoard();
    JPanel mainPanel;
    JLabel mainLabel;
    JPanel gameBoardPanel;
    JPanel buttonPanel;
    JButton quitButton;
    JPanel resultsPanel;
    JLabel XWinsLabel;
    JLabel OWinsLabel;
    JLabel TiesLabel;
    ImageIcon TTCimg;
    int Owins = 0;
    int Xwins = 0;
    int ties = 0;

    public TicTacToeFrame(String title) {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainPanel = new JPanel();

        mainLabel = new JLabel("Tic Tac Toe");
        gameBoardPanel = new JPanel();
        // all the game tiles/buttons
        buttonPanel = new JPanel();
        TTCimg = new ImageIcon("assets/quit.png");
        quitButton = new JButton("Quit", TTCimg);//shows an img
        resultsPanel = new JPanel();
        XWinsLabel = new JLabel("X: 0");
        OWinsLabel = new JLabel("Y: 0");
        TiesLabel = new JLabel("Ties: 0 ");

        UISetup();


        //actions
        quitButton.addActionListener((ActionEvent actionEvent) -> System.exit(0));


        setVisible(true);

    }

    public void UISetup() {

        mainPanel.setLayout(new BorderLayout());
        resultsPanel.setLayout(new GridLayout(3,1));
        gameBoardPanel.setLayout(new GridLayout(game.getBoardSize(), game.getBoardSize()));

        setupGameBoard();

        resultsPanel.add(XWinsLabel);
        resultsPanel.add(OWinsLabel);
        resultsPanel.add(TiesLabel);
        buttonPanel.add(quitButton);
        mainPanel.add(mainLabel, BorderLayout.NORTH);
        mainPanel.add(gameBoardPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void setupGameBoard() {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                gameBoardPanel.add(board[row][col]);
                board[row][col].setFont(XOButtons);
                board[row][col].addActionListener((ActionEvent ae) ->
                {
                    TicTacToeTile selected = (TicTacToeTile) ae.getSource();
                    game.playTurn(selected.getRow(), selected.getColumn());
                    selected.setForeground(
                            game.getCurrentTurn().name().equals("X") ? Color.BLUE: Color.RED
                    );
                    game.calculateResult();
                    if(game.isOver()){
                        System.out.println("The game is over and the Result is: " + game.getResult());
                        updateGameResults();
                        Boolean done = SafeInput.getYNConfirmDialog("Play Again?");
                        if(!done) {
                            System.exit(0);
                        }
                        resetGame();
                    }
                });
            }
        }

    }

    private void updateGameResults() {
        switch (game.getResult()) {
            case "X":
                Xwins++;
                XWinsLabel.setText("X: "+ Xwins);
                break;
            case "O":
                Owins++;
                OWinsLabel.setText("O: "+ Owins);
                break;
            case "tie":
                ties++;
                TiesLabel.setText("Ties: " + ties);
                break;
        }
    }

    public void resetGame() {
        game = new TicTacToe();
        board = game.getBoard();
        gameBoardPanel.removeAll();
        setupGameBoard();
    }
}
