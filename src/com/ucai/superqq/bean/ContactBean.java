package com.ucai.superqq.bean;

import java.io.Serializable;

public class ContactBean implements Serializable {

	private String result;
	private int myuid;
	private int cuid;
	private boolean isGetMyLocation;
	private boolean isShowMyLocation;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getMyuid() {
		return myuid;
	}
	public void setMyuid(int myuid) {
		this.myuid = myuid;
	}
	public int getCuid() {
		return cuid;
	}
	public void setCuid(int cuid) {
		this.cuid = cuid;
	}
	public boolean isGetMyLocation() {
		return isGetMyLocation;
	}
	public void setGetMyLocation(boolean isGetMyLocation) {
		this.isGetMyLocation = isGetMyLocation;
	}
	public boolean isShowMyLocation() {
		return isShowMyLocation;
	}
	public void setShowMyLocation(boolean isShowMyLocation) {
		this.isShowMyLocation = isShowMyLocation;
	}
	public ContactBean() {
		// TODO Auto-generated constructor stub
	}
	public ContactBean(String result, int myuid, int cuid,
			boolean isGetMyLocation, boolean isShowMyLocation) {
		super();
		this.result = result;
		this.myuid = myuid;
		this.cuid = cuid;
		this.isGetMyLocation = isGetMyLocation;
		this.isShowMyLocation = isShowMyLocation;
	}
	@Override
	public String toString() {
		return "ContactBean [result=" + result + ", myuid=" + myuid + ", cuid="
				+ cuid + ", isGetMyLocation=" + isGetMyLocation
				+ ", isShowMyLocation=" + isShowMyLocation + "]";
	}
	
}
