package sandbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		Pattern p = Pattern.compile(".*?((?:/\\d+)+)");
		
		String path = "/a1a/abc/d1d/local/spare3/14/dddddx/2015/10/10";
		
		Matcher m = p.matcher(path);
		if (m.matches()){
			System.out.println("date is " + m.group(1));
		}
		else{
			System.out.println("doesn't match");
		}
		
	}
	public static void testExpression(){
		final String UNDERSCORE = "_";
		final String SPACE = "\\s";
		final String OPS = "(?:>=|<=|!=|=|>|<)";
		String[] sels = new String[]{
			"0x1:3:4=hello world=:5=bye world=",
			"0x1:3:4<3=:5=bye world= 0x23:4!=ba d=",
			
			
		};

		Pattern p = Pattern.compile(OPS + "[^=]+" + "=");

		for (String sel: sels){
			StringBuffer sb = new StringBuffer();
			Matcher m = p.matcher(sel);
			while (m.find()){
				System.out.println("found match: " + m.group());
				m.appendReplacement(sb, m.group().replaceAll(SPACE, UNDERSCORE));
			}
			m.appendTail(sb);
			System.out.println(sb.toString());
		}
	}

}
