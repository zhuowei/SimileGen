package net.zhuoweizhang.similegen;

import java.io.*;
import java.util.*;

public class SimilePrepare {

	public static String cleanWord(String input) {
		String output = input;
		int index = input.lastIndexOf(".");
		if (index >= 0) {
			output = input.substring(0, index);
		}
		return output.replace("[!]", "");
	}

	public static void similePrepare(File inputFile, File outputFile) throws IOException {
		Map<String, List<String>> mystrings = new HashMap<String, List<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line;
		while((line = reader.readLine()) != null) {
			//split the CSV
			String[] parts = line.split(",");
			String adj = cleanWord(parts[0]);
			String word = cleanWord(parts[1]);
			List<String> swordList = mystrings.get(adj);
			if (swordList == null) {
				swordList = new ArrayList<String>();
				mystrings.put(adj, swordList);
			}
			if (!swordList.contains(word)) {
				swordList.add(word);
			}
		}

		//now, for every adj, print out in list
		List<Map.Entry<String, List<String>>> entryList = new ArrayList<Map.Entry<String, List<String>>>(mystrings.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry>() {
			public int compare(Map.Entry a, Map.Entry b) {
				return ((Comparable) a.getKey()).compareTo(b.getKey());
			}
			public boolean equals(Map.Entry a, Map.Entry b) {
				return a.getKey().equals(b.getKey());
			}
		});

		PrintWriter writer = new PrintWriter(outputFile);
		for (Map.Entry<String, List<String>> item: entryList) {
			String adj = item.getKey();
			List<String> words = item.getValue();
			if (words.size() < 2) {
				continue;
			}
			//join the words list
			StringBuilder builder = new StringBuilder();
			builder.append(adj);
			builder.append(",");
			for (int i = 0; i < words.size(); i++) {
				builder.append(words.get(i));
				if (i < words.size() - 1) {
					builder.append(",");
				}
			}
			writer.println(builder.toString());
		}
	}

	public static void main(String[] args) {
		try {
			similePrepare(new File(args[0]), new File(args[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
