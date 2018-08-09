package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String data;
		String line;
		
		StringBuilder builder = new StringBuilder();
		
		BufferedReader reader = request.getReader();
		
		while((line = reader.readLine())!= null){
			builder.append(line);
		}
		
		data = builder.toString();
		
//--------------------Convert request body to json----------------------------//
		
		JsonParser parser = new JsonParser();
		JsonObject userObj = parser.parse(data).getAsJsonObject();
		
//--------------------Login User---------------------------------------------//
		
		DB db = new DB();
		
		String email = userObj.get("email").getAsString();
		
		String password = userObj.get("password").getAsString();
		
		int result = db.login(email, password);
		
		if(result!=0){
			System.out.println("Correct");
		} else {
			System.out.println("Incorrect");
		}
		
//--------------Send result in Json format------------------------------------//
		
		PrintWriter out = response.getWriter();
		JsonObject messageObj;
		
		
		
		if (result == 1) {
			messageObj = new JsonObject();
			messageObj.addProperty("message", 1);
			out.write(messageObj.toString());
			System.out.println("Correct");
		} else {
			messageObj = new JsonObject();
			messageObj.addProperty("message", 0);
			out.write(messageObj.toString());
			System.out.println("Incorrect");
		}
	}
}