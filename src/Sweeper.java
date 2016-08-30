import javax.swing.*;
import java.awt.*;

public class Sweeper extends JFrame {
    private Container container;
    private int row = 9;
    private int col = 9;
    private int timeLength = 0;
    private int minesCount = 10;
    private JButton[][] sweepButton;

    public Sweeper() {
        container = getContentPane();
        container.setLayout(new BorderLayout());

        initGame();
    }

    public void initGame() {
        buildTopPanel();
        buildGamePanel();
        setMines(minesCount);
        buildMainFrame();
    }

    public void buildMainFrame() {
        setTitle("扫雷游戏");
        setBounds(500, 150, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
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
            sweepButton[x][y].setText("Q");

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


    public void buildGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(row, col, 0, 0));
        sweepButton = new JButton[row + 2][col + 2];
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                sweepButton[i][j] = new JButton();
                sweepButton[i][j].setMargin(new Insets(0, 0, 0, 0));
                sweepButton[i][j].setFont(new Font(null, Font.BOLD, 25));
                sweepButton[i][j].setText("");
                gamePanel.add(sweepButton[i][j]);
            }
        }
        container.add(gamePanel, BorderLayout.CENTER);
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
