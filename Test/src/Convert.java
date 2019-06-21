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
    
    public static String packageName = "com.cathybk.crmrsrtdmsvc.dto";
	
	public static void main(String[] args) {
		try (Stream<Path> s = Files.walk(Paths.get("json")).filter(Files::isRegularFile)) {
			List<Path> pathList = s.collect(Collectors.toList());
			for (Path p : pathList) {
				String fileName = p.toFile().getName().replace(".json", "");
				String jsonString = new String(Files.readAllBytes(p), "MS950");
				
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(jsonString);
				genPojo(fileName, rootNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private static void genPojo(String fileName, JsonNode jsonNode) {
        String fileNamePrefix = fileName.replace("Request", "").replace("Response", "");
        StringBuilder fieldNameSb = new StringBuilder();
        StringBuilder getSetSb = new StringBuilder();

        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String jsonKey = fieldNames.next();
            JsonNode fieldNode = jsonNode.get(jsonKey);
            String nodeType = jsonNode.get(jsonKey).getNodeType().name();
            String fieldType = getFieldType(fileNamePrefix, jsonKey, nodeType);
            String fieldName = getFieldName(jsonKey);
            
            System.out.println("jsonKey = " + jsonKey);
            System.out.println("nodeType = " + nodeType);
            System.out.println("fieldType = " + fieldType);
            System.out.println("fieldName = " + fieldName);

            if ("OBJECT".equals(nodeType)) {
                genPojo(fieldType, fieldNode);
            } else if ("ARRAY".equals(nodeType)) {
                genPojo(fieldType.replace("List<", "").replace(">", ""), fieldNode.get(0));
            }

            fieldNameSb.append(getFieldNameTemplate(fieldType, fieldName, jsonKey));
            fieldNameSb.append("\r\n");
            fieldNameSb.append("\r\n");

            getSetSb.append(getGetSetTemplate(fieldType, fieldName));
            getSetSb.append("\r\n");
            getSetSb.append("\r\n");
            
        }
        
        String fileContent = getPoJoTemplate(packageName, fileName, fieldNameSb.toString(), getSetSb.toString());
        try {
            Files.write(Paths.get("pojo/" + fileName + ".java"), fileContent.getBytes());
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }

    private static String getFieldType(String fileNamePrefix, String jsonKey, String nodeType) {
        String fieldType = "";
        
        String objectName = fileNamePrefix;
        if (jsonKey.indexOf("_") != -1) {
            String jsonPropertyFieldName = getFieldName(jsonKey);
            objectName += (jsonPropertyFieldName.substring(0, 1).toUpperCase() + jsonPropertyFieldName.substring(1));
        } else {
            if ("TRANRQ".equals(jsonKey.toUpperCase())) {
                objectName += "Tranrq";
            } else if ("TRANRS".equals(jsonKey.toUpperCase())) {
                objectName += "Tranrs";
            } else if ("MWHEADER".equals(jsonKey.toUpperCase())) {
                objectName += "MwHeader";
            } else {
                objectName += (jsonKey.substring(0, 1).toUpperCase() + jsonKey.substring(1));
            }
        }
        
        if ("OBJECT".equals(nodeType)) {
            fieldType = objectName;
        } else if ("ARRAY".equals(nodeType)) {
            fieldType = "List<" + objectName + "Detail>";
        } else {
            fieldType = "String";
        }
        return fieldType;
    }

    private static String getFieldName(String jsonKey) {
		String result = "";
        if ("TRANRQ".equals(jsonKey.toUpperCase())) {
            result = "tranrq";
        } else if ("TRANRS".equals(jsonKey.toUpperCase())) {
            result = "tranrs";
        } else if ("MWHEADER".equals(jsonKey.toUpperCase())) {
            result = "mwHeader";
        } else if ("SOURCECHANNEL".equals(jsonKey.toUpperCase())) {
            result = "sourceChannel";
        } else if ("MSGID".equals(jsonKey.toUpperCase())) {
            result = "msgId";
        }  else if ("TXNSEQ".equals(jsonKey.toUpperCase())) {
            result = "txnSeq";
        }  else if ("RETURNCODE".equals(jsonKey.toUpperCase())) {
            result = "returnCode";
        }  else if ("RETURNDESC".equals(jsonKey.toUpperCase())) {
            result = "returnDesc";
        }  else if ("O360SEQ".equals(jsonKey.toUpperCase())) {
            result = "o360Seq";
        }  else {
            if (jsonKey.indexOf("_") != -1) {
                String[] fieldNames = jsonKey.split("_");
                for (int i = 0; i < fieldNames.length; i++) {
                    String s = fieldNames[i];
                    if (i > 0) {
                        result += s.substring(0, 1).toUpperCase() + s.substring(1);
                    } else {
                        result += s;
                    }
                }
            } else {
                result = jsonKey;
            }
        }
		return result;
	}
    
    private static String getPoJoTemplate(String packageName, String fileName, String fieldNameSring, String getSetSring) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("template/PoJoTemplate.tl")));
            content = content.replaceAll("\\$packageName\\$", packageName);
            content = content.replaceAll("\\$fileName\\$", fileName);
            content = content.replaceAll("\\$fieldName\\$", fieldNameSring);
            content = content.replaceAll("\\$getSet\\$", getSetSring);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String getFieldNameTemplate(String fieldType, String fieldName, String jsonKey) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("template/FieldNameTemplate.tl")));
            content = content.replaceAll("\\$fieldType\\$", fieldType);
            content = content.replaceAll("\\$fieldName\\$", fieldName);
            content = content.replaceAll("\\$jsonKey\\$", jsonKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String getGetSetTemplate(String fieldType, String fieldName) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("template/GetSetTemplate.tl")));
            content = content.replaceAll("\\$fieldType\\$", fieldType);
            content = content.replaceAll("\\$fieldName\\$", fieldName);
            content = content.replaceAll("\\$fieldNameUpper\\$", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
