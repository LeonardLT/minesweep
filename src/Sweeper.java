import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Sweeper extends JFrame {
    private Container container;
    private int row;
    private int col;
    private int minesCount;//Panel上显示的未消除雷数
    private int minesRealCount;//真实未消除的雷数，
    private int mines;//雷的数目
    private int level;//关卡
    private JButton[][] sweepButton;
    private int[][] sweepButtonValues;
    private boolean[][] buttonFlag;
    private JPanel messagePanel;
    private JLabel timeLabel, minesCountLabel;
    private JMenuItem level1, level2, level3, again, exit;


    public Sweeper(int level) {
        chooseLevel(level);
        initGame(level);
    }

    public int getLevel() {//获得当前level
        return level;
    }

    public void setLevel(int level) {//保存当前关卡
        this.level = level;
    }

    private void chooseLevel(int level) {
        if (level == 1) {
            row = 9;
            col = 9;
            mines = 10;
            minesCount = 10;
            minesRealCount = 10;
            setLevel(1);
        } else if (level == 2) {
            row = 18;
            col = 18;
            mines = 20;
            minesCount = 20;
            minesRealCount = 20;
            setLevel(2);
        } else if (level == 3) {
            row = 27;
            col = 27;
            mines = 30;
            minesCount = 30;
            minesRealCount = 30;
            setLevel(3);
        }
    }


    public void initGame(int level) {
        container = getContentPane();
        container.setLayout(new BorderLayout());
        buildTopPanel();//建立上部Panel,menu and time
        buildGamePanel();//建立游戏核心Panel
        setMines(minesCount);//设雷
        setButtonValue();//计算周围地雷数
        addListener();// 添加监听事件
        buildMainFrame(level);//绘制窗口
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
        level1 = new JMenuItem("初级游戏");
        level2 = new JMenuItem("中级游戏");
        level3 = new JMenuItem("高级游戏");
        again = new JMenuItem("再来一局");

        JMenu gameHelp = new JMenu("帮助");
        JMenuItem help = new JMenuItem("帮助");
        exit = new JMenuItem("退出");

        gameSetting.add(level1);
        gameSetting.add(level2);
        gameSetting.add(level3);
        gameSetting.add(again);
        gameHelp.add(help);
        gameHelp.add(exit);

        menuBar.add(gameSetting);
        menuBar.add(gameHelp);

        menuPanel.add(menuBar);

        return menuPanel;
    }

    public JPanel messagePanel() {
        messagePanel = new JPanel();
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
            setBounds(650, 150, 550, 550);
        } else if (level == 3) {
            setBounds(900, 150, 900, 900);
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }


    private void findMine(int i, int j) {
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
        int count = row * col;//存在与Panel没有被点击的button
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (sweepButton[i][j].isEnabled() == false) {
                    count--;//判断最后剩下的是不是都是雷
                }
            }
        }
        System.out.println("剩余" + count + "未点击");
        System.out.println("mines" + mines + "");
        if (minesRealCount == 0 || count == mines) {//判断输赢的方式1:标记十个雷。2.剩余10个雷没有点击
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
        dispose();
        new Sweeper(getLevel());
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
        if (sweepButton[i][j].getText() != "★") {
            sweepButton[i][j].setEnabled(false);
            if (buttonFlag[i][j] == true && sweepButton[i][j].getText() != "★") {
                return;
            } else {
                buttonFlag[i][j] = true;
                if (sweepButtonValues[i][j] != 10 && sweepButtonValues[i][j] != 0) {
                    markNumber(i, j);
                }
                if (sweepButtonValues[i][j] == 0) {
                    sweepButton[i][j].setText("");
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
        level1.addActionListener(new MenuListener());
        level2.addActionListener(new MenuListener());
        level3.addActionListener(new MenuListener());
        again.addActionListener(new MenuListener());
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
            if (e.getSource() == level1) {
                System.out.println("1");
                setLevel(1);
                new Sweeper(1);
            } else if (e.getSource() == level2) {
                System.out.println("2");
                setLevel(2);
                new Sweeper(2);
            } else if (e.getSource() == level3) {
                new Sweeper(3);
                setLevel(3);
                System.out.println("3");
            } else if (e.getSource() == again) {
                System.out.println("4");
                System.out.println(getLevel());
                new Sweeper(getLevel());//当前关卡从新开始
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
        new Sweeper(1);
    }

}
