package view;

import Cloudme.CloudmeException;
import Cloudme.CloudmeFile;
import Cloudme.CloudmeFolder;
import Cloudme.CloudmeFolderNode;
import Cloudme.CloudmeUser;

/**
 * 
 * Enthält Methoden, die Ordner und Dateien auf der Konsole ausgeben
 *
 */
public class ConsoleView {
	/**
	 * Gibt alle Dateien auf der Cloud mit absolutem Pfad aus
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param node Wird in der Main-Methode beim Aufruf übergeben, um den Root-Ordner festzustellen
	 */
	public static void printAllFiles(CloudmeUser cloudmeUser, CloudmeFolderNode node) {

		// Try-Catch catches exceptions thrown by the used functions and prints a detailed exception report
		try {
			// Prints all files in current directory
			for(CloudmeFile cloudmeFile : node.getFolder().getFiles()) {
				
				System.out.println(node.getFolder().getPath() + cloudmeFile.getMetadata().getName());
			}

			// Prints all files in each subdirectory
			for(CloudmeFolderNode cloudmeFolder : node.getChildren()) {
				printAllFiles(cloudmeUser, cloudmeFolder);
			}		
			
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}
	}
	
	/**
	 * Gibt alle Ordner und Unterordner, die sich auf der Cloud befinden, aus
	 * @param cloudmeUser Beinhaltet die Anmeldedaten für die Cloud
	 * @param node Wird in der Main-Methode beim Aufruf übergeben, um den Root-Ordner festzustellen
	 */
	public static void printAllFolders(CloudmeUser cloudmeUser, CloudmeFolderNode node) {

		try {
			// Print all folders in current directory
			for(CloudmeFolder n : node.getFolder().getChildFolders())
			{
				System.out.println(n.getPath());
			}
			
			// Prints all folders in each subdirectory
			for(CloudmeFolderNode cloudmeFolder : node.getChildren()) {
					printAllFolders(cloudmeUser, cloudmeFolder);
			}		
						
					
			
		} catch (CloudmeException cex) {
			cex.printStackTrace();
		}
	}

}
