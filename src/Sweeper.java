import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Sweeper extends JFrame {
    private Container container;
    //        private int row = 9;
//    private int col = 9;
    private int row;
    private int col;
    private int minesCount = 10;
    private int minesRealCount = 10;
    private JButton[][] sweepButton;
    //    private int[][] sweepButtonValues = new int[row + 2][col + 2];
    private int[][] sweepButtonValues;
    private int sweepButtonCount;
    private boolean[][] buttonFlag;
    private JPanel messagePanel;
    private JLabel timeLabel, minesCountLabel;
    private JMenuItem jmi1, jmi2, jmi3, jmi4, exit;

    public Sweeper() {
        row = 9;
        col = 9;
        sweepButtonCount = 81;
        initGame(1);
    }

    public Sweeper(int level) {
        if (level == 1) {
            row = 9;
            col = 9;
            sweepButtonCount = 81;
            minesCount = 10;
        } else if (level == 2) {
            row = 18;
            col = 18;
            sweepButtonCount = 18 * 18;
            minesCount = 20;
        } else if (level == 3) {
            row = 27;
            col = 27;
            minesCount = 30;
        }
        initGame(level);
    }

    public void initGame(int level) {
        container = getContentPane();
        container.setLayout(new BorderLayout());
        buildTopPanel();
        buildGamePanel();
        setMines(minesCount);
        setButtonValue();
        addListener();
        buildMainFrame(level);
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
        jmi1 = new JMenuItem("初级游戏");
        jmi2 = new JMenuItem("中级游戏");
        jmi3 = new JMenuItem("高级游戏");
        jmi4 = new JMenuItem("再来一局");

        JMenu gameHelp = new JMenu("帮助");
        JMenuItem help = new JMenuItem("帮助");
        exit = new JMenuItem("退出");

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

    public JPanel messagePanel() {
        messagePanel = new JPanel();
//        timeLabel = new JLabel("游戏时间:" + Integer.toString(timeLength) + "秒");
        timeLabel = new JLabel("游戏时间:" + 0 + "秒");
        showTime();
        JLabel resultLabel = new JLabel("   状态:游戏进行中");
        minesCountLabel = new JLabel("   剩余地雷个数:" + minesCount);

        messagePanel.add(timeLabel);
        messagePanel.add(resultLabel);
        messagePanel.add(minesCountLabel);
        return messagePanel;
    }

    public void showTime() {
        Thread time = new Thread(new Runnable() {
            int timeNumber = 0;

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeNumber++;
                    timeLabel.setText("游戏时间:" + timeNumber + "秒");
                    messagePanel.add(timeLabel);
                }
            }
        });
        time.start();

    }

    public JPanel buildGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(row, col, 0, 0));
        sweepButton = new JButton[row + 2][col + 2];
        sweepButtonValues = new int[row + 2][col + 2];
        buttonFlag = new boolean[row + 2][col + 2];


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
            sweepButton[x][y].setText("Q");

        }
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

    public void buildMainFrame(int level) {
        setTitle("扫雷游戏");
        if (level == 1) {
            setBounds(500, 150, 500, 500);
        } else if (level == 2) {
            setBounds(600, 150, 500, 500);
        } else if (level == 3) {
            setBounds(800, 150, 600, 600);
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


    private void findMine(int i, int j) {
//        buttonFlag[i][j] = true;
//        sweepButton[i][j].setBackground(Color.RED);
//        if(sweepButton[i][j].getText() == ""){
        if (!sweepButton[i][j].isEnabled()) {
            return;
        } else if (sweepButton[i][j].getText() == "Q") {
            minesCount--;
            sweepButton[i][j].setText("★");
            if (sweepButtonValues[i][j] == 10) {
                minesRealCount--;
                System.out.println("--真实地雷数目:" + minesRealCount);
            }
        } else if (sweepButton[i][j].getText() == "★") {
            minesCount++;
            sweepButton[i][j].setText("Q");
            if (sweepButtonValues[i][j] == 10) {
                minesRealCount++;
                System.out.println("++真实地雷数目:" + minesRealCount);
            }
        }
        minesCountLabel.setText("   剩余地雷个数:" + minesCount);
        isWinner();
    }

    public void isWinner() {
        System.out.println(sweepButtonCount + "==");
        if (minesRealCount == 0 || sweepButtonCount == 0) {
            JOptionPane.showMessageDialog(null, "--Win--");
        }
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


    //显示雷或者数字
    public void markNumber(int i, int j) {
        sweepButtonCount--;
        System.out.println(sweepButtonCount);

        sweepButton[i][j].setText(Integer.toString(sweepButtonValues[i][j]));
        sweepButton[i][j].setEnabled(false);
    }

    public void markMine(int i, int j) {
        sweepButton[i][j].setBackground(Color.RED);
        sweepButton[i][j].setText("X");
        sweepButton[i][j].setEnabled(false);
    }

    public void markZero(int i, int j) {
        if (sweepButton[i][j].getText() != "★") {


            sweepButton[i][j].setEnabled(false);
            if (buttonFlag[i][j] == true && sweepButton[i][j].getText() != "★") {
                return;
            } else {
                buttonFlag[i][j] = true;
                if (sweepButtonValues[i][j] != 10 && sweepButtonValues[i][j] != 0) {
                    sweepButtonCount++;
                    System.out.println("..........");
                    markNumber(i, j);
                }
                if (sweepButtonValues[i][j] == 0) {
                    sweepButton[i][j].setText("");
                    sweepButtonCount--;
                    System.out.println(sweepButtonCount + "----");
                    for (int s = i - 1; s >= 0 && s <= row && s <= i + 1; s++) {
                        for (int t = j - 1; t >= 0 && t <= col && t <= j + 1; t++) {
                            markZero(s, t);
                        }
                    }
                }
            }
        }
    }


    public void addListener() {
        jmi1.addActionListener(new MenuListener());
        jmi2.addActionListener(new MenuListener());
        jmi3.addActionListener(new MenuListener());
        jmi4.addActionListener(new MenuListener());
        exit.addActionListener(new ExitListener());
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
                        if (Objects.equals(sweepButton[i][j].getText(), "★")) {
                            return;
                        } else if (sweepButtonValues[i][j] == 0) {
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
            isWinner();
        }
    }

    private class FindMineMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    if (e.getSource() == sweepButton[i][j] && e.getButton() == MouseEvent.BUTTON3) { //***
                        findMine(i, j);
                    }
                }
            }
        }
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            if (e.getSource() == jmi1) {
                System.out.println("1");
                new Sweeper(1);
            } else if (e.getSource() == jmi2) {
                System.out.println("2");
                new Sweeper(2);
            } else if (e.getSource() == jmi3) {
                new Sweeper(3);
                System.out.println("3");
            } else if (e.getSource() == jmi4) {
                System.out.println("4");
                new Sweeper();
            }
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exit) {
                System.exit(0);
            }
        }
    }


    public static void main(String[] args) {
        new Sweeper();
    }

}
