package testermatcher.csv;

public class CSVDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CSVDataException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
