import com.sun.tools.javac.nio.JavacPathFileManager;

import javax.swing.*;
import java.awt.*;

public class BaseInterFace extends JFrame {
    private Container container;
    private JPanel menuPanel;

    public BaseInterFace() {
        setTitle("扫雷游戏");
        container = getContentPane();
        container.setLayout(new BorderLayout());
        setBounds(500, 150, 500, 500);

        container.add(initGame());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public JPanel initGame(){
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(initMenu(), BorderLayout.NORTH);
        return gamePanel;
    }

    public JMenuBar initMenu(){
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
        return menuBar;
    }

    public static void main(String[] args) {
        new BaseInterFace();
    }
}
