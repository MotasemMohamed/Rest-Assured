package org.example;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Get_Essential_Information {

    private Integer VehicleClassid = 0;
    private Integer serviceId = 0;
    private String startTime="";
    private String endTime="";
    private Integer AddappTrsId = 0;
    Faker fakerdata= new Faker();
    String plateNumber = fakerdata.number().digits(4);
    private Integer IdAppointmentTransaction = 0;
    private Integer CancelappTrsId = 0;

        @Test (priority = 1)
        public void GetVehicleClasses ()
        {
            Response res = given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId", "000000")
                    .when().get("Dummy End point").then().extract().response();
            VehicleClassid = JsonPath.from(res.asString()).getInt("data[0].id");
            given().relaxedHTTPSValidation().baseUri("https://172.16.25.200/api/inspectionAppFlowRest").header("x-clientId", "000000")
                    .when().get("Dummy End point").then().assertThat().statusCode(200);
        }
    @Test (priority = 2, dependsOnMethods = "GetVehicleClasses")
        public void GetServices ()
        {
            Response response = given().relaxedHTTPSValidation().baseUri("Dumy URL").queryParam("vclClassId", VehicleClassid)
                    .when().get("Dummy End point").then().extract().response();
            serviceId = JsonPath.from(response.asString()).getInt("data[2].id");
            given().relaxedHTTPSValidation().baseUri("Dumy URL").queryParam("vclClassId", VehicleClassid)
                    .when().get("Dummy End point").then().assertThat().statusCode(200);
        }
    @Test (priority = 3, dependsOnMethods = "GetServices")
    public void getVTCZoneList ()
        {
            given().relaxedHTTPSValidation().baseUri("Dumy URL").queryParams("vclClassId", VehicleClassid, "serviceId", serviceId)
                    .when().get("Dummy End point").then().assertThat().statusCode(200);
        }
    @Test (priority = 4, dependsOnMethods = "getVTCZoneList")
    public void Get_Available_Appointment_Time_Slots()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vehicleClassId",VehicleClassid);
        jsonObject.put("serviceId",serviceId);
        jsonObject.put("centerId","101");
        jsonObject.put("apointmentDayDate","03-01-2023");
       Response res= given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
               .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().extract().response();

        startTime= JsonPath.from(res.asString()).getString("data[0].startTime");
        endTime= JsonPath.from(res.asString()).getString("data[0].endTime");
        System.out.println(startTime);
        System.out.println(endTime);

        given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().assertThat().statusCode(200);
    }

    @Test (priority = 5, dependsOnMethods = "Get_Available_Appointment_Time_Slots")
    public void BOOK_WITH_DELEGATION()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","null");
        jsonObject.put("customerName","IsoftName");
        jsonObject.put("customerEmail","IsoftName@Test.com");
        jsonObject.put("customerMobileNo","01001620150");
        jsonObject.put("registeredVehicle",true);
        jsonObject.put("registrationCountryId",51);
        jsonObject.put("vehicleClassId",VehicleClassid);
        jsonObject.put("serviceId",serviceId);
        jsonObject.put("centerId",101);
        jsonObject.put("plateNum",plateNumber+"-"+"ف ف ل");
        jsonObject.put("plateTypeId",31);
        jsonObject.put("apointmentDayDate","2023-01-03");
        jsonObject.put("laneId",184);
        jsonObject.put("apointmentStartTime",startTime);
        jsonObject.put("apointmentEndTime",endTime);
        jsonObject.put("recaptcha","");
        jsonObject.put("operationMode",1);
        JSONObject aptDelegationObject = new JSONObject();
        aptDelegationObject.put("name","Mona Abdallah");
        aptDelegationObject.put("phone","01011111111111");
        aptDelegationObject.put("aptDelegationType","GULF_CITIZEN");
        aptDelegationObject.put("residencyNo","22222222222");
        aptDelegationObject.put("dob","13-12-2006");
        aptDelegationObject.put("identityNo","1111111111111");
        aptDelegationObject.put("acceptConditions",true);
        JSONObject nationalityObject = new JSONObject();
        nationalityObject.put("id",25);
        aptDelegationObject.put("nationality",nationalityObject);
        jsonObject.put("aptDelegation",aptDelegationObject);

        //System.out.println(jsonObject.toString());

        Response res= given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().extract().response();

        AddappTrsId= JsonPath.from(res.asString()).getInt("data[0].appTrsId");
        System.out.println(AddappTrsId);
        given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().assertThat().statusCode(200);
    }
    @Test (priority = 6, dependsOnMethods = "BOOK_WITH_DELEGATION")
    public void  validate_SMS_Verify_Code_Add()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customerMobileNo","01001620150");
        jsonObject.put("passCode","123453");
        jsonObject.put("operationMode",1);
        jsonObject.put("appTrsId",AddappTrsId);

        System.out.println(jsonObject.toString());

        Response res= given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().extract().response();

        IdAppointmentTransaction= JsonPath.from(res.asString()).getInt("data[0].id");
        System.out.println(IdAppointmentTransaction);
        given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().assertThat().statusCode(200);
    }
    @Test
    public void  send_SMS_Verify_Code_cancel()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",IdAppointmentTransaction);
        jsonObject.put("customerMobileNo","01001620150");
        jsonObject.put("operationMode",3);

        System.out.println(jsonObject.toString());

        Response res= given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().extract().response();

       CancelappTrsId= JsonPath.from(res.asString()).getInt("data[0].appTrsId");
        System.out.println(CancelappTrsId);
        given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().assertThat().statusCode(200);
    }
    @Test
    public void  validate_SMS_Verify_Code_cancel()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customerMobileNo","01001620150");
        jsonObject.put("passCode",123456);
        jsonObject.put("operationMode",3);
        jsonObject.put("appTrsId",CancelappTrsId);
        System.out.println(jsonObject.toString());
        Response res= given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().extract().response();
        given().relaxedHTTPSValidation().baseUri("Dumy URL").header("x-clientId","000000")
                .contentType(ContentType.JSON).body(jsonObject.toString())
                .when().post("Dummy End point").then().assertThat().statusCode(200);
    }

}