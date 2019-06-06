package downloader;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class MainBandcamp extends PApplet {

	private PImage cover;
	private PImage saveFile;
	private PImage download;

	private String artistName = "Phosphorescent";
	private String albumTitle = "C’est La Vie";
	private String releaseDate = "05 October 2018";
	private int numberOfSong = 9;

	private ControlP5 cp5;
	private String textValue = "";
	private PFont font;
	private PFont large;

	private String validatedLink;
	private boolean controlPressed;

	public void settings() {
		size(800, 400);
	}

	public void setup() {
		background(255);
		stroke(0);
		textAlign(LEFT, CENTER);
		fill(0);

//		font = createFont("Arial", 16);
		font = createFont("aqua.ttf", 16);
		large = createFont("aqua.ttf", 32);

		textFont(font);

		cover = loadImage("cover_p.jpg");
		saveFile = loadImage("saveFile.png");
		download = loadImage("download.png");

		cp5 = new ControlP5(this);

		cp5.addTextfield("input").setPosition(width / 30 + 100, height / 8 - 15).setSize(width - width / 15 - 100, 40)
				.setText("enter bandcamp link here...").setFont(font).setFocus(false).setColor(255)
				.setCaptionLabel("Try this, mofo").setAutoClear(true)
//	    .setColorBackground(0)
		;

		validatedLink = "";
		controlPressed = false;
	}

	public void draw() {

		createBaseUI();

		// Get input from text field
		String text = cp5.get(Textfield.class, "input").getText();
		if (cp5.get(Textfield.class, "input").isFocus()) {
			if (text.equals("enter bandcamp link here...")) {
				cp5.get(Textfield.class, "input").setText("");
			}
			if (text.contains(".bandcamp.com")) {
				validatedLink = text;
			} else {
				validatedLink = "";
			}
		}

	}

	private void createBaseUI() {
		// create upper part of the ui
		pushStyle();
		fill(255);
		noStroke();
		rect(width / 30 + 10, height / 16, 70, 50);
		popStyle();
		pushStyle();
		textFont(large);
		text("Link:", width / 30 + 10, height / 8 + 5);
		popStyle();

		// Create separating line
		pushStyle();
		strokeWeight(3);
		line(width / 30, height / 4, width - width / 30, height / 4);
		line(width / 30, height * 4 / 5, width - width / 30, height * 4 / 5);
		popStyle();

		// Album Cover
		int coverSize = 150;
		pushStyle();
		image(cover, width / 30 + 1, height * 1 / 4 + 10 + 1, coverSize - 2, coverSize - 2);
		noFill();
		strokeWeight(2);
		rect(width / 30, height * 1 / 4 + 10, coverSize, coverSize);
		popStyle();
		// Artist Info
		// Background Box
		pushStyle();
		fill(255);
		noStroke();
		rect(width / 30 + coverSize + 20, height * 1 / 4 + 10, coverSize * 2.5f, coverSize);
		popStyle();
		// TAGS
		pushStyle();
		textAlign(CENTER, CENTER);
		strokeWeight(2);
		text("Artist", width / 30 + 1.5f * coverSize, height / 4 + 40);
		text("Album Title", width / 30 + 1.5f * coverSize, height / 4 + 70);
		text("Release Date", width / 30 + 1.5f * coverSize, height / 4 + 100);
		text("No. of Songs", width / 30 + 1.5f * coverSize, height / 4 + 130);
		popStyle();
		// INFORMATION
		text(artistName, width / 30 + 320, height / 4 + 40);
		text(albumTitle, width / 30 + 320, height / 4 + 70);
		text(releaseDate, width / 30 + 320, height / 4 + 100);
		text(numberOfSong, width / 30 + 320, height / 4 + 130);

		// Create File Dialog
		int size = 50;
		pushStyle();
		fill(255);
		rect(width / 30, height * 5 / 6, size, size);
		image(saveFile, width / 30 + 4, height * 5 / 6 + 4, size - 6, size - 6);
		popStyle();

		// Create Download Button
		pushStyle();
		image(download, width * 8 / 9, height * 3 / 4 - 4, size, size);
		popStyle();
	}

	public void mouseClicked() {
		// Save File
		if (mouseX > width / 30 && mouseX < width / 30 + 50) {
			if (mouseY > height * 2 / 3 && mouseY < height * 2 / 3 + 50) {
				selectFolder("Safe file:", "folderSelected");
			}
		}
		// Download
		if (mouseX > width * 8 / 9 && mouseX < width * 8 / 9 + 50) {
			if (mouseY > height * 3 / 4 - 4 && mouseY < height * 3 / 4 + 46) {
				if (!validatedLink.equals("")) {
					System.out.println(validatedLink);
					testLinkValidity(validatedLink);
				}
				if (!validatedLink.equals("")) {
					DownloadFiles.runDownload(validatedLink, "C:\\Music");
				}
			}
		}
	}

	public void keyPressed() {
		// Paste
		if (controlPressed && keyCode == 86) {
			if (cp5.get(Textfield.class, "input").isFocus()) {
				String text = getTextFromClipboard();
				int end = text.indexOf("\n");
				text = text.substring(0, end);
				cp5.get(Textfield.class, "input").setText(text);
				if (testLinkValidity(text)) {
					validatedLink = text;
				}
			}
		}
		// set flag for ctrl pressed
		if (keyCode == 17) {
			controlPressed = true;
		}
	}

	public void keyReleased() {
		// remove flag for ctrl pressed
		if (keyCode == 17) {
			controlPressed = false;
		}
	}

	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isAssignableFrom(Textfield.class)) {
			println("controlEvent: accessing a string from controller '" + theEvent.getName() + "': "
					+ theEvent.getStringValue());
		}
	}

	void folderSelected(File selection) {
		if (selection == null) {
			println("Window was closed or the user hit cancel.");
		} else {
			println("User selected " + selection.getAbsolutePath());
		}
	}

	private boolean testLinkValidity(String URLAdress) {
		try {
			URL url = new URL(URLAdress);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			int statusCode = connection.getResponseCode();
			System.out.println(statusCode);

			return true;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	
	String getTextFromClipboard() {
		String text = (String) getFromClipboard(DataFlavor.stringFlavor);
		return text;
	}

	Object getFromClipboard(DataFlavor flavor) {

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		Object object = null;

		if (contents != null && contents.isDataFlavorSupported(flavor)) {
			try {
				object = contents.getTransferData(flavor);
			}

			catch (UnsupportedFlavorException e1) // Unlikely but we must catch it
			{
				e1.printStackTrace();
			}

			catch (java.io.IOException e2) {
				e2.printStackTrace();
			}
		}

		return object;
	}
	
	


	public static void main(String[] args) {
//		PApplet.main("downloader.MainBandcamp");
//		String URLAdress = "https://siskiyou.bandcamp.com/album/not-somewhere";
		String URLAdress = "https://amylandthesniffers.bandcamp.com/album/giddy-up";
		String saveFileDirectory = "C:/Users/AAboueldahab/Downloads/Privat/Musik";
		DownloadFiles.runDownload(URLAdress, saveFileDirectory);
	}

}
