package cst8284.lab09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FileIO {

	private static int numberOfStringsLoaded;

	public static ArrayList<String> loadArrayListFromFile(File f) {
		ArrayList<String> list = new ArrayList<String>();
		if (fileExists(f)) {
			String line;
			try (BufferedReader input = new BufferedReader(new FileReader(f))) {
				while ((line = input.readLine()) != null) {
					list.add(line);
					numberOfStringsLoaded++;
				}

			} catch (IOException e) {
				return null;
			}
		}
		return list;
	}

	public static boolean fileExists(File f) {
		if (f != null)
			if (f.exists())
				if (f.isFile())
					// https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#isReadable-java.nio.file.Path-
					if (Files.isReadable(f.toPath()))
						return true;
		return false;
	}

	public static String toStringFromArrayList(ArrayList<String> arStr) {
		StringBuilder sb = new StringBuilder();
		for (String s : arStr) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}

	public static String toStringFileIO(File f) {
		return String.format("File Name:%s\nFile Size:%d\nDate Modified:%s\nNumber of Strings Download: %d",
				f.getName(), f.length(), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(f.lastModified()),
				numberOfStringsLoaded);
	}

}
