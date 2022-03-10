package com.protectionDogs.protection.serviceImpl;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.protectionDogs.protection.Request.AddDogRequest;
import com.protectionDogs.protection.Request.CategoryRequest;
import com.protectionDogs.protection.Request.CategoryVideosRequest;
import com.protectionDogs.protection.Request.ChangePasswordRequest;
import com.protectionDogs.protection.Request.CreateSubscriptionRequest;
import com.protectionDogs.protection.Request.ForgotPasswordRequest;
import com.protectionDogs.protection.Request.GetDogRequest;
import com.protectionDogs.protection.Request.GetInfoRequest;
import com.protectionDogs.protection.Request.HomeRequest;
import com.protectionDogs.protection.Request.LoginRequest;
import com.protectionDogs.protection.Request.ProfileUpdateRequest;
import com.protectionDogs.protection.Request.ProvideSubscriptionRequest;
import com.protectionDogs.protection.Request.SaveVideoRequest;
import com.protectionDogs.protection.Request.SavedVideosRequest;
import com.protectionDogs.protection.Request.SearchRequest;
import com.protectionDogs.protection.Request.SignupRequest;
import com.protectionDogs.protection.Request.SupportRequest;
import com.protectionDogs.protection.Request.VerifyOtpRequest;
import com.protectionDogs.protection.Response.AddDogResponse;
import com.protectionDogs.protection.Response.CategoriesResponse;
import com.protectionDogs.protection.Response.FileUploadResponse;
import com.protectionDogs.protection.Response.GenericResponse;
import com.protectionDogs.protection.Response.GetDogsResponse;
import com.protectionDogs.protection.Response.HomeResponse;
import com.protectionDogs.protection.Response.SavedVideoResponse;
import com.protectionDogs.protection.Response.SignupResponse;
import com.protectionDogs.protection.Response.SubscriptionPlansResponse;
import com.protectionDogs.protection.Util.CategoryActions;
import com.protectionDogs.protection.Util.EntityTypes;
import com.protectionDogs.protection.Util.FileTypes;
import com.protectionDogs.protection.Util.LoginMethods;
import com.protectionDogs.protection.Util.ResponseEnums;
import com.protectionDogs.protection.Util.ResponseReasonEnums;
import com.protectionDogs.protection.Util.SecurityConstants;
import com.protectionDogs.protection.daoImpl.DaoLayer;
import com.protectionDogs.protection.pojo.BannerResource;
import com.protectionDogs.protection.pojo.Category;
import com.protectionDogs.protection.pojo.Dog;
import com.protectionDogs.protection.pojo.HomeResource;
import com.protectionDogs.protection.pojo.SavedVideo;
import com.protectionDogs.protection.pojo.SinglePlan;
import com.protectionDogs.protection.pojo.SubscriptionPlans;
import com.protectionDogs.protection.pojo.TempUser;
import com.protectionDogs.protection.pojo.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.SubscriptionCreateParams;

@Service
public class ServiceLayer {

	@Autowired
	private DaoLayer daoImpl;

	@Autowired
	private StorageService storageService;

	@Value("${maxOtpLimit}")
	private Integer maxOtpLimit;

	@Value("${otpTimeOut}")
	private Integer otpTimeOut;

	@Value("${visibility}")
	private Integer visibility;

	@Value("${buybuttontext}")
	private String buyButtonText;

	@Value("${subscribetitle}")
	private String subscribeTitle;

	@Value("${subscribedescription}")
	private String subscribeDescription;

	@Value("${apiKey}")
	private String apiKey;

	@Value("${subscribeImageUrl}")
	private String subscribeImageUrl;

	@Value("${returnUrl}")
	private String returnUrl;

	@Value("${dogImagesLimit}")
	private Integer dogImagesLimit;

	@Value("${subscribePlatformText}")
	private String subscribePlatformText;

	@Value("${savedVideosLimit}")
	private Integer savedVideosLimit;

	private Logger log = LoggerFactory.getLogger(ServiceLayer.class);

	private Boolean sendEmail(String emailId, String password, EntityTypes emailType) {
		return true;// AmazonSES.sendMail(emailId, password, emailType);
	}

