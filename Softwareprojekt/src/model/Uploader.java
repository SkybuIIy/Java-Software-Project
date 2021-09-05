package model;

import java.io.File;

import Cloudme.CloudmeException;
import Cloudme.CloudmeFolder;
import Cloudme.CloudmeUser;

/**
 * 
 * Enthält Methoden, die das Hochladen von Dateien auf die Cloud ermöglichen
 *
 */
public class Uploader {
	//Upload to an existing folder
	/**
	 * Läd eine Datei von der lokalen Festplatte in einen bereits bestehenden Ordner auf der Cloud hoch
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param file Ist der absolute Pfad der Datei, die hochgeladen werden soll
	 * @param folder Ist der absolute Pfad des bereits bestehenden Ordners auf der Cloud, in den die Datei hochgeladen werden soll
	 */
	public static void uploadFileToExistingFolder(CloudmeUser cloudmeUser, String file, String folder) {
		
		try {
			cloudmeUser.getFileManager().uploadFile(file, folder);
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}
	}

	//Upload to a new folder
	/**
	 * Läd eine Datei von der lokalen Festplatte in einen neuen Ordner auf der Cloud hoch
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param file Ist der absolute Pfad der Datei, die hochgeladen werden soll
	 * @param folder Ist der absolute Pfad des neuen Ordners auf der Cloud, in den die Datei hochgeladen werden soll
	 */
	public static void uploadFileToNewFolder(CloudmeUser cloudmeUser, String file, String folder) {
		CloudmeFolder cloudmeFolder = null;
		//Creating the new folder
		try {
			cloudmeFolder = cloudmeUser.getFolderManager().newFolder(folder);
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}
		//Actual upload
		if(cloudmeFolder != null) {
			try {
				System.out.println(file);
				cloudmeFolder.uploadFile(file);
			} catch (CloudmeException cex) {
				cex.printStackTrace();
			}
		}
	}
	
	//Upload all files
	/**
	 * Läd alle Dateien aus einem Verzeichnis auf der lokalen Festplatte in einen bestehenden Ordner auf der Cloud hoch
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param sourcePath Ist der absolute Pfad des Verzeichnisses auf der lokalen Festplatte, aus dem hochgeladen werden soll
	 * @param destPath Ist der absolute Pfad des Ordners der Cloud, in den hochgeladen werden soll
	 */
	public static void uploadAllFiles(CloudmeUser cloudmeUser, String sourcePath, String destPath) { 
		File folder = new File(sourcePath);
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            uploadAllFiles(cloudmeUser, fileEntry.getPath(), destPath);
	        } else {
	            System.out.println(fileEntry.getAbsolutePath());
	            // Upload has to be to an existing folder, else error with line 46 (has to be null)
	        	uploadFileToExistingFolder(cloudmeUser, fileEntry.getAbsolutePath(), destPath); 
	        }
	    }
	}
}
