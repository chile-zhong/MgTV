package com.hunan.mgtv.bean;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WatermarkBody{

	@SerializedName("water_mark")
	private String waterMark;

	public void setWaterMark(String waterMark){
		this.waterMark = waterMark;
	}

	public String getWaterMark(){
		return waterMark;
	}

	@Override
 	public String toString(){
		return 
			"WatermarkBody{" + 
			"water_mark = '" + waterMark + '\'' + 
			"}";
		}
}