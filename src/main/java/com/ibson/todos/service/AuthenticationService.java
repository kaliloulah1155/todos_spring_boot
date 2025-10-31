package com.ibson.todos.service;

import com.ibson.todos.request.AuthenticationRequest;
import com.ibson.todos.request.RegisterRequest;
import com.ibson.todos.response.AuthenticationResponse;

public interface AuthenticationService {
  void register(RegisterRequest input) throws Exception;
  AuthenticationResponse login(AuthenticationRequest request);
}
