package br.ufs.dcomp.projetopsr.AIproject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.vandermeer.asciitable.AsciiTable;

public class TableList {
	public void printResults(String data) {
		List<String> temp = new ArrayList<>();

		Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(data);
		while (matcher.find()) {
			temp.add(String.join("<br>", matcher.group().replaceAll("[\\[\\]]", "").split(",")));
		}

		List<String> AMrow = new ArrayList<>(temp).stream().limit(12).collect(Collectors.toList());
		AMrow.add(0, "AM");
		List<String> PMrow = new ArrayList<>(temp).stream().skip(12).collect(Collectors.toList());

		PMrow.add(0, "PM");

		AsciiTable at = new AsciiTable();
//		at.getContext().setGridTheme(TA_GridThemes.INSIDE_HORIZONTAL);
		at.addRule();
//		at.addRow((Object[]) Arrays.asList("", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
//				.toArray(String[]::new));
		at.addRule();
//		at.addRow((Object[]) AMrow.toArray(String[]::new));
		at.addRule();
//		at.addRow((Object[]) PMrow.toArray(String[]::new));
		at.addRule();

		System.out.println(at.render(100));
	}
}