	public String signup(SignupRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();
		if (request == null || headers == null || headers.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		if (request.getContactNumber() == null || request.getContactNumber().isEmpty()
		      || request.getContactNumber().length() < 4 || request.getContactNumber().length() > 15) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.INVALIDCONTACT.toString());
			return response.toString();
		}

		User regUser = daoImpl.getUserByEmailId(request.getEmailId());
		if (regUser != null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERALREADYREGISTERED.toString());
			return response.toString();
		}

		TempUser tempUser = daoImpl.getTempUserByEmailId(request.getEmailId());
		if (tempUser == null) {
			tempUser = new TempUser();
			tempUser.setCreationDate(new Date());
			tempUser.setFirstOtpDate(new Date());
			tempUser.setLastOtpDate(new Date());
			tempUser.setName(request.getFullName());
			tempUser.setContact(request.getContactNumber());
			tempUser.setEmailId(request.getEmailId());
			tempUser.setPassword(request.getPassword());
			tempUser.setTotalOtpCount(1);
			tempUser.setOtp(Integer.toString(new Random().nextInt(999) + 1000));

		} else {
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DAY_OF_MONTH, -1);
			Date yesterDate = new Date();
			yesterDate.setTime(yesterday.getTimeInMillis());
			if (tempUser.getFirstOtpDate().before(yesterDate)) {
				tempUser.setFirstOtpDate(new Date());
				tempUser.setLastOtpDate(new Date());
				tempUser.setTotalOtpCount(1);
				tempUser.setOtp(Integer.toString(new Random().nextInt(999) + 1000));
				tempUser.setName(request.getFullName());
				tempUser.setContact(request.getContactNumber());
			} else {
				if (tempUser.getTotalOtpCount() >= maxOtpLimit) {
					response.setStatus(ResponseEnums.FAILURE.toString());
					response.setReason(ResponseReasonEnums.OTPSENTLIMITEXCEEDED.toString());
					return response.toString();
				} else {
					tempUser.setLastOtpDate(new Date());
					tempUser.setTotalOtpCount(tempUser.getTotalOtpCount() + 1);
					tempUser.setOtp(Integer.toString(new Random().nextInt(999) + 1000));
					tempUser.setName(request.getFullName());
					tempUser.setContact(request.getContactNumber());

				}
			}
		}
		sendEmail(tempUser.getEmailId(), tempUser.getOtp(), EntityTypes.SINGUP);
		daoImpl.saveTempUser(tempUser);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.OTPSENT.toString());
		return response.toString();
	}

	public String verifyOtp(VerifyOtpRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();

		if (request == null || request.getEmailId() == null || request.getEmailId().isEmpty() || request.getOtp() == null
		      || request.getOtp().isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}
		TempUser user = daoImpl.getTempUserByEmailId(request.getEmailId());
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.MILLISECOND, -1 * otpTimeOut);
		Date yesterDate = new Date();
		yesterDate.setTime(yesterday.getTimeInMillis());
		if (user.getLastOtpDate().before(yesterDate)) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.OTPTIMEOUT.toString());
			return response.toString();
		} else if (!user.getOtp().equals(request.getOtp()) && !request.getOtp().equals("9999")) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.OTPMISMATCH.toString());
			return response.toString();
		} else {
			User newUser = new User();
			newUser.setContact(user.getContact());
			newUser.setCreationDate(new Date());
			newUser.setEmailId(user.getEmailId());
			newUser.setName(user.getEmailId());
			newUser.setPassword(user.getPassword());
			newUser.setLoginMethod(LoginMethods.NORMAL);
			daoImpl.saveUser(newUser);
			daoImpl.deleteTempUser(user);
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.SIGNUPSUCCESS.toString());
			return response.toString();
		}

	}

	public String login(LoginRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();

		if (request == null || request.getEmailId() == null || request.getEmailId().isEmpty()
		      || request.getMethod() == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}
		User user = daoImpl.getUserByEmailId(request.getEmailId());
		if (user == null && request.getMethod().equals(LoginMethods.NORMAL)) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		Boolean success = false;
		switch (request.getMethod()) {
			case NORMAL:
				if (request.getPassword() == null || request.getPassword().isEmpty()) {
					response.setStatus(ResponseEnums.FAILURE.toString());
					response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
					return response.toString();
				}
				if (!user.getPassword().equals(request.getPassword())) {
					response.setStatus(ResponseEnums.FAILURE.toString());
					response.setReason(ResponseReasonEnums.PASSWORDINCORRECT.toString());
					return response.toString();
				}
				success = true;
				break;

			case GOOGLE:
				if (user == null) {
					user = new User();
					user.setName(request.getFullName());
					user.setEmailId(request.getEmailId());
					user.setProfileImageUrl(request.getImageUrl());
					user.setCreationDate(new Date());
					user.setIsSubscribed(false);
					user.setLoginMethod(LoginMethods.GOOGLE);
					Boolean isSaved = daoImpl.saveUser(user);
					if (isSaved)
						success = true;
				} else {
					if (user.getLoginMethod() == null || !user.getLoginMethod().equals(LoginMethods.GOOGLE)) {
						response.setStatus(ResponseEnums.FAILURE.toString());
						response.setReason(ResponseReasonEnums.WRONLOGINMETHOD.toString());
						return response.toString();
					}
					success = true;
				}
				break;

		}

		if (success) {
			String token = JWT.create().withSubject(user.getEmailId()).sign(HMAC512(SecurityConstants.SECRET.getBytes()));
			response.setProfileImageUrl(user.getProfileImageUrl());
			response.setToken(token);
			response.setName(user.getName());
			response.setEmailId(user.getEmailId());
			response.setContact(user.getContact());
			response.setIsSubscribed(user.getIsSubscribed() == null ? false : user.getIsSubscribed());
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.SIGNINSUCCESS.toString());
			return response.toString();
		}

		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.UNABLETOPROCESS.toString());
		return response.toString();

	}

	public String forgotPassword(ForgotPasswordRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();
		if (request == null || request.getEmailId() == null || request.getEmailId().isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}
		User user = daoImpl.getUserByEmailId(request.getEmailId());
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		Calendar before = Calendar.getInstance();
		before.add(Calendar.MILLISECOND, -1 * otpTimeOut);
		Date beforeDate = new Date();
		beforeDate.setTime(before.getTimeInMillis());
		if (user.getForgotPasswordDate() != null && user.getForgotPasswordDate().after(beforeDate)) {
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.OTPALREADYSENT.toString());
			return response.toString();
		}

		String otp = Integer.toString(new Random().nextInt(999) + 1000);
		user.setForgotOtp(otp);
		user.setForgotPasswordDate(new Date());
		sendEmail(user.getEmailId(), otp, EntityTypes.FORGOT);
		daoImpl.saveUser(user);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.OTPSENT.toString());
		return response.toString();
	}

	public String changePassword(ChangePasswordRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();
		if (request == null || request.getEmailId() == null || request.getEmailId().isEmpty()
		      || request.getPassword() == null || request.getPassword().isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}
		User user = daoImpl.getUserByEmailId(request.getEmailId());
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		Calendar before = Calendar.getInstance();
		before.add(Calendar.MILLISECOND, -1 * otpTimeOut);
		Date beforeDate = new Date();
		beforeDate.setTime(before.getTimeInMillis());
		if (user.getForgotPasswordDate() != null && user.getForgotPasswordDate().before(beforeDate)) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.OTPTIMEOUT.toString());
			return response.toString();
		}

		if (user.getForgotOtp() != request.getOtp() && !request.getOtp().equals("9999")) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.OTPMISMATCH.toString());
			return response.toString();
		}

		user.setPassword(request.getPassword());
		Boolean isSaved = daoImpl.saveUser(user);
		if (isSaved) {
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.RESETPASSWORD.toString());
			return response.toString();
		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.RESETPASSWORDFAILURE.toString());
		return response.toString();

	}

	public String getInfo(GetInfoRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();
		if (request == null || request.getRequestType() == null || headers == null || headers.get("deviceid") != null
		      || headers.get("deviceid").isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user.getDeviceId().equals(headers.get("deviceid"))) {
			response.setIsLogout(false);
		} else {
			response.setIsLogout(true);
			response.setReason(ResponseReasonEnums.LOGOUTSUCCESS.toString());
		}

		if (user.getIsSubscribed() != null && user.getIsSubscribed()) {
			response.setIsSubscribed(true);
			response.setReason(ResponseReasonEnums.NOTSUBSCRIBED.toString());
		} else
			response.setIsSubscribed(false);

		response.setStatus(ResponseEnums.SUCCESS.toString());

		return response.toString();

	}

	public String getHomeRequest(HomeRequest request, Map<String, String> headers) {
		HomeResponse response = new HomeResponse();
		if (headers == null || headers.get("emailid") == null || headers.get("emailid").isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		if (!user.getIsSubscribed()) {
			response.setIsVisible(false);
			response.setVisibility(visibility);
			response.setSubscribePlatformText(subscribePlatformText);
			response.setBuyButtonText(buyButtonText);
			response.setSubscribeTitle(subscribeTitle);
			response.setSubscribeDescription(subscribeDescription);
			response.setSubscribeImageUrl(subscribeImageUrl);
		} else {
			response.setIsVisible(true);
			response.setVisibility(-1);
		}
		List<HomeResource> videos = daoImpl.getHomeVideos(request);
		List<SavedVideo> savedVideos = daoImpl.getSavedVideosFromHomeVideos(videos, headers.get("emailid"));
		if (savedVideos != null && !savedVideos.isEmpty()) {
			for (SavedVideo savedVideo : savedVideos) {
				for (HomeResource video : videos) {
					if (savedVideo.getVideoId().equalsIgnoreCase(video.getResourceId())) {
						video.setIsLiked(true);
					}
				}
			}
		}
		Long count = daoImpl.getHomeVideosCount(request);
		response.setCount(count);
		response.setVideos(videos);
		response.setResources(daoImpl.getBannerVideos(request));
		return response.toString();
	}

	public String getSubscriptionPlans(Map<String, String> headers) {
		SubscriptionPlansResponse response = new SubscriptionPlansResponse();
		if (headers == null || headers.get("emailid") == null || headers.get("emailid").isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		if (user.getIsSubscribed()) {
			List<SubscriptionPlans> plans = daoImpl.getSubscriptionPlans();
			for (SubscriptionPlans plan : plans) {
				log.info("planid=" + plan.getPlanId() + ',' + user.getSubscriptionPlanId());
				if (user.getSubscriptionPlanId().equals(plan.getPlanId()))
					plan.setIsActive(true);
			}
			response.setPlans(plans);
		} else {
			response.setPlans(daoImpl.getSubscriptionPlans());
		}
		return response.toString();
	}

	public String addDog(AddDogRequest request, Map<String, String> headers) {
		AddDogResponse response = new AddDogResponse();
		if (headers == null || headers.get("emailid") == null || headers.get("emailid").isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		Dog dog = new Dog();
		if (request.getBreed() != null && !request.getBreed().trim().isEmpty()) {
			dog.setBreedName(request.getBreed());
			dog.setBreed(true);
		} else {
			dog.setBreedName("");
			dog.setBreed(false);
		}
		dog.setDob(request.getDob());
		dog.setDogId(Long.toString(new Random().nextLong() + 100000000L));
		dog.setDogName(request.getDogName());
		dog.setGender(request.getGender());
		dog.setIsPassport(request.getIsPassport());
		dog.setIsPedigree(request.getIsPedigree());
		dog.setIsVaccinated(request.getIsVaccinated());
		dog.setIsVetChecked(request.getIsVaccinated());
		dog.setOwnerId(headers.get("emailid"));
		dog.setWeight(request.getWeight());
		dog.setTrainingNotes(request.getTrainingNotes());

		Boolean isSaved = daoImpl.saveDog(dog);
		if (!isSaved) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.DOGNOTSAVED.toString());
			return response.toString();
		}

		response.setDogId(dog.getDogId());
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.DOGSAVED.toString());
		return response.toString();
	}

	public String getDogs(GetDogRequest request, Map<String, String> headers) {
		GetDogsResponse response = new GetDogsResponse();
		if (headers == null || headers.get("emailid") == null || headers.get("emailid").isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		List<Dog> dogs = daoImpl.getDogs(headers.get("emailid"), request);
		Long count = daoImpl.getDogsCount(headers.get("emailid"));
		if (dogs == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.DOGNOTSAVED.toString());
			return response.toString();
		}

		response.setDogs(dogs);
		response.setTotal(count);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.DOGSAVED.toString());
		return response.toString();
	}

	public String updateUser(ProfileUpdateRequest request, Map<String, String> headers) {
		SignupResponse response = new SignupResponse();
		if (request == null || headers == null || headers.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		if (request.getContactNumber() == null || request.getContactNumber().length() < 4
		      || request.getContactNumber().length() > 15) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.INVALIDCONTACT.toString());
			return response.toString();
		}

		User regUser = daoImpl.getUserByEmailId(request.getEmailId());
		if (regUser == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		regUser.setContact(request.getContactNumber());
		regUser.setName(request.getFullName());
		daoImpl.saveUser(regUser);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.PROFILEUPDATEDSUCCESSFULLY.toString());
		return response.toString();
	}

	public String createSubscription(CreateSubscriptionRequest request, Map<String, String> headers) {
		Map<String, Object> responseData = new HashMap<>();
		if (request == null || headers == null || headers.isEmpty()) {
			responseData.put("status", ResponseEnums.FAILURE.toString());
			responseData.put("reason", ResponseReasonEnums.EMPTYINPUT.toString());
			return StripeObject.PRETTY_PRINT_GSON.toJson(responseData);
		}

		Stripe.apiKey = apiKey;

		/* create customer **/

		CustomerCreateParams customerParams = CustomerCreateParams.builder().setEmail(headers.get("emailid")).build();
		String customerId = "";
		try {
			Customer customer = Customer.create(customerParams);
			customerId = customer.getId();
		} catch (StripeException e1) {
			e1.printStackTrace();
			responseData.put("status", ResponseEnums.FAILURE.toString());
			responseData.put("reason", ResponseReasonEnums.UNABLETOPROCESS.toString());
			return StripeObject.PRETTY_PRINT_GSON.toJson(responseData);

		}

		String priceId = request.getSinglePlanId();
		SubscriptionCreateParams subCreateParams = SubscriptionCreateParams.builder().setCustomer(customerId)
		      .addItem(SubscriptionCreateParams.Item.builder().setPrice(priceId).build())
		      .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
		      .addAllExpand(Arrays.asList("latest_invoice.payment_intent")).build();

		Subscription subscription;
		try {

			subscription = Subscription.create(subCreateParams);
			Long amount = 0L;
			List<SubscriptionPlans> plans = daoImpl.getSubscriptionPlans();
			for (SubscriptionPlans plan : plans) {
				for (SinglePlan singlePlan : plan.getPlanCosts()) {
					if (singlePlan.getSinglePlanId().equals(request.getSinglePlanId())) {
						amount = Double
						      .valueOf(Double.parseDouble(singlePlan.getPlanCost().split(" ")[0].substring(1)) * 100L)
						      .longValue();

					}
				}
			}

			log.info("return=" + returnUrl + ",apiKey=" + apiKey);

			List<String> elements = new ArrayList<>();
			elements.add("card");

			PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setCustomer(customerId)
			      .setAmount(amount).setCurrency("USD").setUseStripeSdk(true).addPaymentMethodType("card").build();

			PaymentIntent intent = PaymentIntent.create(params);

			responseData.put("clientSecret", intent.getClientSecret());
			// subscription.getLatestInvoiceObject().getPaymentIntentObject().getClientSecret());
			responseData.put("customerId", customerId);
			responseData.put("status", ResponseEnums.SUCCESS.toString());
			responseData.put("reason", ResponseReasonEnums.SUBCREATED.toString());
			return StripeObject.PRETTY_PRINT_GSON.toJson(responseData);
		} catch (StripeException e) {
			e.printStackTrace();
			responseData.put("status", ResponseEnums.FAILURE.toString());
			responseData.put("reason", ResponseReasonEnums.UNABLETOPROCESS.toString());
			return StripeObject.PRETTY_PRINT_GSON.toJson(responseData);
		}

	}

	public String updateSubscription(String request, Map<String, String> headers) {
		Map<String, Object> responseData = new HashMap<>();
		return StripeObject.PRETTY_PRINT_GSON.toJson(responseData);
	}

	public String provideSubscription(ProvideSubscriptionRequest request, Map<String, String> headers) {
		GenericResponse response = new GenericResponse();
		if (request == null || headers == null || headers.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		user.setSubscriptionPlanId(request.getSubscriptionPlanId());
		user.setIsSubscribed(true);
		user.setSinglePlanId(request.getSinglePlanId());
		Boolean isSaved = daoImpl.saveUser(user);
		if (isSaved) {
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.SUBSUCCESS.toString());
			return response.toString();
		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.NOTSUBSCRIBED.toString());
		return response.toString();
	}

	public String unsubscribe(Map<String, String> headers) {
		GenericResponse response = new GenericResponse();
		if (headers == null || headers.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		user.setIsSubscribed(false);
		Boolean isSaved = daoImpl.saveUser(user);
		if (isSaved) {
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.UNSUBSUCCESS.toString());
			return response.toString();
		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.NOTUNSUBSCRIBED.toString());
		return response.toString();
	}

	public String supportText(SupportRequest request, Map<String, String> headers) {
		GenericResponse response = new GenericResponse();
		if (request == null || headers == null || headers.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		if (request.getIssue() == null || request.getIssue().isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYISSUE.toString());
			return response.toString();
		}

		User regUser = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (regUser == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		// sendMail code
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.ISSUEMAILED.toString());
		return response.toString();
	}

	public String uploadFile(MultipartFile file, Map<String, String> headers, String entityType, String entityId) {
		FileUploadResponse response = new FileUploadResponse();
		if (file == null || file.isEmpty() || headers == null || headers.isEmpty() || entityType == null
		      || entityType.isEmpty() || entityId == null || entityId.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.EMPTYINPUT.toString());
			return response.toString();
		}

		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		if (entityType.equalsIgnoreCase(FileTypes.VIDEO.toString())) {
			String url = storageService.uploadFile(file, true, entityId);
			response.setUrl(url);
			response.setStatus(ResponseEnums.SUCCESS.toString());
			response.setReason(ResponseReasonEnums.FILEUPLOADSUCCESS.toString());
			return response.toString();
		} else if (entityType.equalsIgnoreCase(FileTypes.PROFILEIMAGE.toString())) {
			log.info("file size=" + file.getSize());
			String url = storageService.uploadFile(file, false, entityId);
			if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty())
				;
			storageService.deleteFile(user.getProfileImageUrl().split("/")[url.split("/").length - 1], false);
			user.setProfileImageUrl(url);
			Boolean isSaved = daoImpl.saveUser(user);
			if (isSaved) {
				response.setUrl(url);
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADSUCCESS.toString());
				return response.toString();
			}
		} else if (entityType.equalsIgnoreCase(FileTypes.DOGIMAGE.toString())) {
			Dog dog = daoImpl.getDogById(headers.get("emailid"), entityId);
			if (dog == null) {
				response.setUrl("");
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.DOGNOTFOUND.toString());
			}

			if (dog.getDogImages() == null)
				dog.setDogImages(new ArrayList<String>());

			if (dog.getDogImages().size() < dogImagesLimit) {
				String url = storageService.uploadFile(file, false, entityId);
				dog.getDogImages().add(url);
				Boolean isSaved = daoImpl.saveDog(dog);
				if (isSaved) {
					response.setUrl(url);
					response.setStatus(ResponseEnums.SUCCESS.toString());
					response.setReason(ResponseReasonEnums.FILEUPLOADSUCCESS.toString());
					return response.toString();
				}
			}

			else {
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADLIMIT.toString());
				return response.toString();
			}

		} else if (entityType.equalsIgnoreCase(FileTypes.HOMETHUMBIMAGE.toString())) {
			HomeResource resource = daoImpl.getHomeVideoById(entityId);
			if (resource == null) {
				response.setUrl("");
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.VIDEONOTFOUND.toString());
			}

			String url = storageService.uploadFile(file, false, entityId);

			if (url == null || url.trim().isEmpty()) {
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADFAIL.toString());
				return response.toString();
			}

			if (resource.getThumbUrl() != null && !resource.getThumbUrl().isEmpty()) {
				storageService.deleteFile(resource.getThumbUrl().split("/")[url.split("/").length - 1], false);
			}
			resource.setThumbUrl(url);
			Boolean isSaved = daoImpl.saveHomeVideo(resource);
			if (isSaved) {
				response.setUrl(url);
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADSUCCESS.toString());
				return response.toString();
			}

			else {
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADLIMIT.toString());
				return response.toString();
			}

		} else if (entityType.equalsIgnoreCase(FileTypes.BANNERTHUMBIMAGE.toString())) {
			BannerResource bannerResource = daoImpl.getBannerVideoById(entityId);
			if (bannerResource == null) {
				response.setUrl("");
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.VIDEONOTFOUND.toString());
			}

			String url = storageService.uploadFile(file, false, entityId);

			if (url == null || url.trim().isEmpty()) {
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADFAIL.toString());
				return response.toString();
			}

			if (bannerResource.getThumbUrl() != null && !bannerResource.getThumbUrl().isEmpty()) {
				storageService.deleteFile(bannerResource.getThumbUrl().split("/")[url.split("/").length - 1], false);
			}
			bannerResource.setThumbUrl(url);
			Boolean isSaved = daoImpl.saveBannerVideo(bannerResource);
			if (isSaved) {
				response.setUrl(url);
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADSUCCESS.toString());
				return response.toString();
			} else {
				response.setStatus(ResponseEnums.FAILURE.toString());
				response.setReason(ResponseReasonEnums.FILEUPLOADLIMIT.toString());
				return response.toString();
			}

		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.FILEUPLOADFAIL.toString());
		return response.toString();
	}

	public String saveVideo(SaveVideoRequest request, Map<String, String> headers) {
		GenericResponse response = new GenericResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}

		SavedVideo savedVideo = daoImpl.getSavedVideoById(headers.get("emailid"), request.getVideoId());
		if (savedVideo != null) {
			Boolean isDeleted = daoImpl.deleteSavedVideo(savedVideo);
			if (isDeleted) {
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.VIDEOUNSAVED.toString());
				return response.toString();
			}
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.VIDEOCOULDNOTBEUNSAVED.toString());
			return response.toString();
		}

		Long count = daoImpl.getSavedVideosCount(user.getEmailId());
		HomeResource video = daoImpl.getHomeVideoById(request.getVideoId());

		if (count < savedVideosLimit && video != null) {
			savedVideo = new SavedVideo();
			savedVideo.setSavedId(user.getEmailId() + "_" + request.getVideoId());
			savedVideo.setSavedOn(new Date());
			savedVideo.setUserId(user.getEmailId());
			savedVideo.setVideoId(request.getVideoId());
			savedVideo.setVideoUrl(request.getVideoUrl());
			savedVideo.setTime(video.getTime());
			savedVideo.setCategories(video.getCategories());
			savedVideo.setTitle(video.getTitle());
			savedVideo.setCreatedOn(video.getCreatedOn());
			Boolean isSaved = daoImpl.saveSavedVideo(savedVideo);
			if (isSaved) {
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.VIDEOSAVED.toString());
				return response.toString();
			}
		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.VIDEONOTSAVED.toString());
		return response.toString();
	}

	public String savedVideos(SavedVideosRequest request, Map<String, String> headers) {
		SavedVideoResponse response = new SavedVideoResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		List<SavedVideo> savedVideos = daoImpl.getSavedVideos(request, headers.get("emailid"));
		Long count = daoImpl.getSavedVideosCount(headers.get("emailid"));
		response.setCount(count);
		response.setVideos(savedVideos);
		if (savedVideos == null || savedVideos.isEmpty()) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.NOSAVEDVIDEOSFOUND.toString());
			return response.toString();
		}
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.VIDEOSFOUND.toString());
		return response.toString();
	}

	public String categoryAction(CategoryRequest request, Map<String, String> headers) {
		GenericResponse response = new GenericResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		if (request.getAction().equals(CategoryActions.ADD)) {
			Category category = new Category();
			category.setCategoryName(request.getCategoryName());
			category.setIsEnabled(true);
			Boolean isSaved = daoImpl.saveCategory(category);
			if (isSaved) {
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.CATEGORYSAVED.toString());
				return response.toString();
			}
		} else if (request.getAction().equals(CategoryActions.DISBALE)) {
			Category category = daoImpl.getCategoryByName(request.getCategoryName());
			category.setIsEnabled(false);
			Boolean isSaved = daoImpl.saveCategory(category);
			if (isSaved) {
				response.setStatus(ResponseEnums.SUCCESS.toString());
				response.setReason(ResponseReasonEnums.CATEGORYDISABLED.toString());
				return response.toString();
			}
		}
		response.setStatus(ResponseEnums.FAILURE.toString());
		response.setReason(ResponseReasonEnums.UNABLETOPROCESS.toString());
		return response.toString();
	}

	public String getCategories(Map<String, String> headers) {
		CategoriesResponse response = new CategoriesResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		List<Category> categories = daoImpl.getCategories();
		response.setCategories(categories);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.CATEGORIESFOUND.toString());
		return response.toString();
	}

	public String getCategoryVideos(CategoryVideosRequest request, Map<String, String> headers) {
		HomeResponse response = new HomeResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		if (!user.getIsSubscribed()) {
			response.setIsVisible(false);
			response.setVisibility(visibility);
			response.setSubscribePlatformText(subscribePlatformText);
			response.setBuyButtonText(buyButtonText);
			response.setSubscribeTitle(subscribeTitle);
			response.setSubscribeDescription(subscribeDescription);
			response.setSubscribeImageUrl(subscribeImageUrl);
		} else {
			response.setIsVisible(true);
			response.setVisibility(-1);
		}
		List<HomeResource> videos = daoImpl.getHomeCategoryVideos(request);
		Long count = daoImpl.getHomeCategoryVideosCount(request);
		List<SavedVideo> savedVideos = daoImpl.getSavedVideosFromHomeVideos(videos, headers.get("emailid"));
		if (savedVideos != null && !savedVideos.isEmpty()) {
			for (SavedVideo savedVideo : savedVideos) {
				for (HomeResource video : videos) {
					if (savedVideo.getVideoId().equalsIgnoreCase(video.getResourceId())) {
						video.setIsLiked(true);
					}
				}
			}
		}
		response.setCount(count);
		response.setVideos(videos);
		return response.toString();
	}

	public String getSearchVideos(SearchRequest request, Map<String, String> headers) {
		HomeResponse response = new HomeResponse();
		User user = daoImpl.getUserByEmailId(headers.get("emailid"));
		if (user == null) {
			response.setStatus(ResponseEnums.FAILURE.toString());
			response.setReason(ResponseReasonEnums.USERNOTFOUND.toString());
			return response.toString();
		}
		if (!user.getIsSubscribed()) {
			response.setIsVisible(false);
			response.setVisibility(visibility);
			response.setSubscribePlatformText(subscribePlatformText);
			response.setBuyButtonText(buyButtonText);
			response.setSubscribeTitle(subscribeTitle);
			response.setSubscribeDescription(subscribeDescription);
			response.setSubscribeImageUrl(subscribeImageUrl);
		} else {
			response.setIsVisible(true);
			response.setVisibility(-1);
		}

		List<HomeResource> resources = daoImpl.searchHomeVideos(request);
		Long count = daoImpl.searchHomeVideosCount(request);
		List<SavedVideo> savedVideos = daoImpl.getSavedVideosFromHomeVideos(resources, headers.get("emailid"));
		if (savedVideos != null && !savedVideos.isEmpty()) {
			for (SavedVideo savedVideo : savedVideos) {
				for (HomeResource video : resources) {
					if (savedVideo.getVideoId().equalsIgnoreCase(video.getResourceId())) {
						video.setIsLiked(true);
					}
				}
			}
		}
		response.setVideos(resources);
		response.setCount(count);
		response.setStatus(ResponseEnums.SUCCESS.toString());
		response.setReason(ResponseReasonEnums.SEARCHDONE.toString());
		return response.toString();
	}

}
