package tests;

import config.AppiumConfig;
import models.Auth;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;
import screens.SplashScreen;

public class LoginTests extends AppiumConfig {
    @Test
    public void LoginSuccess() {
        //boolean result = new SplashScreen(driver)
        //       .checkCurrentVersion("Version 1.0.0")
        boolean result = new AuthenticationScreen(driver)
                .fillEmail("inna_83@gmail.com")
                .fillPassword("Aa13579$")
                .submitLogin()
                .isActivityTitleDisplayed("Contact list");
        Assert.assertTrue(result);
    }

    @Test
    public void loginSuccessModel() {
        // boolean result = new SplashScreen(driver)
        //        .checkCurrentVersion("Version 1.0.0")
        boolean result = new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83@gmail.com").password("Aa13579$").build())
                .submitLogin()
                .isActivityTitleDisplayed("Contact list");
        Assert.assertTrue(result);
    }

    @Test
    public void loginSuccessModel2() {
        Assert.assertTrue(new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83@gmail.com").password("Aa13579$").build())
                .submitLogin()
                .isActivityTitleDisplayed("Contact list"));
    }

    @Test
    public void loginWrongEmail() {
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83gmail.com").password("Aa13579$").build())
                .submitLoginNegative()
                .isErrorMessageContainsText("Login or Password incorrect");
    }

    @Test
    public void loginWrongPassword() {
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83@gmail.com").password("Aa135").build())
                .submitLoginNegative()
                .isErrorMessageContainsText("Login or Password incorrect");
    }

    @AfterMethod
    public void postCondition() {

        new ContactListScreen(driver).logout();


 }


}

