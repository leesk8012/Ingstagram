package com.sopeng.instagram.common.api.repo;

import java.util.Vector;

public class InstagramMediaRepo
{
	private static InstagramMediaRepo infoRepository;
	private Vector<InstagramMediaData> datas;

	private InstagramMediaRepo()
	{
		datas = new Vector<InstagramMediaData>();
	}
	
	public static InstagramMediaRepo getInstance()
	{
		if(infoRepository == null)
		{
			synchronized (InstagramMediaRepo.class)
			{
				if(infoRepository == null)
					infoRepository = new InstagramMediaRepo();					
			}
		}
		return infoRepository;
	}
	
	public void addProfile(String userID, String profilePic)
	{
		if(datas != null)
		{
			datas.add(new InstagramMediaData(userID, profilePic));
		}
	}
	
	public void addProfile(String userID, String profilePic, String thumb, String lowPic, String stdPic)
	{
		if(datas != null)
		{
			datas.add(new InstagramMediaData(userID, profilePic, thumb, lowPic, stdPic));
		}
	}
	
	public void removeAll()
	{
		datas = new Vector<InstagramMediaData>();
	}
	
	public InstagramMediaData getData(int position)
	{
		if(datas != null)
		{
			return datas.get(position);
		}
		return null;
	}
	
	public String [] getProfilePics()
	{
		String [] profPics = new String[datas.size()];
		for(int i=0;i<profPics.length;i++)
		{
			profPics[i] = datas.get(i).getProfilePicture();
		}
		return profPics;
	}
	
	public String [] getThumbPics()
	{
		String [] profPics = new String[datas.size()];
		for(int i=0;i<profPics.length;i++)
		{
			profPics[i] = datas.get(i).getThumbnail();
		}
		return profPics;
	}
}
