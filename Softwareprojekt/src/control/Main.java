package control;

import java.util.Scanner;

import Cloudme.CloudmeException;
import Cloudme.CloudmeFolderNode;
import Cloudme.CloudmeUser;
import model.Downloader;
import model.Uploader;
import view.ConsoleView;


/**
 * 
 * Enthält die Main-Methode
 * 
 *
 */
public class Main {
	/**
	 * 
	 * Enthält die Umsetzung des Scanners und die Aufrufe der eigentlichen Modelle zur automatischen Speicherung
	 * 
	 */
	public static void main(String[] args) throws CloudmeException {
		CloudmeUser cloudmeUser = null;
		Scanner username = new Scanner(System.in);
		System.out.println("username: ");
		String user = username.nextLine();
		Scanner password = new Scanner(System.in);
		System.out.println("password: ");
		String pw = password.nextLine();
		
		try {
			cloudmeUser = new CloudmeUser(user, pw);
		} catch (CloudmeException cex) {
			System.out.println("\nInvalid username or password!");
			String[] arg = new String[0];
			main(args);
			
		}

		//Command list
		if (cloudmeUser.isAlive() == true) {
			System.out.println("\ntype 'browseFiles' to show all files" + "\ntype 'browseFolders' to show all folders" + "\ntype 'download' to download a file" 
					+ "\ntype 'downloadAll' to download all files stored on cloud" + "\ntype 'uploadEx' to upload a file to an existing folder" 
					+ "\ntype 'uploadNew' to upload a file to a new folder" + "\ntype 'uploadAll' to upload all files from selected folder" 
					+ "\ntype 'help' to show all the commands again" + "\ntype 'exit' to logout" + "\n");
			}

		Scanner scanner = new Scanner(System.in);
		String scan = scanner.nextLine();

		while (cloudmeUser.isAlive()) {
			if (scan.equalsIgnoreCase("browseFiles")) {
				// Finds the root folder
				CloudmeFolderNode node = cloudmeUser.getFolderManager().getFolderTree();
				ConsoleView.printAllFiles(cloudmeUser, node);
				System.out.println("\n");
			}
			
			else if (scan.equalsIgnoreCase("browseFolders")) {
				// Finds the root folder
				CloudmeFolderNode node = cloudmeUser.getFolderManager().getFolderTree();
				ConsoleView.printAllFolders(cloudmeUser, node);
				System.out.println("\n");
			}

			else if (scan.equalsIgnoreCase("uploadEx")) {
				
				Scanner scan1 = new Scanner(System.in);
				System.out.println("\nabsolute path of file to upload (format: /<path>/<filename.ending>; e.g.: /test/test.txt): "); // Format: C:\<Pfad>\<Dateiname>
				String from = scan1.nextLine();

				Scanner scan2 = new Scanner(System.in);
				System.out.println("cloudfolder to upload to: (format: /<path>/; e.g.: /Documents/) "); // Format: /<Pfad>/
				String to = scan2.nextLine();

				Uploader.uploadFileToExistingFolder(cloudmeUser, from, to);
				System.out.println("\n");
			}
			
			else if (scan.equalsIgnoreCase("uploadNew")) {
				
				Scanner scan1 = new Scanner(System.in);
				System.out.println("\nabsolute path of file to upload (format: /<path>/<filename.ending>; e.g.: /test/test.txt): "); // Format: C:\<Pfad>\<Dateiname>
				String from = scan1.nextLine();

				Scanner scan2 = new Scanner(System.in);
				System.out.println("cloudfolder to upload to (format: /<path>; e.g.: /Documents): "); // Format: /<Pfad>
				String to = scan2.nextLine();

				Uploader.uploadFileToNewFolder(cloudmeUser, from, to);
				System.out.println("\n");
			}

			else if (scan.equalsIgnoreCase("uploadAll")) {
	
				Scanner scan1 = new Scanner(System.in);
				System.out.println("\nabsolute path of directory to upload from (format: /<path>/; e.g.: /test/): ");
				String from = scan1.nextLine();
				
				Scanner scan2 = new Scanner(System.in);
				System.out.println("cloudfolder to upload to (format: /<path>/; e.g.: /Documents/): "); // Format: /<Pfad>/
				String to = scan2.nextLine();

				Uploader.uploadAllFiles(cloudmeUser, from, to);
				System.out.println("\n");
			}

			else if (scan.equalsIgnoreCase("download")) {

				Scanner scan1 = new Scanner(System.in);
				System.out.println("\nabsolute path of file to download fom cloud (format: /<path>/<filename.ending>; e.g.: /Documents/test.txt): ");
				String from = scan1.nextLine();

				Scanner scan2 = new Scanner(System.in);
				System.out.println("absolute path of download destination on hard drive (format: /<path>/<filename.ending>; e.g.: /test/test.txt): ");
				String to = scan2.nextLine();

				Downloader.downloadFile(cloudmeUser, from, to);
				System.out.println("\n");
			}

			else if (scan.equalsIgnoreCase("downloadAll")) { //"Access denied" problem

				Scanner scan1 = new Scanner(System.in);
				System.out.println("\nabsolute path of hard drrive directory to download to (format: /<path>/; e.g.: /test/): ");
				String to = scan1.nextLine();

				Downloader.downloadAllFiles(cloudmeUser, to);
				System.out.println("\n");
			}
			
			else if(scan.equalsIgnoreCase("help")) {
				System.out.println("\ntype 'browseFiles' to show all files" + "\ntype 'browseFolders' to show all folders" + "\ntype 'download' to download a file" 
						+ "\ntype 'downloadAll' to download all files stored on cloud" + "\ntype 'uploadEx' to upload a file to an existing folder" 
						+ "\ntype 'uploadNew' to upload a file to a new folder" + "\ntype 'uploadAll' to upload all files from selected folder" 
						+ "\ntype 'help' to show all the commands again" + "\ntype 'exit' to logout" + "\n");
				}

			else if (scan.equalsIgnoreCase("exit")) {
				cloudmeUser.killUser();
				System.out.println("logging out");
			}

			else {
				System.out.println("please try again");
				System.out.println("\n");
			}
			scan = scanner.nextLine();
		}

	}
}
