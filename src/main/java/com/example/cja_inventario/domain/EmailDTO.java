package com.example.cja_inventario.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class EmailDTO {

    public EmailDTO (String[] toUser, String subject, String massage) {
        this.toUser = toUser;
        this.subject = subject;
        this.massage = massage;
    }
    private String[] toUser;
    private String subject;
    private String massage;
    
    public void setToUser(String[] toUser) {
        this.toUser = toUser;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setMassage(String massage) {
        this.massage = massage;
    }


}
