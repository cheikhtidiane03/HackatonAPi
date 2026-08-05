package com.gl.dto;

import java.util.List;

public record LoginDetails(String username, String token, List<String> roles) { }
