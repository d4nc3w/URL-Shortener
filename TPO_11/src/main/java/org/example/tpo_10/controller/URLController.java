package org.example.tpo_10.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.tpo_10.model.URLDTO;
import org.example.tpo_10.service.URLService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE})
public class URLController {
    private final URLService urlService;
    private final ObjectMapper objectMapper;

    public URLController(URLService urlService, ObjectMapper objectMapper) {
        this.urlService = urlService;
        this.objectMapper = objectMapper;
    }

    @Tag(name = "POST", description = "Create new short URL")
    @PostMapping("api/links")
    public ResponseEntity<URLDTO> createURL(@Valid @RequestParam(required = false) String lang, @RequestBody URLDTO urlDTO) {
        Locale locale;
        if(lang != null && lang.equals("pl")) {
            locale = new Locale("pl", "PL");
        } else if(lang != null && lang.equals("en")) {
            locale = new Locale("en", "US");
        } else if(lang != null && lang.equals("de")) {
            locale = new Locale("de", "DE");
        } else {
            locale = new Locale("en", "US");
        }
        LocaleContextHolder.setLocale(locale);

        URLDTO createdURL = urlService.createURL(urlDTO);
        URI createdURLLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("api/links/{id}")
                .buildAndExpand(createdURL.getId())
                .toUri();
        return ResponseEntity.created(createdURLLocation).body(createdURL);
    }

    @Tag(name = "GET", description = "Get short URL by id")
    @GetMapping("api/links/{id}")
    public ResponseEntity<URLDTO> getURL(@PathVariable String id) {
        return urlService.getURL(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "GET", description = "Get short URL by id")
    @GetMapping("/red/{id}")
    public ResponseEntity redirectURL(@PathVariable String id) {
        urlService.updateVisits(id);
        return urlService.getURL(id)
                .map(urlDTO -> ResponseEntity.status(302)
                        .location(URI.create(urlDTO.getTargetUrl())).build())
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "PATCH", description = "Update short URL")
    @PatchMapping("api/links/{id}")
    public ResponseEntity<?> updateURL(@PathVariable String id, @RequestBody JsonMergePatch patch) {
        try {
            URLDTO urlDTO = urlService.getURL(id).orElseThrow();
            URLDTO patchedURL = applyPatch(urlDTO, patch);
            if(patchedURL.getPassword() != null){
                boolean isPasswordCorrect = urlService.checkPassword(urlDTO.getPassword(), patchedURL.getPassword());
                if(isPasswordCorrect) {
                    urlService.updateURL(patchedURL);
                    return ResponseEntity.noContent().build();
                } else {
                    String errorMsg = "wrong password";
                    return ResponseEntity.status(403).header("Wrong Password", errorMsg).body(errorMsg);
                }
            } else {
                System.out.println("You can only edit password-protected links");
                String errorMsg = "wrong password";
                return ResponseEntity.status(403).header("Wrong password", errorMsg).body(errorMsg);
            }
        } catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private URLDTO applyPatch(URLDTO urlDTO, JsonMergePatch patch) throws JsonProcessingException, JsonPatchException {
        JsonNode urlNode = objectMapper.valueToTree(urlDTO);
        JsonNode patchNode = patch.apply(urlNode);

        URLDTO updatedURL = objectMapper.treeToValue(patchNode, URLDTO.class);
        updatedURL.setId(urlDTO.getId());
        updatedURL.setRedirectUrl(urlDTO.getRedirectUrl());
        updatedURL.setVisits(urlDTO.getVisits());

        return updatedURL;
    }

    @Tag(name = "DELETE", description = "Delete short URL")
    @DeleteMapping("api/links/{id}")
    ResponseEntity<?> deleteURL(@PathVariable String id, @RequestParam(required = false) String pass) {
        try {
            URLDTO urlDTO = urlService.getURL(id).orElseThrow();
            System.out.println(urlDTO.getPassword());
            System.out.println(pass);
            if(urlDTO.getPassword() != null){
                boolean isPasswordCorrect = urlService.checkPassword(urlDTO.getPassword(), pass);
                if (isPasswordCorrect) {
                    urlService.deleteURL(id);
                    return ResponseEntity.noContent().build();

                } else {
                    String errorMsg = "wrong password";
                    return ResponseEntity.status(403).header("Wrong Password").body(errorMsg);
                }
            } else {
                System.out.println("You can only delete password-protected links");
                String errorMsg = "wrong password";
                return ResponseEntity.status(403).header("Wrong Password").body(errorMsg);
            }
        } catch (NoSuchElementException ex) {
            return ResponseEntity.noContent().build();
        }
    }

    /*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handle(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
