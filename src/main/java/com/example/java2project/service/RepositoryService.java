package com.example.java2project.service;

import com.example.java2project.domain.*;
import com.example.java2project.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final CommitRepository commitRepository;
    private final DeveloperRepository developerRepository;
    private final IssueRepository issueRepository;
    private final ReleaseRepository releaseRepository;

    private final String commitUrl = "https://api.github.com/repos/OWNER/REPO/commits?per_page=100&page=";
    private final String developerUrl = "https://api.github.com/repos/OWNER/REPO/contributors?anon=1&per_page=100&page=";
    private final String issueUrl = "https://api.github.com/repos/OWNER/REPO/issues?state=all&per_page=100&page=";
    private final String releaseUrl = "https://api.github.com/repos/OWNER/REPO/releases?per_page=100&page=";
    private final String repoUrl = "https://api.github.com/repos/OWNER/REPO";

    @Autowired
    public RepositoryService(RepositoryRepository repositoryRepository, CommitRepository commitRepository, DeveloperRepository developerRepository, IssueRepository issueRepository, ReleaseRepository releaseRepository) {
        this.repositoryRepository = repositoryRepository;
        this.commitRepository = commitRepository;
        this.developerRepository = developerRepository;
        this.issueRepository = issueRepository;
        this.releaseRepository = releaseRepository;
    }

    // update the database
    public boolean updateData() {
        String repo1 = "json-iterator/java";
        String repo2 = "tensorflow/java";
        return saveRepo(repo1) && saveRepo(repo2);
    }

    public List<Repository> getRepo() {
        return repositoryRepository.findAll();
    }

    // save all information of repo1
    public boolean saveRepo(String repo1) {
        // get the information of repo1
        StringBuilder msg = getMessage(repoUrl.replace("OWNER/REPO", repo1));
        if (msg.isEmpty()) {
            return false;
        }
        Repository repository1 = new Repository();
        String json = msg.toString();
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(json);
            String name = obj.get("name").toString();
            String full_name = obj.get("full_name").toString();
            Long repo_id = (Long) obj.get("id");
            String url = obj.get("html_url").toString();
            JSONObject ownerObj = (JSONObject) obj.get("owner");
            String owner = ownerObj.get("login").toString();
            Long owner_id = (Long) ownerObj.get("id");
            String owner_url = ownerObj.get("html_url").toString();
            repository1.setName(name);
            repository1.setFull_name(full_name);
            repository1.setOwner(owner);
            repository1.setOwner_id(owner_id);
            repository1.setOwner_url(owner_url);
            repository1.setRepo_id(repo_id);
            repository1.setUrl(url);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        repositoryRepository.save(repository1);

        // get the information of commit of repo1
        int page = 1;
        msg = getMessage(commitUrl.replace("OWNER/REPO", repo1) + page);
        if (msg.isEmpty()) {
            return false;
        }
        while (!msg.isEmpty()) {
            json = msg.toString();
            JSONArray array;
            try {
                array = (JSONArray) parser.parse(json);
                if (array.isEmpty()) {
                    break;
                }
                for (Object o : array) {
                    JSONObject jsonObject = (JSONObject) o;
                    Commit commit = new Commit();
                    String sha = jsonObject.get("sha").toString();
                    String date = ((JSONObject) ((JSONObject) jsonObject.get("commit")).get("author")).get("date").toString();
                    commit.setRepository(repository1);
                    commit.setDate(date);
                    commit.setSha(sha);
                    commitRepository.save(commit);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            page++;
            msg = getMessage(commitUrl.replace("OWNER/REPO", repo1) + page);
        }

        // get the information of developer of repo1
        page = 1;
        msg = getMessage(developerUrl.replace("OWNER/REPO", repo1) + page);
        if (msg.isEmpty()) {
            return false;
        }
        while (!msg.isEmpty()) {
            json = msg.toString();
            JSONArray array;
            try {
                array = (JSONArray) parser.parse(json);
                if (array.isEmpty()) {
                    break;
                }
                for (Object o : array) {
                    JSONObject jsonObject = (JSONObject) o;
                    Developer developer = new Developer();
                    String type = jsonObject.get("type").toString();
                    Long contributions = (Long) jsonObject.get("contributions");
                    developer.setContributions(contributions);
                    developer.setType(type);
                    if (type.equals("User")) {
                        String name = jsonObject.get("login").toString();
                        Long user_id = (Long) jsonObject.get("id");
                        String url = jsonObject.get("html_url").toString();
                        developer.setName(name);
                        developer.setUser_id(user_id);
                        developer.setUrl(url);
                    }
                    if (type.equals("Anonymous")) {
                        String name = jsonObject.get("name").toString();
                        String email = jsonObject.get("email").toString();
                        developer.setName(name);
                        developer.setEmail(email);
                    }
                    developer.setRepository(repository1);
                    developerRepository.save(developer);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            page++;
            msg = getMessage(developerUrl.replace("OWNER/REPO", repo1) + page);
        }

        // get the information of issue of repo1
        page = 1;
        msg = getMessage(issueUrl.replace("OWNER/REPO", repo1) + page);
        if (msg.isEmpty()) {
            return false;
        }
        while (!msg.isEmpty()) {
            json = msg.toString();
            JSONArray array;
            try {
                array = (JSONArray) parser.parse(json);
                if (array.isEmpty()) {
                    break;
                }
                for (Object o : array) {
                    JSONObject jsonObject = (JSONObject) o;
                    Issue issue = new Issue();
                    Long issue_id = (Long) jsonObject.get("id");
                    String title = jsonObject.get("title").toString();
                    String state = jsonObject.get("state").toString();
                    if (jsonObject.get("body") != null) {
                        String body = jsonObject.get("body").toString();
                        if (body.length() >= 20000) {
                            body = body.substring(0, 19999);
                        }
                        issue.setBody(body);
                    }
                    String create_date = jsonObject.get("created_at").toString();
                    issue.setIssue_id(issue_id);
                    issue.setTitle(title);
                    issue.setState(state);
                    issue.setCreate_date(create_date);
                    if (state.equals("closed")) {
                        String close_date = jsonObject.get("closed_at").toString();
                        issue.setClose_date(close_date);
                    }
                    issue.setRepository(repository1);
                    issueRepository.save(issue);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            page++;
            msg = getMessage(issueUrl.replace("OWNER/REPO", repo1) + page);
        }

        // get the information of release of repo1
        page = 1;
        msg = getMessage(releaseUrl.replace("OWNER/REPO", repo1) + page);
        if (msg.isEmpty()) {
            return false;
        }
        while (!msg.isEmpty()) {
            json = msg.toString();
            JSONArray array;
            try {
                array = (JSONArray) parser.parse(json);
                if (array.isEmpty()) {
                    break;
                }
                for (Object o : array) {
                    JSONObject jsonObject = (JSONObject) o;
                    Release release = new Release();
                    Long release_id = (Long) jsonObject.get("id");
                    String published_date = jsonObject.get("published_at").toString();
                    release.setRelease_id(release_id);
                    release.setPublish_date(published_date);
                    release.setRepository(repository1);
                    releaseRepository.save(release);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            page++;
            msg = getMessage(releaseUrl.replace("OWNER/REPO", repo1) + page);
        }


        return true;
    }

    // connect to url and get the message
    public StringBuilder getMessage(String s) {
        URL url;
        StringBuilder msg = new StringBuilder();
        try {
            url = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "token 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");
            conn.connect();

            int code = conn.getResponseCode();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    msg.append(line).append("\n");
                }
                reader.close();
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }


}
