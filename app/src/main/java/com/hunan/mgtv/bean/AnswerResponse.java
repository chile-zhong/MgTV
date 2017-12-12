package com.hunan.mgtv.bean;

public class AnswerResponse{
	private String reply;

	public void setReply(String reply){
		this.reply = reply;
	}

	public String getReply(){
		return reply;
	}

	@Override
 	public String toString(){
		return 
			"AnswerResponse{" + 
			"reply = '" + reply + '\'' + 
			"}";
		}
}
