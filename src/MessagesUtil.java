import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessagesUtil {

	public static final String MSG_PREFIX_CMD = "command.";
	public static final String MSG_SUFFIX_DESC = ".desc";
	public static final String MSG_SUFFIX_EXAMPLE = ".example";
	public static final String MSG_SUFFIX_ERROR = ".error";
	public static final String MSG_SUFFIX_EMPTY = ".empty";
	
	public static String getMessage(String code) {
		Properties prop;
		InputStream input = null;
		try {

			prop = new Properties();
			input = MessagesUtil.class.getResourceAsStream("message.properties");
			prop.load(input);
			
			String message = prop.getProperty(code);
			message = (message != null) ? message : "Invalid message!";
			return message;

		} catch (IOException ex) {
			ex.printStackTrace();
			return "Invalid message!";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getCommand(String command) {
		return getMessage(MSG_PREFIX_CMD + command);
	}
	
	public static String getCommandDescription(String command) {
		return getMessage(MSG_PREFIX_CMD + command + MSG_SUFFIX_DESC);
	}
	
	public static String getCommandExample(String command) {
		return getMessage(MSG_PREFIX_CMD + command + MSG_SUFFIX_EXAMPLE);
	}
	
	public static String getCommandError(String command) {
		return getMessage(MSG_PREFIX_CMD + command + MSG_SUFFIX_ERROR);
	}
}
