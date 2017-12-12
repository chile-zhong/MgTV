package com.hunan.mgtv.bean;

import com.google.gson.annotations.SerializedName;
import com.zhouyou.http.model.ApiResult;

import java.io.Serializable;

public class WatermarkResponse implements Serializable{

	@SerializedName("xh")
	private int xh;

	@SerializedName("code")
	private int code;

	@SerializedName("question")
	private Question question;

	@SerializedName("pid")
	private String pid;

	@SerializedName("typ")
	private String typ;

	public void setXh(int xh){
		this.xh = xh;
	}

	public int getXh(){
		return xh;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setQuestion(Question question){
		this.question = question;
	}

	public Question getQuestion(){
		return question;
	}

	public void setPid(String pid){
		this.pid = pid;
	}

	public String getPid(){
		return pid;
	}

	public void setTyp(String typ){
		this.typ = typ;
	}

	public String getTyp(){
		return typ;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"xh = '" + xh + '\'' + 
			",code = '" + code + '\'' + 
			",question = '" + question + '\'' + 
			",pid = '" + pid + '\'' + 
			",typ = '" + typ + '\'' + 
			"}";
		}
}