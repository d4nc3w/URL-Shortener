package org.example.tpo_10.service;

import jakarta.transaction.Transactional;
//import org.example.tpo_10.model.DeleteDTO;
import org.example.tpo_10.model.URL;
import org.example.tpo_10.model.URLDTO;
import org.example.tpo_10.repository.URLRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class URLService {

    private final URLDTOMapper urlDTOMapper;
    private final URLRepository urlRepository;

    public URLService(URLDTOMapper urlDTOMapper, URLRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.urlDTOMapper = urlDTOMapper;
    }

    @Transactional
    public URLDTO createURL(URLDTO urlDTO) {
        String id = createId();
        String shortURL = createRedirectURL(id);
        urlDTO.setRedirectUrl(shortURL);
        urlDTO.setId(id);
        if(urlDTO.getPassword() != null){
            if(urlDTO.getPassword().equals("string")){
                urlDTO.setPassword(null);
            }
        }
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
        //redirectUrl.append("http://localhost:8080/api/links/red/");
        redirectUrl.append(id);
        return redirectUrl.toString();
    }

    public Optional<URLDTO> getURL(String id){
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
}
