import java.util.Scanner;

public class TextFile extends File {

	private String content;

	// Parameterized constructor
	public TextFile(String name, String path) {
		super(name, path, "Text File");
		this.content = "";
	}

	// *****************
	// Utility functions
	// *****************

	// Method to add content to text file
	public void addContent(String content) {
		this.content = this.content + content;
		updateSize(); // Updating size of text file after new content
	}

	// Method to update size of text file
	@Override
	protected void updateSize() {
		this.size = this.content.length();
	}

	// Method to display text file info
	@Override
	public void display() {
		super.display();
		System.out.println("Content: " + this.content);
	}

	@Override
	protected boolean add(File file, String current, String path) {
		return false; // No addition in text file
	}

	@Override
	protected boolean delete(String current, String path) {
		return false; // No deletion in text file
	}

	@Override
	protected File get(String current, String path) {
		return null; // Nothing returns from a text file
	}

	// Method to write to file
	@Override
	protected boolean write(String content, String current, String path) {

		if (current == null) {
			addContent(content);
			return true;
		}

		return false;
	}
}