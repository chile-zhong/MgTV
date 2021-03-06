package com.hunan.mgtv.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Question implements Serializable{

	@SerializedName("ms")
	private String ms;

	@SerializedName("answers")
	private List<AnswersItem> answers;

	@SerializedName("title")
	private String title;

	@SerializedName("qid")
	private String qid;



	public void setMs(String ms){
		this.ms = ms;
	}

	public String getMs(){
		return ms;
	}

	public void setAnswers(List<AnswersItem> answers){
		this.answers = answers;
	}

	public List<AnswersItem> getAnswers(){
		return answers;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setQid(String qid){
		this.qid = qid;
	}

	public String getQid(){
		return qid;
	}

	@Override
 	public String toString(){
		return 
			"Question{" + 
			"ms = '" + ms + '\'' + 
			",answers = '" + answers + '\'' + 
			",title = '" + title + '\'' + 
			",qid = '" + qid + '\'' + 
			"}";
		}
}