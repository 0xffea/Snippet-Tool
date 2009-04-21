package org.abratuhi.snippettool.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.abratuhi.snippettool.model.SnippetTool;
import org.abratuhi.snippettool.util.PrefUtil;

/**
 * Snippet-tool application window. Contains all other components and a
 * reference to Inscript Object Reference to HiWi_GUI is contained in every
 * sub-component to provide the possibility to influence one component from the
 * other.
 * 
 * @author Alexei Bratuhin
 * 
 */
@SuppressWarnings("serial")
public class _frame_SnippetTool extends JFrame implements WindowListener {

	public SnippetTool snippettool;
	public Properties preferences;

	/** Menubar **/
	public _menubar_SnippetTool menubar;

	/** Main panel, marking is done here **/
	public _panel_Main main;

	/** Text panel, inscript text is shown here **/
	public _panel_Text text;

	/**
	 * Log panel, all log messages are shown here (except for exceptions and
	 * errors that occure)
	 **/
	public _panel_Status status;

	/**
	 * Explorer panel, tree-like explorer of eXist database for selecting and
	 * loading resources
	 **/
	public _panel_Explorer explorer;

	/**
	 * Options panel, all adjustments for automatic marking, opacities, colors,
	 * text direction and displayed information are done here
	 **/
	public _panel_Options options;

	/** Info panel, information about currently selected character is shown here **/
	public _panel_Info info;

	public _frame_SnippetTool(SnippetTool snippettool) {
		super();
		this.snippettool = snippettool;
		preferences = snippettool.prefs;
	}

	public void createAndShowGUI() {

		// initialize subparts
		menubar = new _menubar_SnippetTool(this, snippettool);
		main = new _panel_Main(this, snippettool);
		text = new _panel_Text(this, snippettool);
		status = new _panel_Status();
		explorer = new _panel_Explorer(this, snippettool, true);
		options = new _panel_Options(this, snippettool);
		info = new _panel_Info(snippettool);

		// construct GUI as consisting of JSplitPanes
		setJMenuBar(menubar);

		JSplitPane mainoption = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				main, options);
		JSplitPane up = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorer,
				mainoption);
		JSplitPane down = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, text,
				info);
		JSplitPane all = new JSplitPane(JSplitPane.VERTICAL_SPLIT, up, down);

		mainoption.setBorder(null);
		up.setBorder(null);
		down.setBorder(null);
		down.setBorder(null);
		all.setBorder(null);

		mainoption.setDividerSize(PrefUtil.string2integer(preferences
				.getProperty("local.window.divider.width")));
		up.setDividerSize(PrefUtil.string2integer(preferences
				.getProperty("local.window.divider.width")));
		down.setDividerSize(PrefUtil.string2integer(preferences
				.getProperty("local.window.divider.width")));
		down.setDividerSize(PrefUtil.string2integer(preferences
				.getProperty("local.window.divider.width")));
		all.setDividerSize(PrefUtil.string2integer(preferences
				.getProperty("local.window.divider.width")));

		// construct jframe
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(all, BorderLayout.CENTER);
		getContentPane().add(status, BorderLayout.SOUTH);

		setTitle("Snippet-Tool");

		// load user set layout
		loadLayout();

		// load font
		text.text_in.setFont(snippettool.inscript.getFont());
		info.jta_info.setFont(snippettool.inscript.getFont());

		// add own windowlistener
		addWindowListener(this);

		//
		setVisible(true);
		pack();
	}

	public void status(String status) {
		this.status.setStatus(status);
		this.status.repaint();
	}

	/**
	 * Save Layout of application window. Saved are the sizes of each of JPanel
	 * parts of GUI
	 */
	public void saveLayout() {
		String point_window = PrefUtil.point2string(this.getLocation());
		String dim_window = PrefUtil.dimension2string(this.getSize());
		String dim_main = PrefUtil.dimension2string(main.getSize());
		String dim_explorer = PrefUtil.dimension2string(explorer.getSize());
		String dim_options = PrefUtil.dimension2string(options.getSize());
		String dim_text = PrefUtil.dimension2string(text.getSize());
		String dim_info = PrefUtil.dimension2string(info.getSize());
		String dim_status = PrefUtil.dimension2string(status.getSize());

		preferences.setProperty("local.window.position", point_window);
		preferences.setProperty("local.window.size", dim_window);
		preferences.setProperty("local.window.main.size", dim_main);
		preferences.setProperty("local.window.explorer.size", dim_explorer);
		preferences.setProperty("local.window.options.size", dim_options);
		preferences.setProperty("local.window.text.size", dim_text);
		preferences.setProperty("local.window.info.size", dim_info);
		preferences.setProperty("local.window.status.size", dim_status);

	}

	/**
	 * Load Layout of application window. Size of each JPanel part of GUI is
	 * restores from previous session
	 */
	public void loadLayout() {
		setLocation(PrefUtil.string2point(preferences
				.getProperty("local.window.position")));
		setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.size")));
		main.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.main.size")));
		explorer.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.explorer.size")));
		options.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.options.size")));
		text.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.text.size")));
		info.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.info.size")));
		status.setPreferredSize(PrefUtil.string2dimesion(preferences
				.getProperty("local.window.status.size")));
	}

	public void exit() {
		saveLayout();
		snippettool.exit();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		exit();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	public static void main(String[] args) {
		StartGUI.main(args);
	}

}
