package com.medicaps.bloodnate_bloodbank;

import androidx.annotation.Nullable;
// an enum in java
public enum BloodGroup {
    // different types of blood
    A_POSITIVE, B_POSITIVE, O_POSITIVE, AB_POSITIVE, A_NEGATIVE, B_NEGATIVE, O_NEGATIVE, AB_NEGATIVE;

    //static method to convert an enum type to String
    public static String BG(@Nullable BloodGroup bloodGroup){
        String bg = "null";
        if (bloodGroup != null) {
            switch (bloodGroup){
                case A_POSITIVE:bg="A+"; break;
                case B_POSITIVE:bg="B+"; break;
                case O_POSITIVE:bg="O+"; break;
                case AB_POSITIVE:bg="AB+";break;
                case A_NEGATIVE:bg="A-";break;
                case B_NEGATIVE:bg="B-";break;
                case O_NEGATIVE:bg="O-";break;
                case AB_NEGATIVE:bg="AB-";break;
            }
        }
        return bg;
    }
    // static method to convert a String to Enum type
    static @Nullable BloodGroup BG(String s){
        BloodGroup bloodGroup = BloodGroup.O_POSITIVE;
        switch (s){
            case"A+":bloodGroup = BloodGroup.A_POSITIVE;break;
            case"B+":bloodGroup = BloodGroup.B_POSITIVE;break;
            case"O+":bloodGroup =BloodGroup.O_POSITIVE;break;
            case"AB+":bloodGroup = BloodGroup.AB_POSITIVE;break;
            case"A-":bloodGroup = BloodGroup.A_NEGATIVE;break;
            case"B-":bloodGroup = BloodGroup.B_NEGATIVE;break;
            case"O-":bloodGroup =BloodGroup.O_NEGATIVE;break;
            case"AB-":bloodGroup = BloodGroup.AB_NEGATIVE;break;
            default:bloodGroup= null ;break;
        }return bloodGroup;
    }
}
