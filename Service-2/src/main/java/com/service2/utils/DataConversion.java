package com.service2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.service2.dto.FileDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Configuration
public class DataConversion {

	public String convertJSONToCSV(Map<String, Object> data) throws IOException {

		List<Map<String, Object>> list = new ArrayList<>();
		
		list.add(data);
	
		FileDTO dto = new FileDTO();
		dto.setFileName(list);
 

		String json = new Gson().toJson(dto);
		JSONObject output;
		output = new JSONObject(json);
		System.out.println(output);

		JSONArray docs = output.getJSONArray("fileName");
		File file = new File("EmpDetails.CSV");
		String csv = CDL.toString(docs);
		FileUtils.writeStringToFile(file, csv);
		System.out.println("Data has been Sucessfully Writeen to " + file);
		System.out.println(csv);
//		FileOutputStream fileOutputStream = new FileOutputStream("/Users/sagardas/Desktop/file4.txt");
//		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//		objectOutputStream.writeObject(csv);

		return "Created";
	}
	
	

	public String convertJSONToXML(Map<String, Object> data) throws IOException {
		 XStream magicApi = new XStream();
	        magicApi.registerConverter((Converter) new MapEntryConverter());
	        magicApi.alias("root", Map.class);

	        String xml = magicApi.toXML(data);
	        System.out.println("Result of tweaked XStream toXml()");
	        System.out.println(xml);
	        createXMlFile(xml);
			return xml;
	}
	
	
	public static void createXMlFile(String xml) throws IOException {
		java.io.FileWriter fw = new java.io.FileWriter("file.xml");
	    fw.write(xml);
	    fw.close();
		
	}

	
	public Map<String ,Object> covertCsvtoObj(String file1) throws IOException{
		File file = new File(file1);
		Map<String, Object> keyVals=null;
        // Create a File and append if it already exists.
        Reader reader = new FileReader(file);
        Iterator<Map<String, Object>> iterator = new CsvMapper()
                .readerFor(Map.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(reader);
        while (iterator.hasNext()) {
       keyVals = iterator.next();
            System.out.println(keyVals);
        }
		return keyVals;
	}
	
	
	public Map<String ,Object> covertXMLtoObj(String file1) throws IOException, JAXBException{
		
		InputStream is = new FileInputStream(new File(
				file1));
			    String xml = IOUtils.toString(is);

			    XMLSerializer xmlSerializer = new XMLSerializer();
			    JSON json = xmlSerializer.read(xml);

			    System.out.println(json.toString(2));

			   return  printJSON(json.toString(2));
			    }

			    public static Map<String ,Object> printJSON(String jsonString) throws JsonParseException, JsonMappingException, IOException {
			    ObjectMapper mapper = new ObjectMapper();

			   

			    Map<String, Object> jsonInMap = mapper.readValue(jsonString,
			    new TypeReference<Map<String, Object>>() {
			    });

return jsonInMap;
			    
			   
        
	}
	
	
}

