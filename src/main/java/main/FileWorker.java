package main;

import java.io.*;

public class FileWorker {
	public static String getUserString() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String userString = reader.readLine();

		return userString;
	}

	public static void createFile(String filename) throws Exception{
		try {
			File f = new File(filename);
			if (f.createNewFile())
				System.out.println("File created");
			else
				System.out.println("File already exists");
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void writeStringToFile(String filename, String string) throws Exception {
		try {
			FileWriter fw=new FileWriter(filename);
			fw.write(string);
			fw.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public static String getFileContents(String filename) throws Exception {
		String fileContents = "";

		try	{
			FileReader fr = new FileReader(filename);
			int i;
			while((i = fr.read()) != -1)
				fileContents += (char)i;
			fr.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return fileContents;
	}

	public static boolean deleteFile(String filename) throws Exception {
		boolean fileDeleted = false;

		try {
			File file = new File(filename);

			fileDeleted = file.delete();
		} catch (Exception e) {
			System.out.println(e);
		}

		return fileDeleted;
	}
}
