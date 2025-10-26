package com.spacetest.demoblaze.tests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert; // Using SoftAssert
import com.spacetest.demoblaze.base.BaseTest;

public class FooterTest extends BaseTest {

    @Test(description = "Verify that the information in the footer is correct",
          groups = {"Footer", "Regression"})
    public void testFooterInformationIsCorrect() {
        // SoftAssert lets us check all assertions and report all failures at the end
        SoftAssert softAssert = new SoftAssert();

        // 1. Get footer texts
        String header = homePage.footer().getGetInTouchHeaderText();
        String address = homePage.footer().getAddressText();
        String phone = homePage.footer().getPhoneText();
        String email = homePage.footer().getEmailText();

        // 2. Verifications
        softAssert.assertEquals(header, "Get in Touch", "Footer header text is incorrect.");
        softAssert.assertTrue(address.contains("Address: 2390 El Camino Real"), "Footer address is incorrect.");
        softAssert.assertTrue(phone.contains("Phone: +440 123456"), "Footer phone is incorrect.");
        softAssert.assertTrue(email.contains("Email: demo@blazemeter.com"), "Footer email is incorrect.");

        // 3. Report all failures
        softAssert.assertAll();
    }
}