package org.example.tpo_10.service;

import org.example.tpo_10.model.URL;
import org.example.tpo_10.model.URLDTO;
//import org.example.tpo_10.repository.URLRepository;
import org.springframework.stereotype.Service;

@Service
public class URLDTOMapper {

    public URLDTO map(URL url) {
        URLDTO urlDTO = new URLDTO();
        urlDTO.setTargetUrl(url.getTargetUrl());
        urlDTO.setRedirectUrl(url.getRedirectUrl());
        urlDTO.setVisits(url.getVisits());
        urlDTO.setPassword(url.getPassword());
        urlDTO.setName(url.getName());
        urlDTO.setId(url.getId());
        return urlDTO;
    }

    public URL map(URLDTO urlDTO) {
        URL url = new URL();
        url.setTargetUrl(urlDTO.getTargetUrl());
        url.setRedirectUrl(urlDTO.getRedirectUrl());
        url.setVisits(urlDTO.getVisits());
        url.setPassword(urlDTO.getPassword());
        url.setName(urlDTO.getName());
        url.setId(urlDTO.getId());
        return url;
    }
}
