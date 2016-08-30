import javax.swing.*;
import java.awt.*;

public class Sweeper extends JFrame {
    private Container container;

    public Sweeper() {
        container = getContentPane();
        container.setLayout(new BorderLayout());
        initGame();
    }

    public void initGame() {
        buildMenuPanel();
        buildMainFrame();


    }

    public void buildMainFrame() {
        setTitle("扫雷游戏");
        setBounds(500, 150, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public void buildMenuPanel() {
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

        container.add(menuPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new Sweeper();
    }
}
