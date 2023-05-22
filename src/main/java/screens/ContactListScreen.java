package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ContactListScreen extends BaseScreen{
    public ContactListScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }
    // xpath="//*[@resource-id='com.sheygam.contactapp:id/action_bar']/android.widget.TextView"
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/action_bar']/android.widget.TextView")
    AndroidElement activityTextView;
    @FindBy(xpath = "//*[@content-desc='More options']")
    AndroidElement menuOptions;
    @FindBy(xpath = "//*[@content-desc='More options']")
    List<AndroidElement> menuOptionsList;
    @FindBy(xpath = "//*[@text='Logout']")
    AndroidElement logoutButton;
    @FindBy(xpath = "//*[@content-desc='add']")
    AndroidElement plusButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowName']")
    List<AndroidElement> contactNameList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    List<AndroidElement> contactList;
    @FindBy(id="android:id/button1")
    AndroidElement yesBButton;
    @FindBy (id ="com.sheygam.contactapp:id/emptyTxt")
    AndroidElement noContactHereTextView;
    int  countBefore;
    int countAfter;


    public ContactListScreen deleteFirstContact(){

        isActivityTitleDisplayed("Contact list");
        countBefore=contactList.size();
        System.out.println(countBefore);
        AndroidElement first = contactList.get(0);
        Rectangle rectangle = first.getRect();

        int xFrom=rectangle.getX()+rectangle.getWidth()/8;


        int yFrom= rectangle.getY()+rectangle.getHeight()/2;
        // int xTo=rectangle.getX()+(rectangle.getWidth()/8)*7;
        int xto = rectangle.getWidth()-xFrom;
        int yTo=yFrom;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xFrom,yFrom))
                .moveTo(PointOption.point(xto,yTo))
                .release().perform();

        should(yesBButton,8);
        yesBButton.click();
        shoulLessOne(contactList,countBefore);
        countAfter=contactList.size();
        System.out.println(countAfter);
        return this;
    }

    public ContactListScreen isContactAddedByName(String name,String lastName){
        // List<AndroidElement> list =  driver.findElements(By.xpath(""));
        isShouldHave(activityTextView,"Contact list",5);
        System.out.println("size of list" +contactNameList.size());
        boolean isPresent=false;

        for (AndroidElement el:contactNameList) {
            if(el.getText().equals(name + " "+lastName)){
                isPresent = true;
                break;
            }

        }

        Assert.assertTrue(isPresent);

        return this;
    }

    public AddNewContactScreen openContactForm(){
        if(activityTextView.getText().equals("Contact list"))
            plusButton.click();
        return new AddNewContactScreen(driver);
    }


    public ContactListScreen isAccountOpened(){
        Assert.assertTrue(isActivityTitleDisplayed("Contact list"));
        return this;
    }
    public boolean isActivityTitleDisplayed(String text){
        //return activityTextView.getText().contains("Contact list");
        return isShouldHave(activityTextView,text,10);
    }

    public AuthenticationScreen logout(){
        if(activityTextView.getText().equals("Contact list")) {
            menuOptions.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);
    }
    public AuthenticationScreen logout2(){
        if(isElementDisplayed(menuOptions)) {
            menuOptions.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);
    }

    public AuthenticationScreen logout3(){
        if(isElementPresentInList(menuOptionsList)) {
            menuOptions.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);
    }

    public ContactListScreen isListSizeLessThenOne(){

        Assert.assertEquals(countBefore-countAfter,1);

        return this;
    }
    public ContactListScreen removeAllContact() {
        pause(1000);
        while (driver.findElements(By.xpath("//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")).size()>0){
            deleteFirstContact();
        }


        return this;
    }

    public ContactListScreen isNoContactHere(){
        isShouldHave(noContactHereTextView,"No Contacts. Add One more!",10);
        return this;
    }
}