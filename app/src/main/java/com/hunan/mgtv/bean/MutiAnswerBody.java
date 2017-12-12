package com.hunan.mgtv.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class MutiAnswerBody implements Serializable{

	@SerializedName("ms")
	private String ms;

	@SerializedName("qid")
	private String qid;

	@SerializedName("aid")
	private String[] aid;

	public void setMs(String ms){
		this.ms = ms;
	}

	public String getMs(){
		return ms;
	}

	public void setQid(String qid){
		this.qid = qid;
	}

	public String getQid(){
		return qid;
	}

	public void setAid(String[] aid){
		this.aid = aid;
	}

	public String[] getAid(){
		return aid;
	}

	@Override
 	public String toString(){
		return 
			"MutiAnswerBody{" + 
			"ms = '" + ms + '\'' + 
			",qid = '" + qid + '\'' + 
			",aid = '" + aid + '\'' + 
			"}";
		}
}