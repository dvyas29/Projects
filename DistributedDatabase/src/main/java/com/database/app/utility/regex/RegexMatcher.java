package com.database.app.utility.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {

    public boolean matchQuery(String query, String queryRegex) {
        Pattern pattern = Pattern.compile(queryRegex);
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            return !matcher.group(0).isBlank();
        }
        return false;
    }
}
