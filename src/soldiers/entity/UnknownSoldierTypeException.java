package soldiers.entity;

public class UnknownSoldierTypeException extends RuntimeException {

	private static final long serialVersionUID = -7624693448718375191L;

	public UnknownSoldierTypeException() {
		super();
	}

	public UnknownSoldierTypeException(String arg0) {
		super(arg0);
	}

	public UnknownSoldierTypeException(Throwable arg0) {
		super(arg0);
	}

	public UnknownSoldierTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
