/*
package mapper;



import dto.LoginResponseDto;
import dto.RegisterRequestDto;
import entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

 public User toEntity(RegisterRequestDto dto) {
     User user = new User();
     user.setEmail(dto.getEmail());
     user.setPassword(dto.getPassword());
     user.setRole(dto.getRole());
     return user;
 }

 public LoginResponseDto toLoginResponseDto(User user, String token) {
     LoginResponseDto dto = new LoginResponseDto();
     dto.setToken(token);
     dto.setRole(user.getRole());
     return dto;
 }
}

*/
