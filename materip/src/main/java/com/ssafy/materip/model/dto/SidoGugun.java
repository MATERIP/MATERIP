package com.ssafy.materip.model.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SidoGugun implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		FileOutputStream fStreams = new FileOutputStream("C:\\\\Users\\\\SSAFY\\\\Downloads\\\\sido.ser");
		ObjectOutputStream oss = new ObjectOutputStream(fStreams);
		FileOutputStream fStreamg = new FileOutputStream("C:\\\\Users\\\\SSAFY\\\\Downloads\\\\gugun.ser");
		ObjectOutputStream osg = new ObjectOutputStream(fStreamg);
		
		
		
		
		JSONParser jsonParser = new JSONParser();

		Map<String, Integer> sidoMap = new HashMap<String, Integer>();
		Map<String, HashMap<String, Integer>> guGunMap = new HashMap<String, HashMap<String, Integer>>();
		int[] sidoCode = new int[] { 1, 6, 2, 4, 5, 3, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39 };
		Map<String, Object> map = new HashMap<String, Object>();
		int idx = 0;
		try {
			Object object = jsonParser
					.parse(new FileReader("C:\\Users\\SSAFY\\Downloads\\korea-administrative-district.json"));

			JSONObject jsonObject = (JSONObject) object;
			for (JSONObject obj : (List<JSONObject>) jsonObject.get("data")) {
				for (Object sido : (Set<Object>) obj.keySet()) {
					Collections.sort((List<Object>) obj.get(sido), new Comparator<Object>() {

						@Override
						public int compare(Object o1, Object o2) {

							return ((String) o1).compareTo((String) o2);
						}
					});

					sidoMap.put((String) sido, sidoCode[idx++]);

					HashMap<String, Integer> tmp = new HashMap<String, Integer>();
//					if(sido.equals("세종특별자치시")) {
//						tmp.put((String)sido, 1);
//					}
					int idx2 = 1; // 구군 코드 시작 인덱스
					for (String gugun : (List<String>) obj.get(sido)) {
						tmp.put(gugun, idx2++);

					}
					guGunMap.put((String) sido, (HashMap<String, Integer>) tmp.clone());
				}
				
			}
			
			
			oss.writeObject(sidoMap);
			osg.writeObject(guGunMap);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
			
	}
}
