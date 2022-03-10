package com.protectionDogs.protection.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Dog {
	@Id
	private String dogId;
	private String ownerId;
	private String dogName;
	private Boolean breed;
	private String dob;
	private String gender;
	private List<String> dogImages;
	private Boolean isVetChecked;
	private Boolean isPedigree;
	private Boolean isPassport;
	private Boolean isVaccinated;
	private String weight;
	private String trainingNotes;
	private String breedName;
	
	public String getDogId() {
		return dogId;
	}
	public void setDogId(String dogId) {
		this.dogId = dogId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getDogName() {
		return dogName;
	}
	public void setDogName(String dogName) {
		this.dogName = dogName;
	}
	public Boolean getBreed() {
		return breed;
	}
	public void setBreed(Boolean breed) {
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
	public List<String> getDogImages() {
		return dogImages;
	}
	public void setDogImages(List<String> dogImages) {
		this.dogImages = dogImages;
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
	public String getBreedName() {
		return breedName;
	}
	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}
	
	
}
