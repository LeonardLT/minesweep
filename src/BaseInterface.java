import javax.swing.*;
import java.awt.*;

public class BaseInterface extends JFrame {
    private int row = 9;
    private int col = 9;

    public BaseInterface() {
        setTitle("扫雷游戏");
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        setBounds(500, 150, 500, 500);

        container.add(buildMenuPanel(), BorderLayout.NORTH);
        container.add(buildGamePanel(), BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public void initButtonsAllValues() {
        JButton[][] sweepButton = new JButton[row + 2][col + 2];
        JButton[][] sweepButtonValues = new JButton[row + 2][col + 2];
        //遍历数组
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                sweepButton[i][j] = new JButton();
                sweepButton[i][j].setMargin(new Insets(0, 0, 0, 0));
                sweepButton[i][j].setFont(new Font(null, Font.BOLD, 25));
                sweepButton[i][j].setText("");
            }
        }
    }


    public JPanel buildGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(row, col, 0, 0));//???
        JButton[][] sweepButton = new JButton[row + 2][col + 2];
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                sweepButton[i][j] = new JButton();
                gamePanel.add(sweepButton[i][j]);
            }
        }
        initButtonsAllValues();

        return gamePanel;
    }

    public JPanel buildMenuPanel() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameSetting = new JMenu("游戏设置");
        JMenu gameHelp = new JMenu("帮助");

        JMenuItem jmi1 = new JMenuItem("初级游戏");
        JMenuItem jmi2 = new JMenuItem("中级游戏");
        JMenuItem jmi3 = new JMenuItem("高级游戏");
        JMenuItem jmi4 = new JMenuItem("再来一局");
        gameSetting.add(jmi1);
        gameSetting.add(jmi2);
        gameSetting.add(jmi3);
        gameSetting.add(jmi4);

        menuBar.add(gameSetting);
        menuBar.add(gameHelp);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.NORTH);

        return menuPanel;
    }

    public static void main(String[] args) {
        new BaseInterface();
    }
}
