package model;

import Cloudme.CloudmeException;
import Cloudme.CloudmeFile;
import Cloudme.CloudmeFolder;
import Cloudme.CloudmeFolderNode;
import Cloudme.CloudmeUser;

/**
 * 
 * Enthält Methoden, die den Download von Dateien aus der Cloud auf die lokale Festplatte ermöglichen
 *
 */
public class Downloader {

	// Try to find the file by name and download it to a target directory given as a String
	/**
	 * Läd eine Datei aus der Cloud in einen bereits bestehenden Ordner auf der lokalen Festplatte herunter
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param nameFileDownload Ist der absolute Pfad der Datei, die aus der Cloud heruntergeladen werden soll
	 * @param nameFileTarget Ist der absolute Pfad auf der lokalen Festplatte, an den die Datei heruntergeladen werden soll
	 */
	public static void downloadFile(CloudmeUser cloudmeUser, String nameFileDownload, String nameFileTarget) {
		CloudmeFile cloudmeFile = null;

		// Try to find the file
		try {
			cloudmeFile = cloudmeUser.getFileManager().getFile(nameFileDownload);
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}

		// If file was found and is not null download it to the target directory
		if(cloudmeFile != null) {
			try {
				cloudmeFile.downloadFile(nameFileTarget);
			} catch (CloudmeException cex) {
				cex.printStackTrace();
			}
		}
	}
	
	// Method to download all files on the cloud storage at once
	/**
	 * Läd alle Dateien aus der Cloud in ein Verzeichnis auf der lokalen Festplatte herunter (nicht funktionsfähig)
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param namePathTarget Ist der absolute Pfad des Verzeichnisses auf der lokalen Festplatte, in das die Dateien heruntergeladen werden sollen
	 */
	public static void downloadAllFiles(CloudmeUser cloudmeUser, String namePathTarget) {
		// Finds the root folder
		CloudmeFolderNode node = cloudmeUser.getFolderManager().getFolderTree();

		// Try-Catch catches exceptions thrown by the used functions and prints a detailed exception report
		try {
			// Downloads all files in current directory
			for(CloudmeFile cloudmeFile : node.getFolder().getFiles()) {
				downloadFile(cloudmeUser, node.getFolder().getPath() + cloudmeFile.getMetadata().getName(), namePathTarget);
			}

			// Downloads all files in each subdirectory
			for(CloudmeFolder cloudmeFolder : node.getFolder().getChildFolders()) {
				for(CloudmeFile cloudmeFile : cloudmeFolder.getFiles()) {
					downloadFile(cloudmeUser, cloudmeFolder.getPath() + cloudmeFile.getMetadata().getName(), namePathTarget);
				}
			}
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}
	}
	
	
}
