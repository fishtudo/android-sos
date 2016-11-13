package fishtudo.sos;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import api.util.StringUtil;

/**
 * Created by Carlos on 12/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Establishment implements Parcelable{

    @JsonProperty(value = "nomeFantasia")
    private String name;

    @JsonProperty(value = "descricaoCompleta")
    private String description;

    @JsonProperty(value = "esferaAdministrativa")
    private String administrativeSphere;

    private boolean emergency;

    private boolean ambulatory;

    private boolean sus;

    private boolean surgery;

    private boolean obstetrics;

    private boolean neonatal;

    private boolean dialysis;

    @JsonProperty(value = "telefone")
    private String phoneNumber;

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "long")
    private double longitude;

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

//        6 - Emergency
        emergency = parcel.readByte() != 0;

//        7 - Ambulatory
        ambulatory = parcel.readByte() != 0;

//        8 - Sus
        sus = parcel.readByte() != 0;

//        9 - Surgery
        surgery = parcel.readByte() != 0;

//        10 - Obstetrics
        obstetrics = parcel.readByte() != 0;

//        11 - Neonatal
        neonatal = parcel.readByte() != 0;

//        12 - Dialysis
        dialysis = parcel.readByte() != 0;
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

//        6 - Emergency
        dest.writeByte((byte) (emergency ? 1 : 0));

//        7 - Ambulatory
        dest.writeByte((byte) (ambulatory ? 1 : 0));

//        8 - Sus
        dest.writeByte((byte) (sus ? 1 : 0));

//        9 - Surgery
        dest.writeByte((byte) (surgery ? 1 : 0));

//        10 - Obstetrics
        dest.writeByte((byte) (obstetrics ? 1 : 0));

//        11 - Neonatal
        dest.writeByte((byte) (neonatal ? 1 : 0));

//        12 - Dialysis
        dest.writeByte((byte) (dialysis ? 1 : 0));

    }

    @JsonProperty(value = "temAtendаimentoUrgencia")
    public void setEmergency(String emergency) {
        this.emergency = StringUtil.convertPortugueseYesOrNoToBoolean(emergency);
    }

    @JsonProperty(value = "temAtendimentoAmbulatorial")
    public void setAmbulatory(String ambulatory) {
        this.ambulatory = StringUtil.convertPortugueseYesOrNoToBoolean(ambulatory);
    }

    @JsonProperty(value = "temCentroCirurgico")
    public void setSurgery(String surgery) {
        this.surgery = StringUtil.convertPortugueseYesOrNoToBoolean(surgery);
    }

    @JsonProperty(value = "temNeoNatal")
    public void setNeonatal(String neonatal) {
        this.neonatal = StringUtil.convertPortugueseYesOrNoToBoolean(neonatal);
    }

    @JsonProperty(value = "temDialise")
    public void setDialysis(String dialysis) {
        this.dialysis = StringUtil.convertPortugueseYesOrNoToBoolean(dialysis);
    }
    @JsonProperty(value = "vinculoSus")
    public void setSus(String sus) {
        this.sus = StringUtil.convertPortugueseYesOrNoToBoolean(sus);
    }

    @JsonProperty(value = "temObstetra")
    public void setObstetrics(String obstetrics) {
        this.obstetrics = StringUtil.convertPortugueseYesOrNoToBoolean(obstetrics);
    }

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

    public String getAdministrativeSphere() {
        return administrativeSphere;
    }

    public boolean hasAmbulatory() {
        return ambulatory;
    }

    public boolean hasEmergency() {
        return emergency;
    }

    public boolean hasNeonatal() {
        return neonatal;
    }

    public boolean hasObstetrics() {
        return obstetrics;
    }

    public boolean hasSurgery() {
        return surgery;
    }

    public boolean hasSus() {
        return sus;
    }

    public boolean hasDialysis() { return dialysis; }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Establishment createFromParcel(Parcel in) {
            return new Establishment(in);
        }

        public Establishment[] newArray(int size) {
            return new Establishment[size];
        }
    };
}