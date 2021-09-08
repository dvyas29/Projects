package com.wayne.project.utility;

import java.io.BufferedReader;
import java.util.List;

public interface IFileParser {

    public List<String> loadCredentials(BufferedReader stream);
}
