public abstract class File {

	protected String name;
	protected String path;
	protected int size;
	protected String type;

	// Parameterized constructor
	public File(String name, String path, String type) {
		this.name = name;
		this.path = path;
		this.type = type;
		this.size = 0;
	}

	// **************
	// Getter methods
	// **************

	public String getName() {
		return this.name;
	}

	public String getPath() {
		return this.path;
	}

	public String getType() {
		return this.type;
	}

	public int getSize() {
		return this.size;
	}

	// **************
	// Setter methods
	// **************

	public void setPath(String path) {
		this.path = path;
	}

	// ***************
	// Utility methods
	// ***************

	// Method to display file info
	public void display() {
		System.out.println("Name: " + this.name);
		System.out.println("Path: " + this.path);
		System.out.println("Size: " + this.size);
		System.out.println("Type: " + this.type);
	}

	protected abstract boolean add(File file, String current, String path);

	protected abstract boolean delete(String current, String path);

	protected abstract File get(String current, String path);

	protected abstract boolean write(String content, String current, String path);

	protected abstract void updateSize();
}