package tests;

import config.AppiumConfig;
import models.Auth;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

public class DeleteContactTests extends AppiumConfig {
    @BeforeClass
    public void PreCondition(){
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83@gmail.com").password("Aa13579$").build())
                .submitLogin();
    }
    @Test
    public void deleteFirstContact(){
        new ContactListScreen(driver)
                .deleteFirstContact()
                .isListSizeLessThenOne();

    }

    @Test
    public void removeAllContactSuccess(){
        new ContactListScreen(driver)
                .removeAllContact()
                .isNoContactHere();

    }
}
