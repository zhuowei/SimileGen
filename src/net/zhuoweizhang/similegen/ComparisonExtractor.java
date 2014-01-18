package net.zhuoweizhang.similegen;
import java.util.*;

import org.linkgrammar.*;
public class ComparisonExtractor {

	public static void extract(List<String> strings) {
		//use Link grammar
		LinkGrammar.init();
		LinkGrammar.makeLinkage(0); // need to call at least once, otherwise it crashes
		for (String s: strings) {
			System.out.println(s);
			LinkGrammar.parse(s);
			int numLinkages = LinkGrammar.getNumLinkages();
			if (numLinkages <= 0) continue;
			LinkGrammar.makeLinkage(0);
			System.out.println(LinkGrammar.getLinkString());
		}
	}
}
