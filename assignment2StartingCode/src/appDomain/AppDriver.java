package appDomain;

import java.io.*;

import implementations.MyStack;

public class AppDriver {

	public static void main(String[] args) {
		
		MyStack<String> stack = new MyStack<>();
		
		boolean rootFound = false;
		int lineNumber = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
			
			String line;
			
			// Read file 
			while ((line = reader.readLine()) != null) {
				
				lineNumber++;
				
				int start = 0;
				
				// Search current line for XML tags
				while ((start = line.indexOf('<', start)) != -1) {
					
					int end = line.indexOf('<', start);
					
					if (end == -1) {
						System.out.println("Line " + lineNumber + ": Missing closing '>'");
						break;
					}
					
					String tag = line.substring(start + 1, end).trim();
					
					// Ignore XML declarations
					if (tag.startsWith("?")) {
						start = end + 1;
						continue;
					}
					
					// Ignore comments
					if (tag.startsWith("!--")) {
						start = end + 1;
					}
					
					// Ignore self-closing tags
					if (tag.endsWith("/")) {
						start = end + 1;
						continue;
					}
					
					// Compare closing tags to top of stack
					if (tag.startsWith("/")) {
						String closingTag = cleanTag(tag.substring(1));
						
						if (stack.isEmpty()) {
							System.out.println("Line " + lineNumber + ": Unexpected closing tag </" + closingTag + ">");
						} else {
							String openingTag = stack.pop();
							
							if (!openingTag.equals(closingTag)) {
								
								System.out.println("Line " + lineNumber + ": Expected </" 
										+ openingTag + "> but found </" + closingTag + ">");
							}
						}
					}
					
					// Push opening tags to stack
					else {
						String openingTag = cleanTag(tag);
						
						if (!rootFound) {
							rootFound = true;
						} else if (stack.isEmpty()) {
							System.out.println("Line " + lineNumber + ": Multiple root elements found.");
						}
						
						stack.push(openingTag);
					}
					
					start = end - 1;
				}
			}
			
			// Missing closing tags
			while (!stack.isEmpty()) {
				System.out.println("Missing closing tag for <" + stack.pop() + ">");
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes attributes from a tag.
	 * 
	 * @param tag the XML tag with attributes
	 * @return name of XML tag without attributes
	 */
	private static String cleanTag(String tag) {
		
		tag = tag.trim();
		
		int space = tag.indexOf(' ');
		
		// Keep only tag name
		if (space != -1) {
			tag = tag.substring(0, space);
		}
		return tag;
	}

}
