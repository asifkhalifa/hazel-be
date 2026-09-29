package com.hazelngrazel.app.serviceI;

import com.hazelngrazel.app.dto.LoginRequest;
import com.hazelngrazel.app.dto.LoginResponse;
import com.hazelngrazel.app.dto.RegisterRequest;

public interface AuthServiceI {

	void register(RegisterRequest request);

	LoginResponse login(LoginRequest request);

	void changePassword(String currentPassword, String newPassword);
}
