package com.sopeng.instagram.common.api.repo;

public class InstagramMediaData
{
	private String userID;
	private String profilePicture;
	private String thumbnail;
	private String lowPicture;
	private String standardPicture;
	
	// Feed
	public InstagramMediaData(String userid, String profilePic, String thumb, String lowPic, String stdPic)
	{
		userID = userid;
		profilePicture = profilePic;
		thumbnail = thumb;
		lowPicture = lowPic;
		standardPicture = stdPic;
	}

	// Profile
	public InstagramMediaData(String userid, String profilePic)
	{
		this(userid, profilePic, null, null, null);
	}
	
	public String getUserID()
	{
		return userID;
	}

	public String getProfilePicture()
	{
		return profilePicture;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public String getLowPicture()
	{
		return lowPicture;
	}

	public String getStandardPicture()
	{
		return standardPicture;
	}

}
