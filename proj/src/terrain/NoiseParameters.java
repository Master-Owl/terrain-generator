package terrain;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import perlinNoise.Settings;

public class NoiseParameters extends JFrame {
    private Settings settings;
    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private final int MIN_OCTAVES = 4;
    private final int MAX_OCTAVES = 12;
    private final int MIN_PERSIST = 0;
    private final int MAX_PERSIST = 100;

    private int octavesSlider;
    private float persistenceSlider;

    public NoiseParameters(Settings s){
        super();
        settings = s;
        octavesSlider = settings.getOctaves();
        persistenceSlider = settings.getPersistence();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
                screenSize.width / 2,
                screenSize.height / 2,
                300, 200);
        init();
    }

    public Settings getSettings() { return settings; }

    private void init(){
        contentPane = new JPanel();
        tabbedPane = new JTabbedPane();

        JSlider octaves = new JSlider(JSlider.HORIZONTAL, MIN_OCTAVES, MAX_OCTAVES, octavesSlider);
        octaves.setMajorTickSpacing(2);
        octaves.setMinorTickSpacing(1);
        octaves.setPaintLabels(true);
        octaves.setPaintTicks(true);
        octaves.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider)e.getSource();
                octavesSlider = slider.getValue();
            }
        });

        JSlider persistence = new JSlider(JSlider.HORIZONTAL, MIN_PERSIST, MAX_PERSIST, (int)(persistenceSlider * 100f));
        persistence.setMajorTickSpacing(50);
        persistence.setMinorTickSpacing(10);
        persistence.setPaintLabels(true);
        persistence.setPaintTicks(true);
        persistence.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider)e.getSource();
                persistenceSlider = slider.getValue() / 100f;
            }
        });

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setOctaves(octavesSlider);
                settings.setPersistance(persistenceSlider);
                setVisible(false);
            }
        });

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel octTab = new JPanel();
        JPanel persTab = new JPanel();

        octTab.add(octaves);
        persTab.add(persistence);

        tabbedPane.addTab("Octaves", octTab);
        tabbedPane.addTab("Persistence", persTab);

        contentPane.add(tabbedPane);
        contentPane.add(apply);

        getContentPane().add(contentPane);
    }
}
