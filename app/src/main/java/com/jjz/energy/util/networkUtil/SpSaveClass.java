package com.jjz.energy.util.networkUtil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.jjz.energy.util.SpUtil;
import com.jjz.energy.util.Utils;

public class SpSaveClass
{

	public static SpSaveClass instance;

	private SpUtil spUtil = null;

	private SpSaveClass(Context context)
	{
		spUtil = SpUtil.init(context);
	}

	public static SpSaveClass getInstance(Context context)
	{
		if (instance == null)
			instance = new SpSaveClass(context);
		return instance;
	}

	/**
	 * 
	 * @param obj
	 */
	public <T> void saveClass(T obj)
	{
		if (obj != null)
			spUtil.commit(obj.getClass().getSimpleName(), JSONObject.toJSONString(obj));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public <T> T readClass(Class<T> key)
	{
		try
		{

			String saveClassStr = spUtil.readString(key.getSimpleName());

			if (!TextUtils.isEmpty(saveClassStr))
			{
				return Utils.getObject(saveClassStr, key);
			}

			return null;

		} catch (Exception e)
		{
			Log.e(getClass().getSimpleName(), "decode " + key.getSimpleName() + "\n" + e.toString());
			return null;
		}
	}
}
