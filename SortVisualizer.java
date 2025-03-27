import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class SortVisualizer extends JFrame{
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static int[] sizes;
    final private static Color[] colors = {Color.lightGray, Color.gray, Color.darkGray, Color.black};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortVisualizer visualizer = new SortVisualizer();
            visualizer.setVisible(true);
        });
    }

    public SortVisualizer() {
        sizes = new int[48];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = (int) (Math.random() * 850) + 10;
        }
        setTitle("Sort Visualizer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectionSort();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        int xPos = 20;
        for (int i = 0; i < sizes.length; i++) {
            g2d.setColor(colors[i % 4]);
            g2d.fillRect(xPos, 1000 - sizes[i], 20, sizes[i]);
            xPos += 20;
        }

        g.drawImage(bufferedImage, 0, 0, this);
        g2d.dispose();
    }

    public void selectionSort() {
        int curr = 0;
        int smallIndex = curr;
        for (int i = 0; i < sizes.length - 1; i++) {
            for (int j = curr; j < sizes.length; j++) {
                if (sizes[j] < sizes[smallIndex]) {
                    smallIndex = j;
                }
            }
            swap(smallIndex, curr);
            repaint();
            curr++;
            smallIndex = curr;
        }
    }

    private static void swap(int a, int b) {
        int temp = sizes[a];
        sizes[a] = sizes[b];
        sizes[b] = temp;
    }
}
