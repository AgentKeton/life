package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class Frame extends JFrame{

    private Board board;
    private ScheduledFuture future;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    public Frame() {
        future = null;
        boolean initial[][] = new boolean[50][50];

        initial[3][2] = true; initial[4][3] = true; initial[4][4] = true; initial[3][4] = true; initial[2][4] = true;

        board = new Board(initial);

        setSize(450,450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        final JButton runButton = new JButton("Run!");
        final JButton resetButton = new JButton("Reset");

        panel.add(runButton);
        panel.add(resetButton);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (future == null || future.isCancelled()) {
                    runButton.setText("Pause");
                    resetButton.setEnabled(false);
                    future = executor.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            board.next();
                            Frame.this.repaint();
                        }
                    }, 0, 300, TimeUnit.MILLISECONDS);
                } else {
                    future.cancel(false);
                    runButton.setText("Run!");
                    resetButton.setEnabled(true);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.init();
                Frame.this.repaint();
            }
        });
    }

    public void paint( Graphics g ) {
        boolean[][] field = board.getCells();

        Insets insets = getInsets();

        insets.left += 10;
        insets.right += 10;
        insets.top += 40;
        insets.bottom += 10;

        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;

        int wx = field.length;
        int wy = field[0].length;

        int cellW = w / wx;
        int cellH = h / wy;

        Graphics2D g2d = (Graphics2D) g;

        super.paint( g );
        for( int i = insets.left, ic = 0; ic < wx; i += cellW, ic++ ) {
            for( int j = insets.top, jc = 0; jc < wy; j += cellH, jc++ ) {
                if (field[ic][jc] ) {
                    g2d.fillRect(i, j, cellW, cellH);
                } else {
                    g2d.drawRect(i, j, cellW, cellH);
                }
            }
        }

    }

    public static void main(String[] arg ) {
        Frame s = new Frame();
        s.setVisible(true);
    }
}
