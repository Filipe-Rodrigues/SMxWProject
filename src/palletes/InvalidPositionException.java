package palletes;

public class InvalidPositionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPositionException(int page, int line, int column){
		System.err.println("Position out of bounds at page "+page+" line "+line+" column "+column+"!");
		System.out.println("Try entering something between page 0~7, line 0~15 and column 0~15");
	}

}
