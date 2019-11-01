import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Response {
	private String RESPONSE;
	private String ERROR_MESSAGE;

	public Response(String result) {
		super();
		String toParse = returnParsedString(result);
		// System.out.println("toParse " + toParse);

		JsonElement jsonElement = new JsonParser().parse(toParse);

		JsonObject jsonObject = jsonElement.getAsJsonObject();
		setResponse(jsonObject.get("RESPONSE").getAsString());
		setErrorMessage(jsonObject.get("ERROR_MESSAGE").getAsString());

	}

	public String getResponse() {
		return RESPONSE;
	}

	private void setResponse(String response) {
		this.RESPONSE = response;
	}

	public String getErrorMessage() {
		return ERROR_MESSAGE;
	}

	private void setErrorMessage(String errorMessage) {
		this.ERROR_MESSAGE = errorMessage;
	}

	private static String returnParsedString(String output) {
		String ret;
		String[] splitStr = output.trim().split(":", 2);
		ret = removeLastCharOptional(splitStr[1]);

		// System.out.println("Returning :: " + ret);
		return ret;
	}

	private static String removeLastCharOptional(String s) {
		return Optional.ofNullable(s).filter(str -> str.length() != 0).map(str -> str.substring(0, str.length() - 1))
				.orElse(s);
	}

}
