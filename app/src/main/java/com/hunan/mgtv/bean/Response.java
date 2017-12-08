package com.hunan.mgtv.bean;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("code")
	private int code;

	@SerializedName("question")
	private Question question;

	@SerializedName("pid")
	private String pid;

	@SerializedName("typ")
	private String typ;

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
			"code = '" + code + '\'' + 
			",question = '" + question + '\'' + 
			",pid = '" + pid + '\'' + 
			",typ = '" + typ + '\'' + 
			"}";
		}
}