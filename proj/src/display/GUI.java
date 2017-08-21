package display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import perlinNoise.NoiseGenerator;
import terrain.SettingsWindow;

public class GUI implements AppWindow {
	private JFrame window;
	private JPanel windowContent;
	private String windowName;
	private Dimension windowSize;

	private PlaneDrawer planeDrawer;
	private PlaneDrawerSettings settings;
	private SettingsWindow settingsWindow;
	private NoiseGenerator generator;

	private long elevationSeed;
	private long temperatureSeed;
	private long moistureSeed;

	public GUI(String windowName, Dimension windowSize) {
		this.windowName = windowName;
		this.windowSize = windowSize;
		elevationSeed = 0;
		temperatureSeed = 0;
		moistureSeed = 0;
	}

	@Override
	public void init() {
		initWindow();
		initContent();
		initPanels();
	}

	@Override
	public void show() {
		window.setVisible(true);
	}

	@Override
	public void generate(boolean keepseed) {
		Random rand = new Random();
		PlaneDrawerSettings pds = (PlaneDrawerSettings) settingsWindow.getSettings();

		if (!keepseed) {
			elevationSeed = rand.nextLong();
			temperatureSeed = rand.nextLong();
			moistureSeed = rand.nextLong();
		}

		pds.setSeed(elevationSeed);
		generator.changeSettings(pds);
		float[][] elevation = generator.generatePerlinNoise(pds.getOctaves());

		pds.setSeed(temperatureSeed);
		generator.changeSettings(pds);
		float[][] temperature = generator.generatePerlinNoise(pds.getOctaves());

		pds.setSeed(moistureSeed);
		generator.changeSettings(pds);
		float[][] moisture = generator.generatePerlinNoise(pds.getOctaves());

		planeDrawer.setNoiseMaps(elevation, temperature, moisture);
		planeDrawer.redraw(pds.useWireframe(), pds.getAutoRotate(),
				pds.getHeightAmplify(), pds.getScaleSize());

		windowContent.getComponent(0).revalidate();
	}

	private void initWindow() {
		window = new JFrame(windowName);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(windowSize.getSize());
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		window.setBounds((int) (screenSize.width / 2 - (windowSize.getWidth() / 2)),
				(int) (screenSize.height / 2 - (windowSize.getHeight() / 2)), (int) windowSize.getWidth(),
				(int) windowSize.getHeight());
	}

	private void initContent() {
		try {
			Random rand = new Random();
			elevationSeed = rand.nextLong();
			temperatureSeed = rand.nextLong();
			moistureSeed = rand.nextLong();

			settings = new PlaneDrawerSettings();
			settings.setSeed(elevationSeed);
			settings.setScaleSize(7.0f);
			settings.setDetailLevel(35);
			generator = new NoiseGenerator(settings.getSeed(), settings.getArrWidth(), settings.getArrHeight());

			float[][] elevation = generator.generatePerlinNoise(settings.getOctaves());

			settings.setSeed(temperatureSeed);
			generator.changeSettings(settings);
			float[][] temperature = generator.generatePerlinNoise(settings.getOctaves());

			settings.setSeed(moistureSeed);
			generator.changeSettings(settings);
			float[][] moisture = generator.generatePerlinNoise(settings.getOctaves());

			planeDrawer = new PlaneDrawer(new TerrainMap(elevation, temperature, moisture));
			planeDrawer.init(settings.useWireframe(), settings.getAutoRotate(),
					settings.getHeightAmplify(), settings.getScaleSize());
			settingsWindow = new SettingsWindow(settings, this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPanels() {
		windowContent = new JPanel();
		JPanel buttons = new JPanel();

		windowContent.setLayout(new BoxLayout(windowContent, BoxLayout.Y_AXIS));
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JButton regenerate = new JButton("Generate");
		regenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generate(false);
			}
		});

		JButton settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsWindow.setVisible(true);
			}
		});

		buttons.add(regenerate);
		buttons.add(settingsButton);

		windowContent.add(planeDrawer);
		windowContent.add(buttons);

		window.getContentPane().add(windowContent);
	}
}
