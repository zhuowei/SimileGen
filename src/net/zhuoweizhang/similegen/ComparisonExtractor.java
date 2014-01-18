package net.zhuoweizhang.similegen;
import java.io.*;
import java.util.*;

import org.linkgrammar.*;
public class ComparisonExtractor {

	public static void extract(List<String> strings) throws IOException {
		File output = new File("output.txt");
		PrintWriter outfile = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))), true);
		//use Link grammar
		LinkGrammar.init();
		LinkGrammar.makeLinkage(0); // need to call at least once, otherwise it crashes
		int ia = 0;
		long beginAllTime = System.currentTimeMillis();
		for (String s: strings) {
			long beginTime = System.currentTimeMillis();
			//System.out.println(s);
			LinkGrammar.parse(s);
			long endTime = System.currentTimeMillis();
			//System.err.println(endTime - beginTime);
			int numLinkages = LinkGrammar.getNumLinkages();
			if (numLinkages <= 0) continue;
			LinkGrammar.makeLinkage(0);
			int numLinks = LinkGrammar.getNumLinks();
			for (int i = 0; i < numLinks; i++) {
				String a = LinkGrammar.getLinkLabel(i);
				if (a.equals("A")) { //Adjective
					int leftWord = LinkGrammar.getLinkLWord(i);
					int rightWord = LinkGrammar.getLinkRWord(i);
					String leftWordStr = LinkGrammar.getLinkageWord(leftWord);
					String rightWordStr = LinkGrammar.getLinkageWord(rightWord);
					if (leftWordStr.contains("[?]") || rightWordStr.contains("[?]")) {
						continue;
					}
					outfile.println(leftWordStr + "," + rightWordStr);
				}
				
			}
			//System.out.println(LinkGrammar.getLinkString());
			ia++;
		}
		long endAllTime = System.currentTimeMillis();
		System.out.println("Ran through " + ia + " tweets in " + (endAllTime - beginAllTime) + " milliseconds");
		outfile.close();
	}
}
