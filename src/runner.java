public class runner {
	public static void main(String[] args) {
		try {
			Class<?> login = Class.forName("Login");
			login.getMethod("main", String[].class).invoke(null, (Object) args);
		} catch (ClassNotFoundException e) {
			System.err.println("Login class was not found. Compile login.java first.");
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Unable to run Login.main", e);
		}
	}
}
