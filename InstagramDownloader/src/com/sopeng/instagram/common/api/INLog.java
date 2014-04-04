package com.sopeng.instagram.common.api;

import android.util.Log;

public class INLog
{
	private static boolean isDebugMode = true;
	
	public static void d(String tag, String msg)
	{
		if(isDebugMode)
		{	
			if(msg == null)
				msg = "";
			Log.d("["+tag+"]", msg);
		}
	}
	
	public static void e(String tag, String msg)
	{
		if(isDebugMode)
		{	
			if(msg == null)
				msg = "";
			Log.e("["+tag+"]", msg);
		}
	}
	
	public static void i(String tag, String msg)
	{
		if(isDebugMode)
		{	
			if(msg == null)
				msg = "";
			Log.i("["+tag+"]", msg);
		}
	}
	
	public static void v(String tag, String msg)
	{
		if(isDebugMode)
		{	
			if(msg == null)
				msg = "";
			Log.v("["+tag+"]", msg);
		}
	}
	
	public static void w(String tag, String msg)
	{
		if(isDebugMode)
		{	
			if(msg == null)
				msg = "";
			Log.w("["+tag+"]", msg);
		}
	}

	public static void e(String tag, String msg, Exception e)
	{
		e(tag,msg);
	}
}
