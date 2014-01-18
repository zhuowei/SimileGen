package net.zhuoweizhang.similegen;

import java.io.*;
import java.util.*;
import org.json.*;

public class TweetsParser {

	private List<JSONArray> statusMonths = new ArrayList<JSONArray>();
	private File sourceDir;

	public TweetsParser(File file) {
		this.sourceDir = file;
	}

	public TweetsParser readAll() throws IOException, JSONException {
		//List all CSV files
		//for each CSV:
		//read first line
		//put first line indices into list
		int i = 0;
		for (File f: sourceDir.listFiles()) {
			parseOneJson(f);
			if (i++ > 3) break;
		}
		return this;
	}

	private void parseOneJson(File infile) throws IOException, JSONException {
		FileReader fileIn = new FileReader(infile);
		BufferedReader buf = new BufferedReader(fileIn);
		buf.readLine(); //first line is for JSONP
		System.out.println(infile);
		JSONArray arr = new JSONArray(new JSONTokener(buf));
		statusMonths.add(arr);
	}

	public List<String> strings() throws JSONException {
		List<String> myList = new ArrayList<String>();
		for (JSONArray arr: statusMonths) {
			int arrLength = arr.length();
			for (int i = 0; i < arrLength; i++) {
				JSONObject obj = arr.getJSONObject(i);
				myList.add(obj.getString("text"));
			}
		}
		return myList;
	}
}
