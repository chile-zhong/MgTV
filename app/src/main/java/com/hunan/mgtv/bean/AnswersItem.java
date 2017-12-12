package com.hunan.mgtv.bean;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class AnswersItem implements Serializable{

	@SerializedName("txt")
	private String txt;

	@SerializedName("aid")
	private String aid;

	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

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