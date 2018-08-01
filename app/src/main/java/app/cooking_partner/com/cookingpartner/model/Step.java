package app.cooking_partner.com.cookingpartner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

	public static final Creator<Step> CREATOR = new Creator<Step>() {
		@Override
		public Step createFromParcel(Parcel in) {
			return new Step(in);
		}

		@Override
		public Step[] newArray(int size) {
			return new Step[size];
		}
	};
	private int id;
	private String shortDescription, description;
	@SerializedName("videoURL")
	private String videoUrl;
	@SerializedName("thumbnailURL")
	private String thumbNailUrl;

	public Step(int id, String shortDescription, String description, String videoUrl, String thumbNailUrl) {
		this.id = id;
		this.shortDescription = shortDescription;
		this.description = description;
		this.videoUrl = videoUrl;
		this.thumbNailUrl = thumbNailUrl;
	}

	protected Step(Parcel in) {
		id = in.readInt();
		shortDescription = in.readString();
		description = in.readString();
		videoUrl = in.readString();
		thumbNailUrl = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeString(shortDescription);
		parcel.writeString(description);
		parcel.writeString(videoUrl);
		parcel.writeString(thumbNailUrl);
	}
}