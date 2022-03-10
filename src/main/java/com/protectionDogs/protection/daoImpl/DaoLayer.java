package com.protectionDogs.protection.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.protectionDogs.protection.Request.CategoryVideosRequest;
import com.protectionDogs.protection.Request.GetDogRequest;
import com.protectionDogs.protection.Request.HomeRequest;
import com.protectionDogs.protection.Request.SavedVideosRequest;
import com.protectionDogs.protection.Request.SearchRequest;
import com.protectionDogs.protection.pojo.BannerResource;
import com.protectionDogs.protection.pojo.Category;
import com.protectionDogs.protection.pojo.Dog;
import com.protectionDogs.protection.pojo.HomeResource;
import com.protectionDogs.protection.pojo.SavedVideo;
import com.protectionDogs.protection.pojo.SubscriptionPlans;
import com.protectionDogs.protection.pojo.TempUser;
import com.protectionDogs.protection.pojo.User;

@Service
public class DaoLayer {

	@Autowired
	private MongoTemplate mongoTemplate;

	private Logger log = LoggerFactory.getLogger(DaoLayer.class);

	public TempUser getTempUserByEmailId(String emailId) {
		if (emailId == null || emailId.isEmpty())
			return null;
		Query query = new Query().addCriteria(Criteria.where("emailId").is(emailId));
		TempUser user = null;
		try {
			user = mongoTemplate.findOne(query, TempUser.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing temp user=" + emailId + ",error=" + e.getStackTrace());
		}
		return user;
	}

	public Boolean saveTempUser(TempUser user) {
		if (user == null)
			return false;
		try {
			user = mongoTemplate.save(user);
			log.info("Saved user");
			return true;
		} catch (Exception e) {
			log.error("Exception while saving temp user=" + user.getEmailId() + ",error=" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public Boolean saveUser(User user) {
		if (user == null)
			return false;
		try {
			user = mongoTemplate.save(user);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving user=" + user.getEmailId() + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public Boolean deleteTempUser(TempUser user) {
		if (user == null)
			return false;
		try {
			mongoTemplate.remove(user, "tempUser");
			return true;
		} catch (Exception e) {
			log.error("Exception while deleting temp user=" + user.getEmailId() + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public User getUserByEmailId(String emailId) {
		if (emailId == null || emailId.isEmpty())
			return null;
		Query query = new Query().addCriteria(Criteria.where("emailId").is(emailId));
		User user = null;
		try {
			user = mongoTemplate.findOne(query, User.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing user=" + emailId + ",error=" + e.getStackTrace());
			e.printStackTrace();
		}
		return user;
	}

	public List<HomeResource> getHomeVideos(HomeRequest request) {
		if (request == null)
			return null;

		Query query = new Query().addCriteria(Criteria.where("isDailyVideo").is(true))
		      .with(Sort.by(Sort.Direction.DESC, "createdOn").and(Sort.by(Sort.Direction.ASC, "resourceId")))
		      .skip(request.getPageIndex()).limit(request.getPageSize());
		List<HomeResource> resources = new ArrayList<>();
		try {
			resources = mongoTemplate.find(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return resources;
	}
	
	

	public Long getHomeVideosCount(HomeRequest request) {
		if (request == null)
			return null;

		Query query = new Query();
		Long count = 0L;
		try {
			return mongoTemplate.count(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return count;
	}

	public List<BannerResource> getBannerVideos(HomeRequest request) {
		if (request == null)
			return null;

		Query query = new Query()
		      .with(Sort.by(Sort.Direction.DESC, "createdOn").and(Sort.by(Sort.Direction.ASC, "bannerId")))
		      .skip(request.getPageIndex()).limit(request.getPageSize());
		List<BannerResource> resources = new ArrayList<>();
		try {
			resources = mongoTemplate.find(query, BannerResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return resources;
	}

	public List<SubscriptionPlans> getSubscriptionPlans() {
		Query query = new Query().with(Sort.by(Sort.Direction.ASC, "planCost"))
		      .addCriteria(Criteria.where("isEnabled").is(true));
		List<SubscriptionPlans> plans = mongoTemplate.find(query, SubscriptionPlans.class);
		try {
			plans = mongoTemplate.find(query, SubscriptionPlans.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return plans;
	}

	public Boolean saveDog(Dog dog) {
		try {
			mongoTemplate.save(dog);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving dog " + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public List<Dog> getDogs(String emailid, GetDogRequest request) {
		Query query = new Query().with(Sort.by(Sort.Direction.DESC, "dogName").and(Sort.by(Sort.Direction.ASC, "dogId")))
		      .addCriteria(Criteria.where("ownerId").is(emailid)).skip(request.getPageIndex())
		      .limit(request.getPageSize());
		List<Dog> dogs = null;
		try {
			dogs = mongoTemplate.find(query, Dog.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing dogs " + ",error=" + e.getStackTrace());
		}
		return dogs;
	}

	public Long getDogsCount(String emailid) {
		Query query = new Query().addCriteria(Criteria.where("ownerId").is(emailid));
		Long count = 0L;
		try {
			count = mongoTemplate.count(query, Dog.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing dogs " + ",error=" + e.getStackTrace());
		}
		return count;
	}

	public Dog getDogById(String emailid, String dogId) {
		Query query = new Query().addCriteria(Criteria.where("ownerId").is(emailid).and("dogId").is(dogId));
		Dog dog = null;
		try {
			dog = mongoTemplate.findOne(query, Dog.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing dog " + ",error=" + e.getStackTrace());
		}
		return dog;
	}

	public SavedVideo getSavedVideoById(String ownerId, String videoId) {
		Query query = new Query().addCriteria(Criteria.where("videoId").is(videoId).and("userId").is(ownerId));
		SavedVideo savedVideo = null;
		try {
			savedVideo = mongoTemplate.findOne(query, SavedVideo.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing saved video " + ",error=" + e.getStackTrace());
		}
		return savedVideo;
	}

	public Boolean deleteSavedVideo(SavedVideo video) {
		if (video == null)
			return false;
		try {
			mongoTemplate.remove(video, "savedVideo");
			return true;
		} catch (Exception e) {
			log.error("Exception while deleting saved video=" + video.getSavedId() + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public Long getSavedVideosCount(String emailid) {
		Query query = new Query().addCriteria(Criteria.where("userId").is(emailid));
		Long count = 0L;
		try {
			count = mongoTemplate.count(query, SavedVideo.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing saved videos count " + ",error=" + e.getStackTrace());
		}
		return count;
	}

	public Boolean saveSavedVideo(SavedVideo video) {
		try {
			mongoTemplate.save(video);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving video " + ",error=" + e.getStackTrace());
		}
		return false;
	}
	
	public Boolean saveHomeVideo(HomeResource resource) {
		try {
			mongoTemplate.save(resource);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving resource " + ",error=" + e.getStackTrace());
		}
		return false;
	}
	
	public Boolean saveBannerVideo(BannerResource resource) {
		try {
			mongoTemplate.save(resource);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving banner resource " + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public List<SavedVideo> getSavedVideos(SavedVideosRequest request, String emailid) {
		if (request == null)
			return null;

		Query query = new Query().addCriteria(Criteria.where("userId").is(emailid))
		      .with(Sort.by(Sort.Direction.DESC, "savedOn").and(Sort.by(Sort.Direction.ASC, "savedId")))
		      .skip(request.getPageIndex()).limit(request.getPageSize());
		List<SavedVideo> videos = new ArrayList<>();
		try {
			videos = mongoTemplate.find(query, SavedVideo.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing saved videos for " + emailid + ",error=" + e.getStackTrace());
		}
		return videos;
	}

	public List<SavedVideo> getSavedVideosFromHomeVideos(List<HomeResource> videos, String emailid) {
		if (videos == null)
			return null;
		List<String> urls = new ArrayList<String>();
		for (HomeResource video : videos)
			urls.add(video.getResourceId());
		Query query = new Query().addCriteria(Criteria.where("userId").is(emailid).and("videoId").in(urls));
		List<SavedVideo> savedVideos = new ArrayList<>();
		try {
			savedVideos = mongoTemplate.find(query, SavedVideo.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing saved videos for " + emailid + ",error=" + e.getStackTrace());
		}
		return savedVideos;
	}

	public HomeResource getHomeVideoById(String videoId) {
		if (videoId == null || videoId.isEmpty())
			return null;

		Query query = new Query().addCriteria(Criteria.where("resourceId").is(videoId));
		HomeResource resource = null;
		try {
			resource = mongoTemplate.findOne(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resource " + videoId + ",error=" + e.getStackTrace());
		}
		return resource;
	}
	
	
	public BannerResource getBannerVideoById(String videoId) {
		if (videoId == null || videoId.isEmpty())
			return null;
		Query query = new Query().addCriteria(Criteria.where("bannerId").is(videoId));
		BannerResource resource = null;
		try {
			resource = mongoTemplate.findOne(query, BannerResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing banner resource " + videoId + ",error=" + e.getStackTrace());
		}
		return resource;
	}

	public Boolean saveCategory(Category category) {
		try {
			mongoTemplate.save(category);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving category " + ",error=" + e.getStackTrace());
		}
		return false;
	}

	public Category getCategoryByName(String categoryName) {
		if (categoryName == null || categoryName.isEmpty())
			return null;
		Query query = new Query().addCriteria(Criteria.where("categoryName").is(categoryName));
		Category category = null;
		try {
			category = mongoTemplate.findOne(query, Category.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing category " + categoryName + ",error=" + e.getStackTrace());
		}
		return category;
	}

	public List<Category> getCategories() {
		Query query = new Query().addCriteria(Criteria.where("isEnabled").is(true));
		List<Category> categories = new ArrayList<>();
		try {
			categories = mongoTemplate.find(query, Category.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing categories ,error=" + e.getStackTrace());
		}
		return categories;
	}

	public List<HomeResource> getHomeCategoryVideos(CategoryVideosRequest request) {
		if (request == null)
			return null;

		Query query = new Query().addCriteria(Criteria.where("categories._id").is(request.getCategoryId()))
		      .with(Sort.by(Sort.Direction.DESC, "createdOn").and(Sort.by(Sort.Direction.ASC, "resourceId")))
		      .skip(request.getPageIndex()).limit(request.getPageSize());
		List<HomeResource> resources = new ArrayList<>();
		try {
			resources = mongoTemplate.find(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return resources;
	}

	public Long getHomeCategoryVideosCount(CategoryVideosRequest request) {
		if (request == null)
			return null;

		Query query = new Query().addCriteria(Criteria.where("categories._id").is(request.getCategoryId()));
		Long count = 0L;
		try {
			return mongoTemplate.count(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return count;
	}

	public List<HomeResource> searchHomeVideos(SearchRequest request) {
		if (request == null)
			return null;
		Query query = new Query().addCriteria(Criteria.where("title").regex(request.getSearchTerm(),"i"))
		      .with(Sort.by(Sort.Direction.DESC, "createdOn").and(Sort.by(Sort.Direction.ASC, "resourceId")))
		      .skip(request.getPageIndex()).limit(request.getPageSize());
		List<HomeResource> resources = new ArrayList<>();
		try {
			resources = mongoTemplate.find(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return resources;
	}

	public Long searchHomeVideosCount(SearchRequest request) {
		if (request == null)
			return null;

		Query query = new Query().addCriteria(Criteria.where("title").regex(request.getSearchTerm(),"i"));
		Long count = 0L;
		try {
			return mongoTemplate.count(query, HomeResource.class);
		} catch (Exception e) {
			log.error("Exception while retrieveing resources " + ",error=" + e.getStackTrace());
		}
		return count;
	}

}
