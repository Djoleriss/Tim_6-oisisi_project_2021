package View;

public class StateHolder {
	private String userType;
	private static StateHolder instance = new StateHolder();
	
	public StateHolder() {
		
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public static StateHolder getInstance() {
		return instance;
	}
}
