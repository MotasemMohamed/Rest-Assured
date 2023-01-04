package org.example;

import org.testng.annotations.Test;

public class Cancel_Appointment_Testcase {
    Get_Essential_Information getInfoobject;
    @Test
    public void RunTestcase ()
    {
        getInfoobject= new Get_Essential_Information();
        getInfoobject.send_SMS_Verify_Code_cancel();
        getInfoobject.validate_SMS_Verify_Code_cancel();
    }

}
