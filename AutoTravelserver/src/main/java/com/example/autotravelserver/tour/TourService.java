package com.example.autotravelserver.tour;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TourService {

    @Value("${spring.tour.key}")
    private String apiKey;

    private final TourRepository tourRepository;


    public void saveTour(){
        List<TourEntity> tourEntities = this.getTourFromApi();

        for (TourEntity tourEntity :tourEntities){
            tourRepository.save(tourEntity);
        }
    }

    /**
     * TYPE 변환
     * 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
     * @param contentType
     * @return type
     */

    private String matchType(String contentType){
        String type = "";
        int t = Integer.parseInt(contentType);
        switch (t){
            case 12:
                type = "관광지";
                break;
            case 14:
                type = "문화시설";
                break;
            case 15:
                type = "축제공연행사";
                break;
            case 25:
                type = "여행코스";
                break;
            case 28:
                type = "레포츠";
                break;
            case 32:
                type = "숙박";
                break;
            case 38:
                type = "쇼핑";
                break;
            case 39:
                type = "음식점";
                break;
        }
        return type;
    }

    /**
     * tourEntity List에 여행지 값 저장
     *
     * @return
     */
    private List<TourEntity> getTourFromApi(){
        String tourString = getTourString();
        List<Map<String, Object>> parseTour = parseTour(tourString);

        List<TourEntity> tourEntities = new ArrayList<>();

        for ( Map<String, Object> tour : parseTour ){
            TourEntity tourEntity = new TourEntity();
            tourEntity.setContentId((String) tour.get("contentid"));
            tourEntity.setTitle((String) tour.get("title"));
            tourEntity.setType(this.matchType((String) tour.get("contenttypeid")));
            tourEntity.setAddress((String) tour.get("address"));
            tourEntity.setLat((String) tour.get("mapy"));
            tourEntity.setLng((String) tour.get("mapx"));
            tourEntity.setDescription((String) tour.get(""));

            tourEntities.add(tourEntity);
        }

        return tourEntities;
    }

    /**
     * json을 parse
     * @param jsonString
     * @return
     */
    private List<Map<String, Object>> parseTour(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        List<Map<String,Object>> resultList = new ArrayList<>();

        JSONObject responseData = (JSONObject) jsonObject.get("response");
        JSONObject bodyData = (JSONObject) responseData.get("body");
        JSONObject itemsData = (JSONObject) bodyData.get("items");
        JSONArray itemArr = (JSONArray) itemsData.get("item");

        for ( Object item: itemArr){
            Map<String, Object> resultMap = new HashMap<>();
            JSONObject jsonItem = (JSONObject) item;
            resultMap.put( "address", jsonItem.get("addr1"));
            resultMap.put("contentid", jsonItem.get("contentid"));
            resultMap.put("contenttypeid",jsonItem.get("contenttypeid"));
            resultMap.put("mapx",jsonItem.get("mapx"));
            resultMap.put("mapy",jsonItem.get("mapy"));
            resultMap.put("title",jsonItem.get("title"));

            resultList.add(resultMap);
        }

        return resultList;
    }

    /**
     * api를 호출해 문자열로 값 가져오기
     * @return String 형태
     */
    private String getTourString(){

        String apiUrl
                = "https://apis.data.go.kr/B551011/KorService1/searchStay1?serviceKey=IWQ8AgfCDCnhO7BVKzm7e9DNq7L%2B4G1D2JmPuoZjUxyFi7mvcdIM1Zd3th%2FhFmG8wQvodEed0BlOW7YYstjmAw%3D%3D&numOfRows=3375&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            int responseCode = httpURLConnection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200){
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e){
            return "failed to get response";
        }
    }


}
