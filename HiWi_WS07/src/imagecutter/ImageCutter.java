package src.imagecutter;import java.awt.Dimension;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import javax.swing.JButton;import javax.swing.JFrame;import javax.swing.JPanel;import javax.swing.JTextField;import javax.swing.SpringLayout;import src.gui.HiWi_GUI;import src.gui.HiWi_GUI_explorer;import src.util.image.ImageUtil;import src.util.spring.SpringUtilities;public class ImageCutter extends JFrame implements ActionListener{		HiWi_GUI root;		HiWi_GUI_explorer explorer;		JTextField id = new JTextField();		JButton ok = new JButton("OK");	JButton cancel = new JButton("Cancel");		public ImageCutter(HiWi_GUI r){		super("Image Cutter");		this.root = r;				explorer = new HiWi_GUI_explorer(this.root, false);				JPanel buttons = new JPanel(new SpringLayout());		buttons.add(ok);		buttons.add(cancel);		ok.addActionListener(this);		cancel.addActionListener(this);		SpringUtilities.makeCompactGrid(buttons, 1, 2, 0, 0, 0, 0);				this.setLocation(100, 100);		this.setPreferredSize(new Dimension(300, 400));		this.setVisible(true);				JPanel all = new JPanel(new SpringLayout());		all.add(explorer);		all.add(new JPanel(new SpringLayout()).add(id));		all.add(buttons);				SpringUtilities.makeCompactGrid(all, 3,1, 0,0,0,0);		//SpringUtilities.makeGrid(this, 1,3, 0,0,0,0);				add(all);				pack();	}	@Override	public void actionPerformed(ActionEvent e) {		String cmd = e.getActionCommand();		if(cmd.equals(ok.getActionCommand())){			String inscriptId = id.getText();			String imageCollection = explorer.selectedCollection;			String imageResource = explorer.selectedResource;						ImageUtil.cutImageDueCoordinates(root, imageCollection, imageResource, inscriptId);			//ImageUtil.saveSubimages(root, ImageUtil.cutImageDueCoordinates(root, imageCollection, imageResource, inscriptId));		}		if(cmd.equals(cancel.getActionCommand())){			dispose();		}	}}