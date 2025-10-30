package com.ibson.todos.service;

import com.ibson.todos.request.RegisterRequest;

public interface AuthenticationService {
  void register(RegisterRequest input) throws Exception;
}
