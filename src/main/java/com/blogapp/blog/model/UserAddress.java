package com.blogapp.blog.model;

public class UserAddress {
	
	private String street ;
	private String suite ;
	private String city ;
	private String zipcode;
	private AddressGeo geo;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSuite() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public AddressGeo getGeo() {
		return geo;
	}
	public void setGeo(AddressGeo geo) {
		this.geo = geo;
	}
	
	
	
	
}
