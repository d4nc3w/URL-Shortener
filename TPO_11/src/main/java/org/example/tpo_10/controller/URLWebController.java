package org.example.tpo_10.controller;

import jakarta.validation.Valid;
import org.example.tpo_10.model.URLDTO;
import org.example.tpo_10.service.URLService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Optional;

@Controller
public class URLWebController {
    private final URLService urlService;

    public URLWebController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String showForm(@RequestParam(value = "lang", required = false) String lang, Model model) {
        Locale locale;
        if(lang != null && lang.equals("pl")){
            locale = new Locale("pl", "PL");
            System.out.println("Chosen: PL");
            model.addAttribute("lang", "pl");
        } else if (lang != null && lang.equals("en")){
            locale = new Locale("en", "US");
            System.out.println("Chosen: EN");
            model.addAttribute("lang", "en");
        } else if(lang != null && lang.equals("de")){
            locale = new Locale("de", "DE");
            System.out.println("Chosen: DE");
            model.addAttribute("lang", "de");
        } else {
            locale = new Locale("en", "US");
            model.addAttribute("lang", "en");
        }
        LocaleContextHolder.setLocale(locale);

        urlService.getURLsFromDB();
        model.addAttribute("url", new URLDTO());
        return "index";
    }

    @PostMapping("/addURL")
    public String addURL(@Valid @ModelAttribute("url") URLDTO urlDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        URLDTO createdUrl = urlService.createURL(urlDTO);
        urlService.saveURL(createdUrl);
        return "redirect:success?id=" + createdUrl.getId();
    }

    @GetMapping("success")
    public String success(@RequestParam String id, Model model) {
        model.addAttribute("id", id);
        return "success";
    }

    @GetMapping("/url")
    public String displayURL(@RequestParam String id, Model model) {
        Optional<URLDTO> url = urlService.getURL(id);
        if (url.isPresent()) {
            model.addAttribute("passwordCorrect", false);
            model.addAttribute("url", url.get());
            return "url";
        } else {
            return "notfound";
        }
    }

    @PostMapping("/url")
    public String checkPassword(@RequestParam String id, @RequestParam String name, @RequestParam String password, Model model) {
        Optional<URLDTO> urlDTO = urlService.getURL(id);
        if (urlDTO.isPresent()) {
            URLDTO url = urlDTO.get();
            model.addAttribute("url", url);

            if (url.getPassword().equals(password) && url.getName().equals(name)) {
                model.addAttribute("isCorrect", true);
            } else {
                model.addAttribute("isCorrect", false);
                model.addAttribute("error", "Invalid name or password");
            }
            return "url";
        } else {
            return "notfound";
        }
    }

    @GetMapping("/delete")
    public String deleteURL(@RequestParam String id) {
        URLDTO url = urlService.getURL(id).orElseThrow();
        if (url.getPassword() == null || url.getPassword().isEmpty()) {
            return "deleterror";
        } else {
            urlService.deleteURL(id);
            return "deletesuccess";
        }
    }

    @GetMapping("/edit")
    public String editURL(@RequestParam String id, Model model){
        Optional<URLDTO> url = urlService.getURL(id);
        if (url.isPresent()) {
            URLDTO urldto = url.get();
            model.addAttribute("url", urldto);
            return "edit";
        } else {
            return "notfound";
        }
    }

    @PostMapping("/edit")
    public String editURL(@RequestParam String id, @Valid @ModelAttribute("url") URLDTO url, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        URLDTO oldURL = urlService.getURL(id).orElseThrow();
        url.setId(id);
        url.setRedirectUrl(oldURL.getRedirectUrl());
        if (url.getPassword() == null || url.getPassword().isEmpty()) {
            url.setPassword(oldURL.getPassword());
        }
        urlService.updateURL(url);
        model.addAttribute("url", url);
        return "redirect:success?id=" + id;
    }
}
