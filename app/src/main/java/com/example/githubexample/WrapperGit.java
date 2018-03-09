package com.example.githubexample;

public class WrapperGit
{
    String title;
    String repo_url;
    String description;

    public WrapperGit(String title, String repo_url, String description) {
        this.title = title;
        this.repo_url = repo_url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
