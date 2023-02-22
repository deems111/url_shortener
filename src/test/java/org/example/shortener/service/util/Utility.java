package org.example.shortener.service.util;

import java.util.UUID;

public interface Utility {

    String DEFAULT_NAME = "Name";
    String DEFAULT_EMAIL = "user@gmail.com";

    String DEFAULT_UNIQUE_EMAIL = "user1@gmail.com";
    String DEFAULT_URL = "https://stackoverflow.com/";

    String DEFAULT_DB_URL = "https://www.google.com/";

    String DEFAULT_SHORT_URL = "123qwe";
    UUID DEFAULT_USER_UUID = UUID.fromString("e592ad12-a48f-444e-9318-75f75e51600f");

    UUID DEFAULT_UNIQUE_USER_UUID = UUID.fromString("8c9438ab-0f45-4de6-b1c1-e33ada3e6577");

    UUID DEFAULT_SHORTENER_ID = UUID.fromString("e592ad12-a48f-444e-9318-75f75e51601f");

}
