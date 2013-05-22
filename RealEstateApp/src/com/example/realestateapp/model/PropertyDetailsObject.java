package com.example.realestateapp.model;

public class PropertyDetailsObject {

	private int mPropertyID;
	private String mPropertyTitle;
	private String mPropertyAddress;
	private String mPropertyPrice;
	private String mPropertyDescription;
	private String[] mPropertyImagesURL;
	private String[] mPropertyRoomCount;
	private String mPropertyUploaderMail;
	
	public PropertyDetailsObject() {
		mPropertyID = -1;
		mPropertyTitle = "3BHK and 2 Bath - Individual row house";
		mPropertyAddress = "210, South Blvd, Amin Drive, Dallas, Texas - 67785";
		mPropertyPrice = "400,000";
		mPropertyDescription = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		mPropertyImagesURL = new String[] {"http://findghar.com/blog/wp-content/uploads/2011/10/bangalore_apartments-houses-in-bangalore-flats-in-bangalore.jpg", 
				"http://cachefly.apartmentsapart.com/images/apartment_940_xbig.jpg"};
		mPropertyRoomCount = null;
		mPropertyUploaderMail = "dummy@dummydomain.com";
	}

	/**
	 * @param mPropertyID
	 * @param mPropertyTitle
	 * @param mPropertyAddress
	 * @param mPropertyPrice
	 * @param mPropertyDescription
	 * @param mPropertyImagesURL
	 * @param mPropertyRoomCount
	 * @param mPropertyUploaderMail
	 */
	public PropertyDetailsObject(int mPropertyID, String mPropertyTitle,
			String mPropertyAddress, String mPropertyPrice,
			String mPropertyDescription, String[] mPropertyImagesURL,
			String[] mPropertyRoomCount, String mPropertyUploaderMail) {
		super();
		this.mPropertyID = mPropertyID;
		this.mPropertyTitle = mPropertyTitle;
		this.mPropertyAddress = mPropertyAddress;
		this.mPropertyPrice = mPropertyPrice;
		this.mPropertyDescription = mPropertyDescription;
		this.mPropertyImagesURL = mPropertyImagesURL;
		this.mPropertyRoomCount = mPropertyRoomCount;
		this.mPropertyUploaderMail = mPropertyUploaderMail;
	}

	/**
	 * @return the mPropertyID
	 */
	public int getPropertyID() {
		return mPropertyID;
	}

	/**
	 * @return the mPropertyTitle
	 */
	public String getPropertyTitle() {
		return mPropertyTitle;
	}

	/**
	 * @return the mPropertyAddress
	 */
	public String getPropertyAddress() {
		return mPropertyAddress;
	}

	/**
	 * @return the mPropertyPrice
	 */
	public String getPropertyPrice() {
		return mPropertyPrice;
	}

	/**
	 * @return the mPropertyDescription
	 */
	public String getPropertyDescription() {
		return mPropertyDescription;
	}

	/**
	 * @return the mPropertyImagesURL
	 */
	public String[] getPropertyImagesURL() {
		return mPropertyImagesURL;
	}

	/**
	 * @return the mPropertyRoomCount
	 */
	public String[] getPropertyRoomCount() {
		return mPropertyRoomCount;
	}

	/**
	 * @return the mPropertyUploaderMail
	 */
	public String getPropertyUploaderMail() {
		return mPropertyUploaderMail;
	}
}
