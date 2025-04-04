package haedalSpring2025.myFirstProj.dto;

import lombok.Getter;

@Getter //읽기만함
public class UserRegistrationRequestDto {
    //request를 받는 dto
    private String username;
    private String password;
    private String name;
}
