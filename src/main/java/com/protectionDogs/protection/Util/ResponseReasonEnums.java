package com.protectionDogs.protection.Util;

public enum ResponseReasonEnums {
	
	EMPTYINPUT("The request is empty"),
	UNABLETOPROCESS("The request could not be processed. Please try again later!!"),
	SUBCREATED("The request is successfull!!"),
	PROFILEUPDATEDSUCCESSFULLY("Profile has been updated successfully!!"),
	EMPTYISSUE("Please enter the issue!!"),
	VIDEOSAVED("The video has been saved to favourites successfully!!"),
	VIDEONOTSAVED("The video could not be saved to favourites!! "),
	VIDEOUNSAVED("Video has been removed from favourites!!"),
	WRONLOGINMETHOD("You have already logged in with another login method!!"),
	CATEGORIESFOUND("The categories have been found!!"),
	CATEGORYSAVED("The category has been saved successfully!!"),
	CATEGORYDISABLED("The category has been disabled!!"),
	SEARCHDONE("The following videos were found!!"),
	VIDEOSFOUND("The favourite videos have been found"),
	VIDEONOTFOUND("The video does not exist"),
	VIDEOCOULDNOTBEUNSAVED("Video could not be unsaved!!"),
	NOSAVEDVIDEOSFOUND("No favourite videos could be found!!"),
	SUBSUCCESS("You have been successfully subscribed"),
	UNSUBSUCCESS("You have been successfully unsubscribed"),
	ISSUEMAILED("Your has been successfully registered. Our support team will get in touch shortly!!"),
	INVALIDCONTACT("Please enter valid contact number"),
	USERNOTFOUND("The user emailid is not registered with us!!"),
	DOGNOTFOUND("Sorry we could not find the dog!!"),
	SIGNINSUCCESS("The signin is successful"),
	PASSWORDINCORRECT("The password is incorrect"),
	UNAUTHORIZEDACCESS("Unauthorized access"),
	RESETPASSWORD("The new password has been reset"),
	RESETPASSWORDFAILURE("Unable to reset password. Please try again later!!"),
	OTPSENT("An otp has been sent to your registered emailId "),
	OTPTIMEOUT("Your otp has timed out. Please try again later!!"),
	SIGNUPSUCCESS("You have been registered successfully!!"),
	OTPSUCCESS("Your otp is correct"),
	OTPMISMATCH("wrong otp!!"),
	LOGOUTSUCCESS("You have been logged out!!"),
	USERALREADYREGISTERED("User is already registered!!"),
	DOGSAVED("Your dog has been saved successfully!!"),
	DOGNOTSAVED("Your dog could not be saved. Please try again later!!"),
	NOTSUBSCRIBED("You are not subscribed!!"),
	NOTUNSUBSCRIBED("You are not unsubscribed!!"),
	OTPALREADYSENT("An otp has already been sent to you!!"),
	OTPSENTLIMITEXCEEDED("Otp sending limit has been reached today. Please try again later!! "),
	PASSWORDNOTSENT("The new password could not be sent to your registered emailId "),
	LOCATIONLOGSUCCESS("Your current location is loggedin successfully"),
	LOCATIONLOGFAIL("Your current location login failed"),
	PERMISSIONDENIED("You do not have adequate permissions"),
	MANAGERDOESNOTEXIST("The manager emailId does not exist"),
	NEWUSERCREATED("New user has been created"),
	NEWUSERCREATEFAILED("Unable to create new user. Please try again later!!"),
	USERALREADYEXISTS("The user already exists!!"),
	MANAGERNOTALLOWED("Admin does not need a manager"),
	REPORTEESNOTFOUND("No reportees were found!!"),
	REPORTEESFOUND("Reportees were found successfully!!"),
	LOCATIONDISABLED("The employee's location is disabled!!"),
	LOCATIONSNOTFOUND("The employee's locations are unavailable for this day"),
	EXPENSEINITIATED("The expense has been initiated"),
	EXPENSEINITIATEFAIL("The expense initiate has failed!!"),
	FILEUPLOADFAIL("The file cannot be uploaded"),
	FILEUPLOADSUCCESS("The file has been uploaded successfully"),
	FILEUPLOADLIMIT("You cannot upload any more images"),
	EXPENSENOTFOUND("No expenses found"),
	EXPENSEFOUND("The expenses have been found"),
	FILENOTFOUND("The file could not be found"),
	DELETESUCCESS("The user is successfully deleted!!"),
	DELETEFAIL("The user deletion failed!!"),
	NEWUSERPASSWORD("The new user created password: "),
	EDITUSER("The user has been edited successfully!!"),
	CREATEUSER("The user has been created successfully!!"),
	CREATEUSERFAILED("The user creation has failed!!"),
	EDITUSERFAILED("The user edit has failed!!");
	
	private String reason;
	
	
	private ResponseReasonEnums(String reason) {
		this.reason = reason;
	}
	
	public String toString() {
		return reason;
	}
	
	public String toString(String password) {
		return reason+password;
	}

}
