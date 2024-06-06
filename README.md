# URL-Shortener

[Documentation] below:

1. URLController

URLController is a REST controller that handles requests related to URL management (creation, retrieval, update, redirection, and deletion).

Class-level Annotations:

@RestController: Indicates that this class is a REST controller.

@RequestMapping: Configures the base URL and content type for all endpoints in the controller.
path="/"
produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }

Fields:

private final URLService urlService;
private final ObjectMapper objectMapper;
  
These are initialized through constructor injection.

Constructor:

public URLController(URLService urlService, ObjectMapper objectMapper)

Methods:

* createURL

@Tag(name = "POST", description = "Create new short URL")
@PostMapping("api/links")

Creates a new short URL.

Parameters:

@Valid @RequestParam(required = false) String lang
@RequestBody URLDTO urlDTO

Returns: ResponseEntity<URLDTO>

* getURL

@Tag(name = "GET", description = "Get short URL by id")
@GetMapping("api/links/{id}")

Retrieves a short URL by its ID.

Parameter: @PathVariable String id

Returns: ResponseEntity<URLDTO>

* redirectURL

@Tag(name = "GET", description = "Get short URL by id")
@GetMapping("/red/{id}")

Redirects to the target URL by ID and updates visit count.

Parameter: @PathVariable String id

Returns: ResponseEntity

* updateURL

@Tag(name = "PATCH", description = "Update short URL")
@PatchMapping("api/links/{id}")

Updates an existing short URL.

Parameters:
@PathVariable String id
@RequestBody JsonMergePatch patch

Returns: ResponseEntity<?>

* deleteURL

@Tag(name = "DELETE", description = "Delete short URL")
@DeleteMapping("api/links/{id}")

Deletes a short URL by ID.

Parameters:
@PathVariable String id
@RequestParam(required = false) String pass

Returns: ResponseEntity<?>

* handle

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)

Handles validation exceptions.

Returns: Map<String, String>

* applyPatch

Private method that applies JSON merge patch to URLDTO.

Parameters:

URLDTO urlDTO
JsonMergePatch patch

Returns: URLDTO

2. URLWebController

URLWebController handles web requests related to the URL management via web pages.

Fields:

private final URLService urlService;

Initialized through constructor injection.

Constructor:

public URLWebController(URLService urlService)

Methods:

* showForm

@GetMapping("/")

Displays the form for URL creation.

Parameters:

@RequestParam(value = "lang", required = false) String lang
Model model
Returns: String

* addURL

@PostMapping("/addURL")

Handles form submission to create a new URL.

Parameters:

@Valid @ModelAttribute("url") URLDTO urlDTO
BindingResult bindingResult

Returns: String

* success

@GetMapping("success")

Displays success page after URL creation.

Parameters:

@RequestParam String id
Model model

Returns: String

* displayURL

@GetMapping("/url")

Displays a URL details page.

Parameters:

@RequestParam String id
Model model

Returns: String

* checkPassword

@PostMapping("/url")

Validates password for a URL.

Parameters:

@RequestParam String id
@RequestParam String name
@RequestParam String password
Model model

Returns: String

* deleteURL

@GetMapping("/delete")

Deletes a URL.

Parameter: @RequestParam String id

Returns: String

* editURL

@GetMapping("/edit")

Displays the URL edit form.

Parameters:

@RequestParam String id
Model model

Returns: String

@PostMapping("/edit")

Handles form submission to edit a URL.

Parameters:

@RequestParam String id
@Valid @ModelAttribute("url") URLDTO url
BindingResult bindingResult
Model model

Returns: String

3. URL

URL is an entity class representing the URL in the database.

Annotations:

@Entity: Marks this class as a JPA entity.
@Id: Marks id as the primary key.

Fields:

private String id;
private String targetUrl;
private String redirectUrl;
private int visits;
private String password;
private String name;
Getters and Setters:

Getters and Setters:
Standard getters and setters for all fields.

4. URLDTO

URLDTO is a Data Transfer Object used to transfer URL data.

Fields:

private String id;
private String targetUrl;
private String redirectUrl;
private int visits;
private String password;
private String name;

Getters and Setters:
Standard getters and setters for all fields.

5. URLRepository

URLRepository is a repository interface for CRUD operations on URL entities.

Annotation:

@Repository
Extends: CrudRepository<URL, String>

6. URLDTOMapper

URLDTOMapper is a service that maps URL entities to URLDTO objects and vice versa.

Methods:

map(URL url)
Converts a URL entity to a URLDTO.
Parameter: URL url
Returns: URLDTO
map(URLDTO urlDTO)
Converts a URLDTO to a URL entity.
Parameter: URLDTO urlDTO
Returns: URL

7. URLService

URLService is a service class that contains the business logic for managing URLs.

Fields:

private final URLDTOMapper urlDTOMapper;
private final URLRepository urlRepository;
private static List<String> allUrls;

