package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Add_Delegate_Appointment_Testcase {
    Get_Essential_Information getInfoobject;
    public  static RequestSpecification BaseUrl ;

    @BeforeTest
    public static void  createRequestSpecification ()
    {
        BaseUrl = new RequestSpecBuilder().setBaseUri("Dummy Base Url").build();
    }
    @Test
    public void RunTestcase ()
    {
        getInfoobject= new Get_Essential_Information();
        getInfoobject.GetVehicleClasses();
        getInfoobject.GetServices();
        getInfoobject.Get_Available_Appointment_Time_Slots();
        getInfoobject.BOOK_WITH_DELEGATION();
        getInfoobject.validate_SMS_Verify_Code_Add();
//        getInfoobject.send_SMS_Verify_Code_cancel();
//        getInfoobject.validate_SMS_Verify_Code_cancel();
    }

}
