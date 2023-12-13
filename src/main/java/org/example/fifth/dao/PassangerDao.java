package org.example.fifth.dao;

import org.example.fifth.domain.Passanger;

public interface PassangerDao {

    Passanger login(String email, String password);

    void register(String firstname, String lastname, String contactNumber, String email, String password);
}
