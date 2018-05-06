package com.nhommot.doctruyen.utils;

import com.google.firebase.auth.FirebaseAuth;

public class AuthorUtils {

    public static String getCurrentUserId(){
       FirebaseAuth auth = FirebaseAuth.getInstance();
       if(auth.getCurrentUser() == null){
           return "";
       }
       return auth.getCurrentUser().getUid();
    }
}
