import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Convert {
	
	public static boolean jsonProperty = true;

	public static void main(String[] args) {
		try (Stream<Path> s = Files.walk(Paths.get("json")).filter(Files::isRegularFile)) {
			List<Path> pathList = s.collect(Collectors.toList());
			for (Path p : pathList) {
				String fileName = p.toFile().getName().replace(".json", "");
				String jsonString = new String(Files.readAllBytes(p));
				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(jsonString);
				genPojo(fileName, rootNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void genPojo(String fileName, JsonNode jsonNode) throws IOException {
		String fileNamePrefix = fileName.replace("Request", "").replace("Response", "");
		
		StringBuilder sb = new StringBuilder();
		sb.append("public class " + fileName + " {").append("\r\n");
		sb.append("    ").append("\r\n");
		
		Iterator<String> fieldNames = jsonNode.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			String originFieldName = fieldName;
			if (jsonProperty) {
				fieldName = getJsonPropertyFieldName(fieldName);
			}
			
			System.out.println("[" +fieldName+"]");
			
			String objectName = fileNamePrefix.substring(0, 1).toUpperCase() 
					+ fileNamePrefix.substring(1) 
					+ fieldName.substring(0, 1).toUpperCase() 
					+ fieldName.substring(1);
			String nodeType = jsonNode.get(originFieldName).getNodeType().name();
			System.out.println(nodeType + " " + fieldName);
			
			if (jsonProperty) {
				sb.append("    @JsonProperty(\"" + originFieldName + "\")");
				sb.append("\r\n");
			}
			
			if ("OBJECT".equals(nodeType)) {
				genPojo(objectName, jsonNode.get(originFieldName));
				
				sb.append("    public " + objectName + " " + fieldName + ";");
			} else if ("ARRAY".equals(nodeType)) {
				genPojo(objectName, jsonNode.get(originFieldName).get(0));
				
				sb.append("    public List<" + objectName + "> " + fieldName + ";");
			} else {
				sb.append("    public String " + fieldName + ";");
			}
			sb.append("\r\n");
			sb.append("\r\n");
		}
		
		sb.append("    public " + fileName + "(");
		sb.append(") {");
		sb.append("\r\n");
		sb.append("        super();");
		sb.append("\r\n");
		sb.append("    }");
		sb.append("\r\n");
		sb.append("\r\n");
		
		int i = 0;
		sb.append("    public " + fileName + "(");
		fieldNames = jsonNode.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			String originFieldName = fieldName;
			if (jsonProperty) {
				fieldName = getJsonPropertyFieldName(fieldName);
			}
			
			String objectName = fileNamePrefix.substring(0, 1).toUpperCase() 
					+ fileNamePrefix.substring(1) 
					+ fieldName.substring(0, 1).toUpperCase() 
					+ fieldName.substring(1);
			String nodeType = jsonNode.get(originFieldName).getNodeType().name();
			
			if (i != 0) {
				sb.append(", ");
			}
			
			if ("OBJECT".equals(nodeType)) {
				sb.append(objectName + " " + fieldName);
			} else if ("ARRAY".equals(nodeType)) {
				sb.append("List<" + objectName + "> " + fieldName);
			} else {
				sb.append("String " + fieldName);
			}
			i++;
		}
		sb.append(") {");
		sb.append("\r\n");
		sb.append("        super();");
		sb.append("\r\n");
		
		fieldNames = jsonNode.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			sb.append("        this." + fieldName + " = " + fieldName + ";");
			sb.append("\r\n");
		}
		
		sb.append("    }");
		sb.append("\r\n");
		sb.append("\r\n");

		fieldNames = jsonNode.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			String originFieldName = fieldName;
			if (jsonProperty) {
				fieldName = getJsonPropertyFieldName(fieldName);
			}
			
			String fieldNameUpper = fieldName.substring(0, 1).toUpperCase() 
					+ fieldName.substring(1);
			String objectName = fileNamePrefix.substring(0, 1).toUpperCase() 
					+ fileNamePrefix.substring(1) 
					+ fieldNameUpper;
			String nodeType = jsonNode.get(originFieldName).getNodeType().name();
			
			if ("OBJECT".equals(nodeType)) {
				
			} else if ("ARRAY".equals(nodeType)) {
				objectName = "List<" + objectName + ">";
			} else {
				objectName = "String";
			}

			sb.append("    public " + objectName + " get" + fieldNameUpper + "() {");
			sb.append("\r\n");
			sb.append("        return " + fieldName + ";");
			sb.append("\r\n");
			sb.append("    }");
			sb.append("\r\n");
			sb.append("\r\n");
			sb.append("    public void set" + fieldNameUpper + "(" + objectName + " " + fieldName + ") {");
			sb.append("\r\n");
			sb.append("        this." + fieldName + " = " + fieldName + ";");
			sb.append("\r\n");
			sb.append("    }");
			sb.append("\r\n");
			sb.append("\r\n");
		}
		
		sb.append("    @Override");
		sb.append("\r\n");
		sb.append("    public String toString() {");
		sb.append("\r\n");
		sb.append("        return \"" + fileName + " [");

		i = 0;
		fieldNames = jsonNode.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if (jsonProperty) {
				fieldName = getJsonPropertyFieldName(fieldName);
			}
			
			if (i != 0) {
				sb.append(", ");
			}
			sb.append("" + fieldName + "=\" + " + fieldName + " + \"");

			i++;
		}
		sb.append("]\";");
		sb.append("\r\n");
		
		sb.append("    }");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("}");
		
		Files.write(Paths.get("pojo/" + fileName + ".java"), sb.toString().getBytes());
	}
	
	private static String getJsonPropertyFieldName(String fieldName) {
		String result = "";
		fieldName = fieldName.toLowerCase();
		if (fieldName.indexOf("_") != -1) {
			String[] fieldNames = fieldName.split("_");
			for (int i = 0; i < fieldNames.length; i++) {
				String s = fieldNames[i];
				if (i > 0) {
					result += s.substring(0, 1).toUpperCase() + s.substring(1);
				} else {
					result += s;
				}
			}
		} else {
			result = fieldName;
		}
		return result;
	}
}
