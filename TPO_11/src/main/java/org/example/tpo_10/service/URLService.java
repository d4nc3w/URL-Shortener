package org.example.tpo_10.service;

import jakarta.transaction.Transactional;
import org.example.tpo_10.model.URL;
import org.example.tpo_10.model.URLDTO;
import org.example.tpo_10.repository.URLRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class URLService {

    private final URLDTOMapper urlDTOMapper;
    private final URLRepository urlRepository;
    private static List<String> allUrls;

    public URLService(URLDTOMapper urlDTOMapper, URLRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.urlDTOMapper = urlDTOMapper;
        getURLsFromDB();
    }

    @Transactional
    public URLDTO createURL(URLDTO urlDTO) {
        String id = createId();
        String shortURL = createRedirectURL(id);
        urlDTO.setRedirectUrl(shortURL);
        urlDTO.setId(id);
        if(urlDTO.getPassword() != null && urlDTO.getPassword().equals("string")){
            urlDTO.setPassword(null);
        }
        getURLsFromDB();
        URL url = urlDTOMapper.map(urlDTO);
        urlRepository.save(url);
        return urlDTOMapper.map(url);
    }

    private String createId(){
        String[] arr = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ".split("");
        StringBuilder redirectUrl = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            redirectUrl.append(arr[(int) (Math.random() * arr.length)]);
        }
        return redirectUrl.toString();
    }

    private String createRedirectURL(String id) {
        StringBuilder redirectUrl = new StringBuilder();
        redirectUrl.append("http://localhost:8080/red/");
        redirectUrl.append(id);
        return redirectUrl.toString();
    }

    public Optional<URLDTO> getURL(String id){
        getURLsFromDbEdit(id);
        return urlRepository.findById(id)
                .map(urlDTOMapper::map);
    }

    @Transactional
    public Optional<URL> updateURL(URLDTO urlDTO){
        return Optional.of(urlRepository.save(urlDTOMapper.map(urlDTO)));
    }

    public boolean checkPassword(String password, String passwordToCheck){
        if(password != null) {
            return password.equals(passwordToCheck);
        } else {
            return true;
        }
    }

    @Transactional
    public void deleteURL(String id){
        urlRepository.deleteById(id);
    }

    public void updateVisits(String id){
        URL url = urlRepository.findById(id).orElseThrow();
        int visits = url.getVisits();
        url.setVisits(visits + 1);
        urlDTOMapper.map(url);
        urlRepository.save(url);
    }

    public void saveURL(URLDTO url){
        urlRepository.save(urlDTOMapper.map(url));
    }

    public void getURLsFromDB() {
        List<String> targetURLs = new ArrayList<>();
        List<URL> urls = (List<URL>) urlRepository.findAll();
        for (URL url : urls) {
            targetURLs.add(url.getTargetUrl());
        }
        setAllUrls(targetURLs);
    }

    public void getURLsFromDbEdit(String excludedID){
        List<String> targetURLs = new ArrayList<>();
        List<URL> urls = (List<URL>) urlRepository.findAll();
        for (URL url : urls) {
            if(!url.getId().equals(excludedID)){
                targetURLs.add(url.getTargetUrl());
            }
        }
        setAllUrls(targetURLs);

    }

    public static List<String> getAllUrls() {
        return allUrls;
    }

    public static void setAllUrls(List<String> allUrls) {
        URLService.allUrls = allUrls;
    }
}
