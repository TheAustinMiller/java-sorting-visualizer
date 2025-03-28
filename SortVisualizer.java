import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SortVisualizer extends JFrame {
    private static final int WIDTH = 1016;
    private static final int HEIGHT = 1000;
    private static final int LENGTH = 50;
    private static int[] sizes;
    final private static Color[] colors = {Color.lightGray, Color.gray, Color.darkGray, Color.black};
    private SortPanel sortPanel;
    int curr;
    int smallIndex;
    private volatile boolean running = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortVisualizer visualizer = new SortVisualizer();
            visualizer.setVisible(true);
        });
    }

    public SortVisualizer() {
        sizes = new int[LENGTH];
        randomize();
        setTitle("Sort Visualizer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel instructionLabel = new JLabel("S: Selection Sort | B: Bubble Sort | I: Insertion Sort " +
                "| M: Merge Sort | Q: Quick Sort | H: Heap Sort | R: Reset");
        instructionLabel.setFont(new Font("Helvetica", Font.PLAIN, 15)); // Make font smaller
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.add(instructionLabel);
        add(instructionPanel, BorderLayout.NORTH);

        sortPanel = new SortPanel();
        sortPanel.setFocusable(true); // Ensure the panel can receive focus
        add(sortPanel);

        sortPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!running) {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        randomize();
                        curr = 0;
                        smallIndex = curr;
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_S) {
                        running = true;
                        new Thread(() -> {
                            selectionSort();
                            running = false;
                        }).start();
                    } else if (e.getKeyCode() == KeyEvent.VK_B) {
                        running = true;
                        new Thread(() -> {
                            bubbleSort();
                            running = false;
                        }).start();
                    } else if (e.getKeyCode() == KeyEvent.VK_I) {
                        running = true;
                        new Thread(() -> {
                            insertionSort();
                            running = false;
                        }).start();
                    } else if (e.getKeyCode() == KeyEvent.VK_M) {
                        running = true;
                        new Thread(() -> {
                            mergeSort(0, LENGTH - 1);
                            running = false;
                        }).start();
                    } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                        running = true;
                        new Thread(() -> {
                            quickSort(0, LENGTH - 1);
                            running = false;
                        }).start();
                    } else if (e.getKeyCode() == KeyEvent.VK_H) {
                        running = true;
                        new Thread(() -> {
                            heapSort();
                            running = false;
                        }).start();
                    }
                }
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                sortPanel.requestFocusInWindow();
            }
        });
    }

    private void heapify(int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        curr = i;
        smallIndex = l < n ? l : (r < n ? r : i);

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (l < n && sizes[l] > sizes[largest]) {
            largest = l;

            curr = l;
            smallIndex = i;
            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (r < n && sizes[r] > sizes[largest]) {
            largest = r;

            curr = r;
            smallIndex = i;
            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (largest != i) {
            curr = i;
            smallIndex = largest;
            swap(i, largest);

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
            heapify(n, largest);
        }
    }

    public void heapSort() {
        for (int i = sizes.length / 2 - 1; i >= 0; i--) {
            curr = i;
            smallIndex = sizes.length - 1;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }

            heapify(sizes.length, i);
        }

        for (int i = sizes.length - 1; i > 0; i--) {
            curr = 0;
            smallIndex = i;
            swap(0, i);

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
            heapify(i, 0);
        }

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int partition(int left, int right) {
        int pivot = sizes[right];
        curr = right;

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int i = left - 1;

        for (int j = left; j <= right - 1; j++) {
            curr = j;
            smallIndex = right;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sizes[j] <= pivot) {
                i++;
                curr = i;
                smallIndex = j;

                swap(i, j);

                try {
                    SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                    Thread.sleep(25);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        swap(i + 1, right);
        curr = i + 1;
        smallIndex = right;

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i + 1;
    }

    public void quickSort(int left, int right) {
        if (left < right) {
            curr = left;
            smallIndex = right;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int pivotIndex = partition(left, right);

            quickSort(left, pivotIndex - 1);
            quickSort(pivotIndex + 1, right);
        }

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void selectionSort() {
        curr = 0;
        smallIndex = curr;
        for (int i = 0; i < sizes.length - 1; i++) {
            for (int j = curr; j < sizes.length; j++) {
                if (sizes[j] < sizes[smallIndex]) {
                    smallIndex = j;
                }
            }
            swap(smallIndex, curr);

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(50); // Add a small delay
            } catch (Exception e) {
                e.printStackTrace();
            }

            curr++;
            smallIndex = curr;
        }
    }

    public void bubbleSort() {
        for (int i = 0; i < sizes.length - 1; i++) {
            for (int j = 0; j < sizes.length - 1 - i; j++) {
                if (sizes[j] > sizes[j + 1]) {
                    curr = j;
                    smallIndex = j + 1;
                    swap(curr, smallIndex);
                    try {
                        SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                        Thread.sleep(25); // Add a small delay
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void insertionSort() {
        for (int i = 1; i < sizes.length; ++i) {
            int key = sizes[i];
            int j = i - 1;
            curr = i; // Current element being inserted
            while (j >= 0 && sizes[j] > key) {
                sizes[j + 1] = sizes[j];
                j = j - 1;
                smallIndex = j;
                try {
                    SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                    Thread.sleep(10); // Add a small delay
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sizes[j + 1] = key;
        }
        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(50); // Add a small delay
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mergeSort(int left, int right) {
        if (left < right) {
            int m = left + (right - left) / 2;

            curr = left;
            smallIndex = m;
            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mergeSort(left, m);
            mergeSort(m + 1, right);

            mergeHelper(left, m, right);
        }
    }

    public void mergeHelper(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy elements
        for (int i = 0; i < n1; ++i) {
            L[i] = sizes[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = sizes[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        // Merge and visualize
        while (i < n1 && j < n2) {
            curr = left + k;
            smallIndex = left + i < mid ? i : j + mid + 1;

            if (L[i] <= R[j]) {
                sizes[k] = L[i];
                i++;
            }
            else {
                sizes[k] = R[j];
                j++;
            }
            k++;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (i < n1) {
            curr = k;
            smallIndex = left + i;
            sizes[k] = L[i];
            i++;
            k++;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        while (j < n2) {
            curr = k;
            smallIndex = j + mid + 1;
            sizes[k] = R[j];
            j++;
            k++;

            try {
                SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            SwingUtilities.invokeAndWait(() -> sortPanel.repaint());
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void swap(int a, int b) {
        int temp = sizes[a];
        sizes[a] = sizes[b];
        sizes[b] = temp;
    }

    private static void randomize() {
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = (int) (Math.random() * 850) + 10;
        }
    }

    private class SortPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int xPos = 0;
            for (int i = 0; i < sizes.length; i++) {
                g2d.setColor(colors[i % 4]);
                if (i == smallIndex) {
                    g2d.setColor(Color.green);
                }
                if (i == curr) {
                    g2d.setColor(Color.red);
                }
                g2d.fillRect(xPos, getHeight() - sizes[i], 20, sizes[i]);
                xPos += 20;
            }
        }
    }
}
