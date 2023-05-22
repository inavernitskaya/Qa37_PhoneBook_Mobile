package tests;

import config.AppiumConfig;
import models.Auth;
import models.Contact;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class AddNewContactsTests extends AppiumConfig {
    @BeforeClass
    public void preCondition() {
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("inna_83@gmail.com").password("Aa13579$").build())
                .submitLogin()
        .isActivityTitleDisplayed("Contact list");
    }

    @Test(invocationCount = 3)
    public void createNewContactSuccess(){
        int i = new Random().nextInt(1000)+1000;
        Contact contact = Contact.builder()
                .name("Simon")
                .lastName( "Wow"+i)
                .email("wow"+i+"@gmail.com")
                .phone("6789456"+i)
                .address("NY")
                .description("The best friend")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(),contact.getLastName());



    }

    @Test
    public void createContactWithEmptyName(){
        Contact contact = Contact.builder()
                .lastName( "Dow")
                .email("dow@gmail.com")
                .phone("678945633333")
                .address("NY")
                .description("Empty name")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{name=must not be blank}");

    }
    @Test
    public void createNewContactSuccessReq(){
        int i = new Random().nextInt(1000)+1000;
        Contact contact =Contact.builder()
                .name("Wolf")
                .lastName("Wow"+i)
                .email("wow"+i+"@mail.com")
                .phone("1234572345")
                .address("Rehovot")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(),contact.getLastName());


    }
    @Test
    public void addNewContactEmptyLastName(){

        Contact contact =Contact.builder()
                .name("Neg")
                .email("neg@mail.com")
                .phone("1234572345")
                .address("Rehovot")
                .description("The best friend").build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("Error");
    }

    @AfterClass
    public void postCondition(){

        new ContactListScreen(driver)
                .logout();
    }
}