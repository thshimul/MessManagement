package com.nub.db;

import java.util.Date;

public class DateManage {
    public static void main(String[] args) {
        String [] mnth = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        Date date =  new Date();
        
        System.out.println(mnth[date.getMonth()]+"-"+(1900+date.getYear()));
    }
}
