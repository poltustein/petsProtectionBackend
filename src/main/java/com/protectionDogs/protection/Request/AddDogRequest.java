package com.protectionDogs.protection.Request;

import com.protectionDogs.protection.Util.Utility;

public class AddDogRequest {
	
	private String dogName;
	private String breed;
	private String dob;
	private String gender;
	private Boolean isVetChecked;
	private Boolean isPedigree;
	private Boolean isPassport;
	private Boolean isVaccinated;
	private String weight;
	private String trainingNotes;
	
	public String getDogName() {
		return dogName;
	}
	public void setDogName(String dogName) {
		this.dogName = dogName;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getIsVetChecked() {
		return isVetChecked;
	}
	public void setIsVetChecked(Boolean isVetChecked) {
		this.isVetChecked = isVetChecked;
	}
	public Boolean getIsPedigree() {
		return isPedigree;
	}
	public void setIsPedigree(Boolean isPedigree) {
		this.isPedigree = isPedigree;
	}
	public Boolean getIsPassport() {
		return isPassport;
	}
	public void setIsPassport(Boolean isPassport) {
		this.isPassport = isPassport;
	}
	public Boolean getIsVaccinated() {
		return isVaccinated;
	}
	public void setIsVaccinated(Boolean isVaccinated) {
		this.isVaccinated = isVaccinated;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTrainingNotes() {
		return trainingNotes;
	}
	public void setTrainingNotes(String trainingNotes) {
		this.trainingNotes = trainingNotes;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
}
