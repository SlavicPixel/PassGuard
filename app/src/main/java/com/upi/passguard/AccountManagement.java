package com.upi.passguard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountManagement {
    private String username;
    private String password;
    Map<String, String> accountsDB = new HashMap<>();


    public AccountManagement(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Boolean createNewAccount(String confirmPassword){
        if (!Objects.equals(password, confirmPassword)) return false;
        accountsDB.put(username, password);
        return true;
    }

    public Boolean loginAccount(){
        if(containsEntry(accountsDB, username, password)) return true;
        return false;
    }

    public static <K, V> boolean containsEntry(Map<K, V> map, K key, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
