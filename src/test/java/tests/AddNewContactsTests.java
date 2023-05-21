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

    @Test
    public void createNewContactSuccess() {
        int i = new Random().nextInt(1000) + 1000;
        Contact contact = Contact.builder()
                .name("Simon")
                .lastName("Wow" + i)
                .email("wow" + i + "@gmail.com")
                .phone("6789456" + i)
                .address("NY")
                .description("The best friend")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastName());
    }

    @Test
    public void createContactWithEmptyName() {
        Contact contact = Contact.builder()
                .lastName("Dow")
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
    public void createNewContactSuccessReq() {
        int i = new Random().nextInt(1000) + 1000;
        Contact contact = Contact.builder()
                .name("Simon")
                .lastName("Wow" + i)
                .email("wow" + i + "@gmail.com")
                .phone("6789456" + i)
                .address("NY")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastName());
    }

    @Test
    public void createContactWithEmptyLastName() {
        Contact contact = Contact.builder()
                .name("Sam")
                .email("dow@gmail.com")
                .phone("678945633333")
                .address("NY")
                .description("Empty last name")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{lastName=must not be blank}");

    }

    @Test
    public void createContactWithWrongEmail() {
        Contact contact = Contact.builder()
                .name("Sam")
                .lastName("Swong")
                .email("dowgmail.com")
                .phone("678945633333")
                .address("NY")
                .description("Wrong email")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{email=must be a well-formed email address}");

    }

    @Test
    public void createContactWithWrongPhone() {
        Contact contact = Contact.builder()
                .name("Sam")
                .lastName("Swong")
                .email("dow@gmail.com")
                .phone("6789")
                .address("NY")
                .description("Wrong phone")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{phone=Phone number must contain only digits! And length min 10, max 15!}");

    }

    @Test
    public void createContactWithEmptyAddress() {
        Contact contact = Contact.builder()
                .name("Sam")
                .lastName("Swong")
                .email("dow@gmail.com")
                .phone("6789685340")
                .description("Empty address")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{address=must not be blank}");

    }


    @AfterClass
    public void postCondition() {

        new ContactListScreen(driver)
                .logout();
    }
}