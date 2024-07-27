package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import endPoints.UserEndPoints;
import io.restassured.response.Response;
import payload.User;

public class UserTest {
	
	Faker faker;
	User userPayload;
	Logger lg;
	
	@BeforeClass
	public void setUpData()
	{
		faker=new Faker();
		userPayload=new User();
		lg=LogManager.getLogger(this);
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setUsername(faker.name().username());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setUserStatus(0);
		lg.info("Data has been created sucessfully");
		
		

	}
	
	@Test(priority=1)
	public void testCreateUser()
	{ 
		
		lg.info("Creating user ");
	  Response res=	UserEndPoints.createUser(userPayload);
	  res.then().log().all();
	//  Assert.assertEquals(res.statusCode(), 200);
	  System.out.println("User has been created ");
	  lg.info("user has been create sucessfully ");
	 
	}
	
	
	@Test(priority=2)
	public void testGetUser()
	{   

		Response res=UserEndPoints.readUser(userPayload.getUsername());
		res.then().log().body();
		Assert.assertEquals(res.statusCode(),200);
	
	}
	
	@Test(priority=3)
	public void testUpdateUser()
	{
		userPayload.setFirstName("Shoaib ");
		
		Response res=UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
		res.then().log().body();
	Response re=UserEndPoints.readUser(userPayload.getUsername());
	re.then().log().body();
		
	}
	
	@Test(priority=4)
	public void testDelUser()
	{
		Response res=UserEndPoints.delUser(userPayload.getUsername());
		res.then().log().status();
	}
	
}