Constructor:

public URLService(URLDTOMapper urlDTOMapper, URLRepository urlRepository)

Methods:

createURL(URLDTO urlDTO)
Creates a new short URL.
Parameter: URLDTO urlDTO
Returns: URLDTO
createId()
Generates a random ID.
Returns: String
createRedirectURL(String id)
Generates a redirect URL.

Parameter: String id

Returns: String

8. OpenAPIConfiguration

OpenAPIConfiguration is a configuration class for setting up OpenAPI documentation.

Methods:

defineOpenApi()

Configures the OpenAPI documentation.

Returns: OpenAPI


NotEnoughDigits

Annotation:

Annotation Class: NotEnoughDigits
Validator Class: NotEnoughDigitsValidator
Message: "{org.example.tpo_10.PasswordValidator.missing.digits}"
Description: Ensures the password contains at least 3 digits.

Validator:

Validator Method: isValid
Parameters: password (String), context (ConstraintValidatorContext)

Logic:

If the password is null or empty, it is considered valid.
Counts the number of digits in the password.
Returns true if there are at least 3 digits; otherwise, returns false.
NotEnoughLowercaseLetters

Annotation:

Annotation Class: NotEnoughLowercaseLetters
Validator Class: NotEnoughLowercaseValidator
Message: "{org.example.tpo_10.PasswordValidator.missing.lowercase}"
Description: Ensures the password contains at least 1 lowercase letter.

Validator:

Validator Method: isValid
Parameters: password (String), context (ConstraintValidatorContext)

Logic:

If the password is null or empty, it is considered valid.
Counts the number of lowercase letters in the password.
Returns true if there is at least 1 lowercase letter; otherwise, returns false.
NotEnoughSpecial

Annotation:

Annotation Class: NotEnoughSpecial
Validator Class: NotEnoughSpecialValidator
Message: "{org.example.tpo_10.PasswordValidator.missing.special}"
Description: Ensures the password contains at least 4 special characters.

Validator:

Validator Method: isValid
Parameters: password (String), context (ConstraintValidatorContext)

Logic:

If the password is null or empty, it is considered valid.
Counts the number of special characters in the password (characters that are not letters or digits).
Returns true if there are at least 4 special characters; otherwise, returns false.
NotEnoughUppercaseLetters

Annotation:

Annotation Class: NotEnoughUppercaseLetters
Validator Class: NotEnoughUppercaseValidator
Message: "{org.example.tpo_10.PasswordValidator.missing.uppercase}"
Description: Ensures the password contains at least 2 uppercase letters.
Validator:

Validator Method: isValid
Parameters: password (String), context (ConstraintValidatorContext)

Logic:

If the password is null or empty, it is considered valid.
Counts the number of uppercase letters in the password.
Returns true if there are at least 2 uppercase letters; otherwise, returns false.
NotUniqueURL

Annotation:

Annotation Class: NotUniqueURL
Validator Class: NotUniqueURLValidator
Message: "{org.example.tpo_10.UrlNotUniqueValidator.notUnique}"
Description: Ensures the URL is unique and not already present in the database.

Validator:

Validator Method: isValid
Parameters: value (String), context (ConstraintValidatorContext)

Logic:

If the URL is null, it is considered invalid.
Retrieves all URLs from the URL service.
Returns true if the URL is not present in the list of all URLs; otherwise, returns false.
PasswordTooShort

Annotation:

Annotation Class: PasswordTooShort
Validator Class: PasswordTooShortValidator
Message: "{org.example.tpo_10.PasswordValidator.too.short}"
Description: Ensures the password is at least 10 characters long.

Validator:

Validator Method: isValid
Parameters: password (String), context (ConstraintValidatorContext)

Logic:

If the password is null or empty, it is considered valid.
Counts the number of characters in the password.
Returns true if the password is at least 10 characters long; otherwise, returns false.
UrlNotHttps

Annotation:

Annotation Class: UrlNotHttps
Validator Class: UrlNotHttpsValidator
Message: "{org.example.tpo_10.UrlNotHttpsValidator.notHttps}"
Description: Ensures the URL starts with "https://".

Validator:

Validator Method: isValid
Parameters: value (String), context (ConstraintValidatorContext)

Logic:

If the URL is null, it is considered invalid.
Returns true if the URL starts with "https://"; otherwise, returns false.
UrlNotValid

Annotation:

Annotation Class: UrlNotValid
Validator Class: UrlNotValidValidator
Message: "{org.example.tpo_10.UrlNotValid.noDomain}"
Description: Ensures the URL ends with a valid domain (e.g., .com, .net, .org, etc.).

Validator:

Validator Method: isValid
Parameters: value (String), context (ConstraintValidatorContext)

Logic:

If the URL is null, it is considered invalid.
Returns true if the URL ends with a valid domain (e.g., .com, .net, .org, etc.); otherwise, returns false.

