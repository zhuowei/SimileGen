package net.zhuoweizhang.similegen;
import java.io.*;
import java.util.*;
public class SimileGen {

	public static void main(String[] args) {
		try {
			File myDict = new File(args[0]);
			List<String> strings = new TweetsParser(myDict).readAll().strings();
			System.out.println(strings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
