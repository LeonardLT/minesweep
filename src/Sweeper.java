import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sweeper extends JFrame {
    private Container container;
    private int row = 9;
    private int col = 9;
    private int timeLength = 0;
    private int minesCount = 10;
    private JButton[][] sweepButton;
    int[][] sweepButtonValues = new int[row + 2][col + 2];
    private boolean[][] buttonFlag = new boolean[row + 2][col + 2];

    public Sweeper() {
        container = getContentPane();
        container.setLayout(new BorderLayout());

        initGame();
    }

    public void initGame() {
        buildTopPanel();
        buildGamePanel();
        setMines(minesCount);
        setButtonValue();
        addListener();
        buildMainFrame();
    }

    public void buildMainFrame() {
        setTitle("扫雷游戏");
        setBounds(500, 150, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


    class FindMineMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 1; i <= row; i++) {
                for (int j = 0; j <= row; j++) {
                    if (e.getSource() == sweepButton[i][j] && e.getButton() == MouseEvent.BUTTON3) { //***
                        findMine(i, j);
                    }
                }
            }
        }


    }

    private void findMine(int i, int j) {
        buttonFlag[i][j] = true;
        sweepButton[i][j].setBackground(Color.RED);
        sweepButton[i][j].setText("★");
        minesCount--;
        buildTopPanel();
    }

    public void gameOver() {
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (sweepButtonValues[i][j] == 0) {
                    sweepButton[i][j].setText("");
                } else if (sweepButtonValues[i][j] == 10) {
                    sweepButton[i][j].setText("X");
                } else {
                    sweepButton[i][j].setText(Integer.toString(sweepButtonValues[i][j]));

                }
                sweepButton[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(null, "Game over!");
        new Sweeper();
    }

    //计算周围地雷数
    public void setButtonValue() {
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (sweepButtonValues[i][j] != 10) {
                    for (int x = j - 1; x <= j + 1; x++) {
                        if (sweepButtonValues[i - 1][x] == 10) {
                            sweepButtonValues[i][j]++;
                        }
                        if (sweepButtonValues[i + 1][x] == 10) {
                            sweepButtonValues[i][j]++;
                        }
                    }
                    if (sweepButtonValues[i][j - 1] == 10) {
                        sweepButtonValues[i][j]++;
                    }
                    if (sweepButtonValues[i][j + 1] == 10) {
                        sweepButtonValues[i][j]++;
                    }
                    //显示数字
//                    sweepButton[i][j].setText(Integer.toString(sweepButtonValues[i][j]));
                }
            }
        }
    }

    //显示雷或者数字
    public void markNumber(int i, int j) {
        sweepButton[i][j].setText(Integer.toString(sweepButtonValues[i][j]));
        sweepButton[i][j].setEnabled(false);
    }

    public void markMine(int i, int j) {
        sweepButton[i][j].setBackground(Color.RED);
        sweepButton[i][j].setText("X");
        sweepButton[i][j].setEnabled(false);
    }

    public void markZero(int i, int j) {
//        sweepButton[i][j].setText(".");
        sweepButton[i][j].setEnabled(false);
        if (buttonFlag[i][j] == true) {
            return;
        } else {
            buttonFlag[i][j] = true;
            if (sweepButtonValues[i][j] != 10 && sweepButtonValues[i][j] != 0) {
                markNumber(i, j);
            }
            if (sweepButtonValues[i][j] == 0) {
                sweepButton[i][j].setText("");
                for (int s = i - 1; s >= 0 && s <= row && s <= i + 1; s++) {
                    for (int t = j - 1; t >= 0 && t < col && t < j + 1; t++) {
                        markZero(s, t);
                    }
                }
            }
        }
    }


    public void addListener() {
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                sweepButton[i][j].addActionListener(new ButtonActionListener());
                sweepButton[i][j].addMouseListener(new FindMineMouseListener());
            }
        }
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    if (e.getSource() == sweepButton[i][j]) {
                        if (sweepButtonValues[i][j] == 0) {
                            markZero(i, j);
                        } else if (sweepButtonValues[i][j] == 10) {
                            markMine(i, j);
                            gameOver();
                        } else {
                            markNumber(i, j);
                        }
                    }
                }
            }
        }
    }

    public void setMines(int minesCount) {
        this.minesCount = minesCount;
        int[] randomValues = new int[minesCount];
        for (int i = 0; i < minesCount; i++) {
            int randomNumber = (int) (Math.random() * col * row);
            for (int j = 0; j < randomValues.length; j++) {
                if (randomValues[j] == randomNumber) {
                    randomNumber = (int) (Math.random() * col * row);
                    j--;
                }
            }
            randomValues[i] = randomNumber;
            int x = randomValues[i] / col + 1;
            int y = randomValues[i] % col + 1;
            sweepButtonValues[x][y] = 10;
//            sweepButton[x][y].setText("Q");

        }
    }

    public void buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(menuPanel(), BorderLayout.NORTH);
        topPanel.add(messagePanel(), BorderLayout.SOUTH);
        container.add(topPanel, BorderLayout.NORTH);
    }

    public JPanel menuPanel() {
        JMenuBar menuBar = new JMenuBar();
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());//***

        JMenu gameSetting = new JMenu("游戏设置");
        JMenuItem jmi1 = new JMenuItem("初级游戏");
        JMenuItem jmi2 = new JMenuItem("中级游戏");
        JMenuItem jmi3 = new JMenuItem("高级游戏");
        JMenuItem jmi4 = new JMenuItem("再来一局");

        JMenu gameHelp = new JMenu("帮助");
        JMenuItem help = new JMenuItem("帮助");
        JMenuItem exit = new JMenuItem("退出");

        gameSetting.add(jmi1);
        gameSetting.add(jmi2);
        gameSetting.add(jmi3);
        gameSetting.add(jmi4);
        gameHelp.add(help);
        gameHelp.add(exit);

        menuBar.add(gameSetting);
        menuBar.add(gameHelp);

        menuPanel.add(menuBar);

        return menuPanel;
    }


    public JPanel buildGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(row, col, 0, 0));
        sweepButton = new JButton[row + 2][col + 2];

        for (int i = 0; i < row + 2; i++) {      //!!!!!!!!!!!!!!!!
            for (int j = 0; j < col + 2; j++) {
                sweepButton[i][j] = new JButton();
                sweepButton[i][j].setMargin(new Insets(0, 0, 0, 0));
                sweepButton[i][j].setFont(new Font(null, Font.BOLD, 25));
                sweepButton[i][j].setText("");
                sweepButtonValues[i][j] = 0;
                buttonFlag[i][j] = false;
            }
        }
        for (int i = 1; i <= row; i++) {        //!!!!!!!!!!!!!!!!
            for (int j = 1; j <= col; j++) {
                gamePanel.add(sweepButton[i][j]);

            }
        }
        container.add(gamePanel, BorderLayout.CENTER);
        return gamePanel;
    }

    public JPanel messagePanel() {
        JPanel messagePanel = new JPanel();
        JLabel timeLabel = new JLabel("游戏时间:" + Integer.toString(timeLength) + "秒");
        JLabel resultLabel = new JLabel("   状态:游戏进行中");
        JLabel minesCountLabel = new JLabel("   剩余地雷个数:" + minesCount);

        messagePanel.add(timeLabel);
        messagePanel.add(resultLabel);
        messagePanel.add(minesCountLabel);
        return messagePanel;
    }

    public static void main(String[] args) {
        new Sweeper();
    }

}
