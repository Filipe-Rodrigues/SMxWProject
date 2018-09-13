package palletes;

public class InvalidTile32PalleteNumberException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidTile32PalleteNumberException(int length) {
		System.err.println("Invalid number of palletes! Trying to create "+length+" layers of colors, but 8 was expected.");
	}

}
