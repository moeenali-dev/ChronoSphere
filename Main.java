import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DashboardGUI gui = new DashboardGUI();
                gui.setVisible(true);
                gui.toFront();
                gui.repaint();
            }
        });
    }
}