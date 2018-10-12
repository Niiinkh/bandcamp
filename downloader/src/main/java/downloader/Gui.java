package downloader;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {


	public Gui(){
		JFrame guiFrame = new JFrame();
		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Bandcamp Downloader");
		guiFrame.setSize(500,100);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		
	    final JTextField linkAdress = new JTextField("Enter Bandcamp link here...");
	    
		JButton downloadBut = new JButton("Download Songs");
		downloadBut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) {
				MainBandcamp.runDownload(linkAdress.getText());
			}
		});

		//The JFrame uses the BorderLayout layout manager.
		//Put the two JPanels and JButton in different areas.
		guiFrame.add(linkAdress, BorderLayout.NORTH);
		guiFrame.add(downloadBut,BorderLayout.SOUTH);
		//make sure the JFrame is visible
		guiFrame.setVisible(true);
		
	}
}
