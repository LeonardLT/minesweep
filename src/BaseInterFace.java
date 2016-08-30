import javax.swing.*;
import java.awt.*;

public class BaseInterFace extends JFrame {
    private Container container;

    public BaseInterFace() {
        setTitle("扫雷游戏");
        container = getContentPane();
        container.setLayout(new BorderLayout());
        setBounds(500, 150, 500, 500);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public static void main(String[] args) {
        new BaseInterFace();
    }
}
