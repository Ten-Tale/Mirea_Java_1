package main;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipWorker {
	private static final int BUFFER = 2048;

	public static void WriteZip(String filename, ZipOutputStream zipStream) throws Exception {
		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			ZipEntry entry = new ZipEntry(filename);
			zipStream.putNextEntry(entry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipStream.write(bytes, 0, length);
			}
			zipStream.closeEntry();
			fis.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void unZip(ZipInputStream inputStream, String filename) throws Exception {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename));

			byte[] bytesIn = new byte[BUFFER];
			int read = 0;
			while ((read = inputStream.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
