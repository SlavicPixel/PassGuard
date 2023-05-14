package com.upi.passguard;

public class VaultModel {
    private int id;
    private String Title;
    private String Username;
    private String Password;
    private String Url;
    private String Notes;

    public VaultModel(int id, String title, String username, String password, String url, String notes) {
        this.id = id;
        Title = title;
        Username = username;
        Password = password;
        Url = url;
        Notes = notes;
    }

    public VaultModel(){
    }

    @Override
    public String toString() {
        return "com.upi.passguard.VaultModel{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Url='" + Url + '\'' +
                ", Notes='" + Notes + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

}
