package co.edu.uniandes.dse.parcial1.exceptions;

public final class ErrorMessage {
	public static final String CONCIERTO_NOT_FOUND = "The book with the given id was not found";
	public static final String ESTADIO_NOT_FOUND = "The review with the given id was not found";
	public static final String EDITORIAL_NOT_FOUND = "The editorial with the given id was not found";
	public static final String REVIEW_NOT_ASSOCIATED_TO_BOOK = "The review is not associated to the book";

	private ErrorMessage() {
		throw new IllegalStateException("Utility class");
	}
}