package main;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Disk {
		private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

		public static List<Map<String, String>> getInfo() {

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			File[] roots = File.listRoots (); // Получить список разделов диска
			for (File file : roots) {
				Map<String, String> map = new HashMap<String, String>();

				long freeSpace=file.getFreeSpace();
				long totalSpace=file.getTotalSpace();
				long usableSpace=totalSpace-freeSpace;

				map.put("path", file.getPath());
				map.put ("freeSpace", freeSpace / 1024/1024/1024 + "G"); // Свободное пространство
				map.put ("usableSpace", usableSpace / 1024/1024/1024 + "G"); // Доступное пространство
				map.put ("totalSpace", totalSpace / 1024/1024/1024 + "G"); // общее пространство
				map.put ("проценты", DECIMALFORMAT.format (((double) usableSpace / (double) totalSpace) * 100) + "%"); // общее пространство

				list.add(map);
			}

			return list;
		}
	}
