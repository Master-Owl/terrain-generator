package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import display.PlaneDrawerSettings;
import export.SaveMesh;
import perlinNoise.Settings;

public class SettingsWindow extends JFrame {
	private Settings settings;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private AppWindow window;

	private final int MIN_OCTAVES = 4;
	private final int MAX_OCTAVES = 12;
	private final int MIN_PERSIST = 0;
	private final int MAX_PERSIST = 100;

	private final float MIN_SCALE = 2.00f;
	private final float MAX_SCALE = 10.0f;
	private final float MIN_HEIGHT = 0.00f;
	private final float MAX_HEIGHT = 20.0f;
	private final int MIN_DETAIL = 10;
	private final int MAX_DETAIL = 100;

	private int octavesSlider;
	private float persistenceSlider;

	private boolean wireframe;
	private boolean autoRotate;
	private float scaleSizeSlider;
	private float heightAmplifySlider;
	private int detailSlider;
	
	public 	static String EXPORT_LOCATION = "." + File.separator + "exports";

	public SettingsWindow(Settings s, AppWindow w) {
		super();
		window = w;
		octavesSlider = s.getOctaves();
		persistenceSlider = s.getPersistence();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screenSize.width / 2, screenSize.height / 2, 500, 200);
		setTitle("Settings");
		contentPane = new JPanel();
		tabbedPane = new JTabbedPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		init();

		if (s instanceof PlaneDrawerSettings) {
			PlaneDrawerSettings pds = new PlaneDrawerSettings(s);
			settings = pds;
			wireframe = pds.useWireframe();
			autoRotate = pds.getAutoRotate();
			scaleSizeSlider = pds.getScaleSize();
			heightAmplifySlider = pds.getHeightAmplify();
			detailSlider = pds.getArrWidth() - 1;

			initPlaneDrawer();
		} else {
			settings = s;
		}

		apply();
	}

	public Settings getSettings() {
		return settings;
	}

	private void init() {
		JSlider octaves = new JSlider(JSlider.HORIZONTAL, MIN_OCTAVES, MAX_OCTAVES, octavesSlider);
		octaves.setMajorTickSpacing(2);
		octaves.setMinorTickSpacing(1);
		octaves.setPaintLabels(true);
		octaves.setPaintTicks(true);
		octaves.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				octavesSlider = slider.getValue();
			}
		});

		JSlider persistence = new JSlider(JSlider.HORIZONTAL, MIN_PERSIST, MAX_PERSIST,
				(int) (persistenceSlider * 100f));
		persistence.setMajorTickSpacing(50);
		persistence.setMinorTickSpacing(10);
		persistence.setPaintLabels(true);
		persistence.setPaintTicks(true);
		persistence.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				persistenceSlider = slider.getValue() / 100f;
			}
		});

		JPanel octTab = new JPanel();
		JPanel persTab = new JPanel();

		octTab.add(octaves);
		persTab.add(persistence);

		tabbedPane.addTab("Blending", octTab);
		tabbedPane.addTab("Harshness", persTab);
	}

	private void initPlaneDrawer() {
		JSlider scaleSize = new JSlider(JSlider.HORIZONTAL, (int) MIN_SCALE, (int) MAX_SCALE, (int) scaleSizeSlider);
		scaleSize.setMajorTickSpacing(2);
		scaleSize.setMinorTickSpacing(1);
		scaleSize.setPaintLabels(true);
		scaleSize.setPaintTicks(true);
		scaleSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				scaleSizeSlider = (float) slider.getValue();
			}
		});

		JSlider amplify = new JSlider(JSlider.HORIZONTAL, (int) MIN_HEIGHT, (int) MAX_HEIGHT,
				(int) heightAmplifySlider);
		amplify.setMajorTickSpacing(4);
		amplify.setMinorTickSpacing(1);
		amplify.setPaintLabels(true);
		amplify.setPaintTicks(true);
		amplify.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				heightAmplifySlider = (float) slider.getValue();
			}
		});

		JSlider detail = new JSlider(JSlider.HORIZONTAL, MIN_DETAIL, MAX_DETAIL, detailSlider);
		detail.setMajorTickSpacing(30);
		detail.setMinorTickSpacing(5);
		detail.setPaintLabels(true);
		detail.setPaintTicks(true);
		detail.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				detailSlider = slider.getValue() + 1;
			}
		});

		JCheckBox wireframeBox = new JCheckBox("Wireframe Only");
		wireframeBox.setSelected(wireframe);
		wireframeBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JCheckBox box = (JCheckBox) e.getSource();
				wireframe = box.isSelected();
			}
		});
		
		JCheckBox autoRotateBox = new JCheckBox("Auto-Rotate");
		autoRotateBox.setSelected(autoRotate);
		autoRotateBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				JCheckBox box = (JCheckBox)e.getSource();
				autoRotate = box.isSelected();
			}
		});

		JPanel scalePanel = new JPanel();
		JPanel amplifyPanel = new JPanel();
		JPanel detailPanel = new JPanel();
		JPanel miscPanel = new JPanel();

		scalePanel.add(scaleSize);
		amplifyPanel.add(amplify);
		detailPanel.add(detail);
		miscPanel.add(wireframeBox);
		miscPanel.add(autoRotateBox);

		tabbedPane.add("Size", scalePanel);
		tabbedPane.add("Height Amplify", amplifyPanel);
		tabbedPane.add("Detail Level", detailPanel);
		tabbedPane.add("Misc.", miscPanel);
	}

	private void apply() {
		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setOctaves(octavesSlider);
				settings.setPersistance(persistenceSlider);

				if (settings instanceof PlaneDrawerSettings) {
					((PlaneDrawerSettings) settings).setScaleSize(scaleSizeSlider);
					((PlaneDrawerSettings) settings).setHeightAmplify(heightAmplifySlider);
					((PlaneDrawerSettings) settings).setDetailLevel(detailSlider);
					((PlaneDrawerSettings) settings).useWireframe(wireframe);
					((PlaneDrawerSettings) settings).setAutoRotate(autoRotate);
				}
				window.generate(true);
				setVisible(false);
			}
		});

		contentPane.add(tabbedPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(apply);
		
		if (settings instanceof PlaneDrawerSettings){
			JButton export = new JButton("Export Map");
			export.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!SaveMesh.WriteToFile(
							EXPORT_LOCATION + File.separator + "landscape.obj",
							SaveMesh.StringifyData(GUI.mapData)))
						return;
					
					JFrame popup = new JFrame();
					JLabel text = new JLabel();
					JPanel pane = new JPanel();
					JButton ok = new JButton("Ok");
					
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							popup.setVisible(false);
						}
					});
					
					Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
					popup.setBounds(screenSize.width / 2, screenSize.height / 2, 500, 200);
					popup.setLayout(new BorderLayout());
					pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));					
					text.setText("Saved!");
					
					pane.add(text);
					pane.add(ok);
					popup.getContentPane().add(pane);
					popup.pack();
					popup.setVisible(true);
				}
			});
			buttons.add(export);
		}
		
		contentPane.add(buttons);
		getContentPane().add(contentPane);
	}
}
