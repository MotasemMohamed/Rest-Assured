package org.example;

import org.testng.annotations.Test;

public class Add_Delegate_Appointment_Testcase {
    Get_Essential_Information getInfoobject;
    @Test
    public void RunTestcase ()
    {
        getInfoobject= new Get_Essential_Information();
        getInfoobject.GetVehicleClasses();
        getInfoobject.GetServices();
        getInfoobject.Get_Available_Appointment_Time_Slots();
        getInfoobject.BOOK_WITH_DELEGATION();
        getInfoobject.validate_SMS_Verify_Code_Add();

    }

}
