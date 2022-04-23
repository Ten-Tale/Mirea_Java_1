package main;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
	public static void main(String[] args) throws Exception{

		// 1
		System.out.println(Disk.getInfo());

		// 2
		try {
			String filename = "test.txt";

			FileWorker.createFile(filename);

			String userString = FileWorker.getUserString();

			FileWorker.writeStringToFile(filename, userString);

			String fileContent = FileWorker.getFileContents(filename);
			System.out.println(fileContent);

			FileWorker.deleteFile(filename);

		} catch (Exception e) {
			System.out.println(e);
		}


		// 3
		try {
			String filename = "testJSON.json";

			FileWorker.createFile(filename);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "19B0544");
			jsonObject.put("name", "Andrey Shkunov");

			String result = jsonObject.toJSONString();
			FileWorker.writeStringToFile(filename, result);

			String fileContents = FileWorker.getFileContents(filename);
			System.out.println((JSONObject) new JSONParser().parse(fileContents));

			FileWorker.deleteFile(filename);

		} catch (Exception e) {
			System.out.println(e);
		}

		// 4
		try {
			String filename = "testXML.xml";
			FileWorker.createFile(filename);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			String instrumentName = FileWorker.getUserString();
			String instrumentBrand = FileWorker.getUserString();

			Element root = doc.createElement("Instruments");
			Element instrument = doc.createElement("Instrument");
			Element type = doc.createElement("type");
			Element brand = doc.createElement("brand");

			type.setTextContent(instrumentName);
			brand.setTextContent(instrumentBrand);

			instrument.appendChild(type);
			instrument.appendChild(brand);
			root.appendChild(instrument);

			try (FileOutputStream output = new FileOutputStream(filename)) {
				writeXml(doc, output);

			} catch (Exception e) {
				System.out.println(e);
			}


		} catch (Exception e) {
			System.out.println(e);
		}

		// 5
		try {
			String path = "testZip.zip";
			String file = "zip.txt";

			String unZipFilename = "testZipNew.txt";

			FileOutputStream fos = new FileOutputStream(path);
			ZipOutputStream zipOS = new ZipOutputStream(fos);
			ZipWorker.WriteZip(file, zipOS);
			zipOS.close();
			fos.close();

			String destDirectory = "/zip";
			File destDir = new File(destDirectory);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(path));
			ZipEntry entry = zipIn.getNextEntry();
			while (entry != null) {
				String filePath = destDirectory + File.separator + unZipFilename;
				if (!entry.isDirectory()) {
					ZipWorker.unZip(zipIn, filePath);
				} else {
					File dir = new File(filePath);
					dir.mkdirs();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();

			ZipInputStream zipIs = null;
			zipIs = new ZipInputStream(new FileInputStream(path));
			entry = null;
			while ((entry = zipIs.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					System.out.print("Directory: ");
				} else {
					System.out.print("Zip contains: ");
				}
				System.out.println(entry.getName());
			}
			zipIs.close();

			FileWorker.deleteFile(path);
			FileWorker.deleteFile(unZipFilename);

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private static void writeXml(Document doc, OutputStream output) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);
	}
}
