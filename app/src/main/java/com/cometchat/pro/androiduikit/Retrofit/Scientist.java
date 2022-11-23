package com.cometchat.pro.androiduikit.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
* Let's Create our Scientist class to represent a single Scientist.
* It will implement java.io.Serializable interface, a marker interface that will allow
*  our
* class to support serialization and deserialization.
*/
public class Scientist implements Serializable {
   /**
    * Let' now come define instance fields for this class. We decorate them with
    * @SerializedName
    * attribute. Through this we are specifying the keys in our json data.
    */


   //sineset
   @SerializedName("id")
   private String mId;
   @SerializedName("name")
   private String name;
   @SerializedName("description")
   private String description;
   @SerializedName("galaxy")
   private String galaxy;
   @SerializedName("star")
   private String star;
   @SerializedName("dob")
   private String dob;
   @SerializedName("died")
   private String died;

    @SerializedName("group_name")
    private String group_name;

    @SerializedName("admin")
    private String admin;

    @SerializedName("email")
    private String email;

    @SerializedName("unique_id")
    private String unique_id;

    @SerializedName("position")
    private String position;

    @SerializedName("avgSpeedInKMH")
    private String avgSpeedInKMH;

    @SerializedName("date")
    private String date;







   /**
    * Let's now come define our getter and setter methods.
    */

   //for id
   public String getId() {
       return mId;
   }

   public void setId(String id) {
       mId = id;
   }
    //for name
    public String getPosition() {
        return position;
    }

    public void setPosition(String name) {
        this.name = position;
    }


   //for name
   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

    //for group_name
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.name = group_name;
    }

    //for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.name = date;
    }

    //for admin
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.name = admin;
    }

    //for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.name = email;
    }


    //for unique_id
    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.name = unique_id;
    }


    //for description
   public String getDescription() {
       return description;
   }

   public void setDescription(String description) {
       this.description = description;
   }

   //for star
   public String getStar() {
       return star;
   }

   public void setStar(String star) {
       this.star = star;
   }

   //for galaxy
   public String getGalaxy() {
       return galaxy;
   }

   public void setGalaxy(String galaxy) {
       this.galaxy = galaxy;
   }

   //for dob
   public String getDob() {
       return dob;
   }

   public void setDob(String dob) {
       this.dob = dob;
   }


   //for die
   public String getDied() {
       return died;
   }
   public void setDied(String died) {
       this.died = died;
   }

    //for die
    public String getAvgSpeedInKMH() {
        return avgSpeedInKMH;
    }
    public void setAvgSpeedInKMH(String avgSpeedInKMH) {
        this.avgSpeedInKMH = avgSpeedInKMH;
    }

   @Override
   public String toString() {
       return getName();
   }
}
//end

