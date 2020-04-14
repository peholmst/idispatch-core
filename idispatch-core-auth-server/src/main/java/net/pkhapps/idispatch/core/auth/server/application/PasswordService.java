package net.pkhapps.idispatch.core.auth.server.application;

public interface PasswordService {

    void changePasswordForCurrentPrincipal(String currentPasswordHash, String newPasswordHash);


}
