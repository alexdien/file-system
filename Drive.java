import java.util.ArrayList;
import java.util.Scanner;

public class Drive extends File {

	private ArrayList<File> directories;

	// Parameterized constructor
	public Drive(String name) {
		super(name, "", "Drive");
		directories = new ArrayList<File>();
	}

	// ***************
	// Utility methods
	// ***************

	// Method to display directory info
	@Override
	public void display() {
		System.out.println("Name: " + this.name);
		System.out.println("Size: " + this.size);
		System.out.println("Type: " + this.type);

		if (!directories.isEmpty()) {
			System.out.println("Contains:\n");

			for (File i : directories) {
				i.display();
			}
		}
	}

	// Method to add a new entry
	@Override
	public boolean add(File file, String current, String path) {

		if (current == null) {

			for (File i : directories) {

				if (i.getName().equals(file.getName())) {
					System.out.println("\"" + file.getName() + "\" already exists!");
					return false;
				}
			}

			directories.add(file);
			updateSize();
			return true;
		}

		else {
			boolean added = false;
			Scanner scanner = new Scanner(path);
			scanner.useDelimiter("\\\\");

			while (scanner.hasNext()) {

				if (scanner.next().equals(current)) {
					break;
				}
			}

			for (File i : directories) {

				if (i.getName().equals(current)) {

					if (scanner.hasNext()) {
						added = i.add(file, scanner.next(), path);
						break;
					}

					else {
						added = i.add(file, null, path);
						break;
					}
				}
			}

			scanner.close();

			if (!added) {
				System.out.println("Invalid path!");
			}

			return added;
		}
	}

	// Method to delete a file or directory
	@Override
	protected boolean delete(String current, String path) {
		boolean deleted = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		File target = null;

		while (scanner.hasNext()) {

			if (scanner.next().equals(current)) {
				break;
			}
		}

		for (File i : directories) {

			if (i.getName().equals(current)) {

				if (scanner.hasNext()) {
					i.delete(scanner.next(), path);
				}

				target = i;
				deleted = true;
			}
		}

		directories.remove(target);
		updateSize();
		scanner.close();

		if (!deleted) {
			System.out.println("Invalid path!");
		}

		return deleted;
	}

	// Method to get file or directory from given path
	@Override
	protected File get(String current, String path) {
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");

		while (scanner.hasNext()) {

			if (scanner.next().equals(current)) {
				break;
			}
		}

		for (File i : directories) {

			if (i.getName().equals(current)) {

				if (scanner.hasNext()) {
					return i.get(scanner.next(), path);
				}

				else {
					return i;
				}
			}
		}

		scanner.close();
		return null;
	}

	// Method to write to file
	@Override
	protected boolean write(String content, String current, String path) {

		if (current == null) {
			return false; // No writing on drive
		}

		boolean written = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");

		while (scanner.hasNext()) {

			if (scanner.next().equals(current)) {
				break;
			}
		}

		for (File i : directories) {

			if (i.getName().equals(current)) {

				if (scanner.hasNext()) {
					written = i.write(content, scanner.next(), path);
					updateSize();
					break;
				}

				else {
					written = i.write(content, null, path);
					updateSize();
					break;
				}
			}
		}

		scanner.close();
		return written;
	}

	// Method to update size of drive
	@Override
	protected void updateSize() {
		this.size = 0;

		for (File i : directories) {
			this.size = this.size + i.getSize();
		}
	}
}