import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {

	private ArrayList<Drive> drives;

	// Parameterized constructor
	public FileSystem() {
		drives = new ArrayList<Drive>();
	}

	// ******************
	// All create methods
	// ******************

	private boolean create(String type, String name, String path) {

		// Creating a new drive
		if (type.equals("Drive")) {
			return createDrive(name);
		}

		else if (type.equals("Folder")) {
			return createFolder(name, path);
		}

		else if (type.equals("Text File")) {
			return createTextFile(name, path);
		}

		else if (type.equals("Zip File")) {
			return createZipFile(name, path);
		}

		return false;
	}

	// Method to create a new drive
	private boolean createDrive(String name) {

		// Checking for duplication
		for (Drive i : drives) {

			if (i.getName().equals(name)) {
				System.out.println("Drive \"" + name + "\"" + " already exists!");
				return false;
			}
		}

		// Creating a new drive
		Drive newDrive = new Drive(name);
		drives.add(newDrive);
		System.out.println("Drive \"" + name + "\"" + " added successfully!");
		return true;
	}

	// Method to create a new folder
	private boolean createFolder(String name, String path) {
		boolean added = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					added = i.add(new Folder(name, path), scanner.next(), path);
					break;
				}

				else {
					added = i.add(new Folder(name, path), null, path);
					break;
				}
			}
		}

		scanner.close();
		return added;
	}

	// Method to create a new text file
	private boolean createTextFile(String name, String path) {
		boolean added = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					added = i.add(new TextFile(name, path), scanner.next(), path);
					break;
				}

				else {
					added = i.add(new TextFile(name, path), null, path);
					break;
				}
			}
		}

		scanner.close();
		return added;
	}

	// Method to create a new zip file
	private boolean createZipFile(String name, String path) {
		boolean added = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					added = i.add(new ZipFile(name, path), scanner.next(), path);
					break;
				}

				else {
					added = i.add(new ZipFile(name, path), null, path);
					break;
				}
			}
		}

		scanner.close();
		return added;
	}

	// Method to delete a file or directory
	private boolean delete(String path) {
		boolean deleted = false;
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					deleted = i.delete(scanner.next(), path);
					break;
				}
			}
		}

		scanner.close();
		return deleted;
	}

	// Method to move a file or directory
	private boolean move(String src, String des) {
		boolean moved = false;
		Scanner scanner = new Scanner(src);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();
		File target;

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					String str = scanner.next();

					// Locating current directory
					target = i.get(str, src);
					target.setPath(des);

					// Deleting from previous directory
					delete(src);

					// Moving to new destination
					Scanner scannerDes = new Scanner(des);
					scannerDes.useDelimiter("\\\\");
					current = scannerDes.next();

					for (Drive j : drives) {

						if ((j.getName() + ":").equals(current)) {

							if (scannerDes.hasNext()) {
								moved = j.add(target, scannerDes.next(), des);
							}

							else {
								moved = j.add(target, null, des);
							}
						}
					}
				}

				break;
			}
		}

		scanner.close();
		return moved;
	}

	// Method to write to file
	private boolean writeToFile(String path, String content) {
		Scanner scanner = new Scanner(path);
		scanner.useDelimiter("\\\\");
		String current = scanner.next();

		for (Drive i : drives) {

			if ((i.getName() + ":").equals(current)) {

				if (scanner.hasNext()) {
					i.write(content, scanner.next(), path);
					return true;
				}
			}
		}

		scanner.close();
		return false;
	}

	// Method to display file system
	public void display() {
		System.out.println("\nAll drives:\n");

		for (Drive i : drives) {
			i.display();
		}
	}

	// Method to start system (Only public method of system)
	public void start() {

		int choice = -1;
		Scanner scanner = new Scanner(System.in);

		while (choice != 6) {
			System.out.println("\n\t\t\t   FILE MANAGEMENT SYSTEM\n\n");
			System.out.println("1 - Create");
			System.out.println("2 - Delete");
			System.out.println("3 - Move");
			System.out.println("4 - Write");
			System.out.println("5 - Display");
			System.out.println("6 - Exit");
			System.out.print("\nEnter choice: ");
			choice = scanner.nextInt();

			// Validating user choice
			while (choice < 1 || choice > 6) {
				System.out.print("\n Invalid choice!\n Enter choice again: ");
				choice = scanner.nextInt();
			}

			// Displaying create menu
			if (choice == 1) {
				int temp = choice;
				choice = -1;

				while (choice != 5) {
					System.out.println("\n1 - Drive");
					System.out.println("2 - Folder");
					System.out.println("3 - Text file");
					System.out.println("4 - Zip file");
					System.out.println("5 - Exit");
					System.out.print("\nEnter choice: ");
					choice = scanner.nextInt();

					// Validating user choice
					while (choice < 1 || choice > 5) {
						System.out.print("\nInvalid choice!\nEnter choice again: ");
						choice = scanner.nextInt();
					}

					if (choice == 5) {
						continue;
					}

					// Taking info as input
					System.out.print("\nEnter name: ");
					scanner.nextLine();
					String name = scanner.nextLine();

					if (choice == 1) {
						create("Drive", name, "");
					}

					else if (choice == 2) {
						System.out.print("Enter path: ");
						String path = scanner.nextLine();

						if (!create("Folder", name, path)) {
							System.out.println("Somthing went wrong!");
						}

						else {
							System.out.println("Created!");
						}
					}

					else if (choice == 3) {
						System.out.print("Enter path: ");
						String path = scanner.nextLine();

						if (!create("Text File", name, path)) {
							System.out.println("Somthing went wrong!");
						}

						else {
							System.out.println("Created!");
						}
					}

					else if (choice == 4) {
						scanner.nextLine();
						System.out.print("Enter path: ");
						String path = scanner.nextLine();

						if (!create("Zip File", name, path)) {
							System.out.println("Somthing went wrong!");
						}

						else {
							System.out.println("Created!");
						}
					}
				}

				choice = temp;
			}

			else if (choice == 2) {
				System.out.print("Enter path: ");
				scanner.nextLine();
				String path = scanner.nextLine();

				if (!delete(path)) {
					System.out.println("Somthing went wrong!");
				}

				else {
					System.out.println("Deleted!");
				}
			}

			else if (choice == 3) {
				System.out.print("Enter source path: ");
				scanner.nextLine();
				String spath = scanner.nextLine();

				System.out.print("Enter destination path: ");
				String dpath = scanner.nextLine();

				if (!move(spath, dpath)) {
					System.out.println("Somthing went wrong!");
				}

				else {
					System.out.println("Moved!");
				}
			}

			else if (choice == 4) {
				System.out.print("Enter path: ");
				scanner.nextLine();
				String path = scanner.nextLine();

				System.out.print("Enter content: ");
				String content = scanner.nextLine();

				if (!writeToFile(path, content)) {
					System.out.println("Somthing went wrong!");
				}

				else {
					System.out.println("Written!");
				}
			}

			else if (choice == 5) {
				display();
			}

			else if (choice == 6) {
				System.out.println("\nExiting ...");
			}

		}

		scanner.close();
	}
}