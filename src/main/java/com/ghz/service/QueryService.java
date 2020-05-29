package com.ghz.service;

import com.ghz.except.GhzException;

import java.util.Map;

public interface QueryService {

    Map<String,Object> getColAndAllGhzData() throws GhzException;
}
