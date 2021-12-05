package com.example.app_nativa.placeholder;


import java.util.UUID;


public class PlaceholderContent {


    public static class PlaceholderItem {
        public final String id;
        public final String title;
        public final String author;
        public final String description;
        public final String image;
        public final String country;
        public final String url;
        public final String language;
        public final String source;
        public final String category;
        public final String published_at;


        public PlaceholderItem(String title,String author,String description,String image,String country,String url, String language, String source,String category,String published_at) {
            this.id = UUID.randomUUID().toString();
            this.title = title;
            this.author = author;
            this.description =description ;
            this.image = image;
            this.country = country;
            this.url =url ;
            this.language = language;
            this.source= source;
            this.category= category;
            this.published_at=published_at;

        }


        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getPublished_at() {
            return published_at;
        }

        public String getSource() {
            return source;
        }

        public String getAuthor() {
            return author;
        }

        public String getCountry() {
            return country;
        }

        public String getCategory() {
            return category;
        }

        public String getLanguage() {
            return language;
        }

    }

}