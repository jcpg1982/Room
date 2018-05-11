package pe.com.mastermachines.probando.retrofit;

/**
 * @author Anonymous on 10/05/2018.
 * @version 1.0
 */
class RegisterResponse {

    private boolean success = false;

    private String message = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
