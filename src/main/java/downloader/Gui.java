package downloader;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class Gui {


	public Gui(){
		//create Frame
		JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Bandcamp Downloader");
		guiFrame.setSize(500,150);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		
		// Create text field and delete the tool tip text on mouseClicked
	    final JTextField linkAdress = new JTextField("Please enter Bandcamp link here...");
	    	    
	    linkAdress.addMouseListener(new MouseListener() {
			
			public void mouseReleased(java.awt.event.MouseEvent e) {}
			public void mousePressed(java.awt.event.MouseEvent e) {}
			public void mouseExited(java.awt.event.MouseEvent e) {}
			public void mouseEntered(java.awt.event.MouseEvent e) {}
			public void mouseClicked(java.awt.event.MouseEvent e) {
					linkAdress.setText("");
			}
		});
//	    
	    final JTextField directory = new JTextField("C:\\Music");
	    
		JButton downloadBut = new JButton("Download Songs");
		downloadBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
//				FileDialog fd = new FileDialog(guiFrame, "Save file...", FileDialog.SAVE);
				DownloadFiles.runDownload(linkAdress.getText(), directory.getText());
			}
		});

		//The JFrame uses the BorderLayout layout manager.
		//Put the two JPanels and JButton in different areas.
		guiFrame.add(linkAdress, BorderLayout.NORTH);
		guiFrame.add(directory, BorderLayout.SOUTH);
		guiFrame.add(downloadBut,BorderLayout.CENTER);
		//make sure the JFrame is visible
		guiFrame.setVisible(true);
		
	}
}
