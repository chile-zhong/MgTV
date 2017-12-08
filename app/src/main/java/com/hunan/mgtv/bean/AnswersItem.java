package com.hunan.mgtv.bean;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AnswersItem{

	@SerializedName("txt")
	private String txt;

	@SerializedName("aid")
	private String aid;

	public void setTxt(String txt){
		this.txt = txt;
	}

	public String getTxt(){
		return txt;
	}

	public void setAid(String aid){
		this.aid = aid;
	}

	public String getAid(){
		return aid;
	}

	@Override
 	public String toString(){
		return 
			"AnswersItem{" + 
			"txt = '" + txt + '\'' + 
			",aid = '" + aid + '\'' + 
			"}";
		}
}