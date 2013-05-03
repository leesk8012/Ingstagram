package com.sopeng.instagram.api.repo;

public class ProfilePictureData
{
	private String userID;
	private String profilePictureURL;
	
	public ProfilePictureData(String userid, String picUrl)
	{
		userID = userid;
		profilePictureURL = picUrl;
	}
	
	public String getUserID()
	{
		return userID;
	}
	public String getPicURL()
	{
		return profilePictureURL;
	}
}
