package fishtudo.sos;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Carlos on 12/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Establishment implements Parcelable{

    @JsonProperty(value = "nomeFantasia")
    private String name;

    @JsonProperty(value = "descricaoCompleta")
    private String description;

    @JsonProperty(value = "telefone")
    private String phoneNumber;

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "long")
    private double longitude;

    public String getDescription() {
        return description  == null ? "" :description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name == null ? "" :name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Establishment(){

    }

    public Establishment(String name){
        this.name = name;
    }

    public Establishment(String name, String phoneNumber, double latitude, double longitude){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Establishment(Parcel parcel){

//        1 - Name
        name = parcel.readString();

//        2 - Description
        description = parcel.readString();

//        3 - Phone number
        phoneNumber = parcel.readString();

//        4 - Latitude
        latitude = parcel.readDouble();

//        5 - Longitude
        longitude = parcel.readDouble();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

//        1 - Name
        dest.writeString(getName());

//        2 - Description
        dest.writeString(getDescription());

//        3 - Phone number
        dest.writeString(getPhoneNumber());

//        4 - Latitude
        dest.writeDouble(getLatitude());

//        5 - Longitude
        dest.writeDouble(getLongitude());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Establishment createFromParcel(Parcel in) {
            return new Establishment(in);
        }

        public Establishment[] newArray(int size) {
            return new Establishment[size];
        }
    };
}