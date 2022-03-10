package com.protectionDogs.protection.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.protectionDogs.protection.Request.AddDogRequest;
import com.protectionDogs.protection.Request.CategoryRequest;
import com.protectionDogs.protection.Request.CategoryVideosRequest;
import com.protectionDogs.protection.Request.ChangePasswordRequest;
import com.protectionDogs.protection.Request.CreateSubscriptionRequest;
import com.protectionDogs.protection.Request.ForgotPasswordRequest;
import com.protectionDogs.protection.Request.GetDogRequest;
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
import com.protectionDogs.protection.Util.URIConstants;
import com.protectionDogs.protection.serviceImpl.ServiceLayer;

@RequestMapping("/petsProtection")
@RestController
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private ServiceLayer service;
	
	@RequestMapping(value = URIConstants.SIGNUP, method = RequestMethod.POST)
	public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.signup(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.VERIFYOTP, method = RequestMethod.POST)
	public ResponseEntity<String> verifyOtp(@RequestBody @Valid VerifyOtpRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.verifyOtp(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.LOGIN, method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.login(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.FORGOT, method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.forgotPassword(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.CHANGEPASSWORD, method = RequestMethod.POST)
	public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.changePassword(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.GETINFO, method = RequestMethod.POST)
	public ResponseEntity<String> getInfo(@RequestBody @Valid ChangePasswordRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.changePassword(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.GETHOME, method = RequestMethod.POST)
	public ResponseEntity<String> getHome(@RequestBody @Valid HomeRequest request,
			@RequestHeader Map<String, String> headers) {
		String response = service.getHomeRequest(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(value = URIConstants.GETSUBSCRIPTIONPLANS, method = RequestMethod.GET)
	public ResponseEntity<String> getSubscriptionPlans(@RequestHeader Map<String, String> headers) {
		String response = service.getSubscriptionPlans( headers);
		log.info("headers=" + headers.get("emailid") + ",response=" + response);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.ADDDOG, method = RequestMethod.POST)
	public ResponseEntity<String> addDog(@RequestBody @Valid AddDogRequest request, @RequestHeader Map<String, String> headers) {
		String response = service.addDog(request, headers);
		log.info("headers=" + headers.get("emailid") + ",response=" + response);
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(value = URIConstants.GETDOGS, method = RequestMethod.POST)
	public ResponseEntity<String> getDogs(@RequestBody @Valid GetDogRequest request, @RequestHeader Map<String, String> headers) {
		String response = service.getDogs(request, headers);
		log.info("headers=" + headers.get("emailid") + ",response=" + response);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.UPDATEUSER, method = RequestMethod.POST)
	public ResponseEntity<String> updateUser(@RequestBody @Valid ProfileUpdateRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.updateUser(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.REQUESTSUB, method = RequestMethod.POST)
	public ResponseEntity<String> createSubscription(@RequestBody @Valid CreateSubscriptionRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.createSubscription(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.WEBHOOK, method = RequestMethod.POST)
	public ResponseEntity<String> updateSubscription(@RequestBody @Valid String request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.updateSubscription(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.PROVIDESUB, method = RequestMethod.POST)
	public ResponseEntity<String> provideSubscription(@RequestBody @Valid ProvideSubscriptionRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.provideSubscription(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.UNSUB, method = RequestMethod.GET)
	public ResponseEntity<String> unSubscription(@RequestHeader Map<String, String> headers) {
		String response = service.unsubscribe(headers);
		log.info("response=" + response+",headers=" + headers.get("emailid") );
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.CALLBACK, method = RequestMethod.GET)
	public ResponseEntity<String> callback(@RequestParam("client_secret") String secret, @RequestParam("source_redirect_slug") String redirectSlug, @RequestHeader Map<String, String> headers) {
		//String response = service.getDogs(request, headers);
		log.info("headers=" + headers.get("emailid") + ",secret="+secret+",redirectSlug="+redirectSlug);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = URIConstants.SUPPORT, method = RequestMethod.POST)
	public ResponseEntity<String> createSupport(@RequestBody @Valid SupportRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.supportText(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.FILEUPLOAD, method = RequestMethod.POST)
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file,
	      @PathVariable(value = "entityId") String entityId, @PathVariable(value = "entityType") String entityType,
	      @RequestHeader Map<String, String> headers) {
		String response = service.uploadFile(file, headers, entityType, entityId);
		log.info("upload file request-" + entityId + ",response-" + response);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.SAVEVIDEOS, method = RequestMethod.POST)
	public ResponseEntity<String> saveVideos(@RequestBody @Valid SaveVideoRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.saveVideo(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.SAVEDVIDEOS, method = RequestMethod.POST)
	public ResponseEntity<String> savedVideos(@RequestBody @Valid SavedVideosRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.savedVideos(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.CATEGORYACTION, method = RequestMethod.POST)
	public ResponseEntity<String> addCategories(@RequestBody @Valid CategoryRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.categoryAction(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.GETCATEGORIES, method = RequestMethod.GET)
	public ResponseEntity<String> getCategories(@RequestHeader Map<String, String> headers) {
		String response = service.getCategories(headers);
		log.info("response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.GETCATEGORYVIDEOS, method = RequestMethod.POST)
	public ResponseEntity<String> getCategoriesVideos(@RequestBody @Valid CategoryVideosRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.getCategoryVideos(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = URIConstants.SEARCHVIDEOS, method = RequestMethod.POST)
	public ResponseEntity<String> getSearchVideos(@RequestBody @Valid SearchRequest request,
	      @RequestHeader Map<String, String> headers) {
		String response = service.getSearchVideos(request, headers);
		log.info("request=" + request + ",response=" + response+",headers=" + headers.get("emailid"));
		return ResponseEntity.ok(response);
	}
	
	
	
	
}
