package haedalSpring2025.myFirstProj.service;

import haedalSpring2025.myFirstProj.domain.User;
import haedalSpring2025.myFirstProj.dto.UserDetailResponseDto;
import haedalSpring2025.myFirstProj.dto.UserSimpleResponseDto;
import haedalSpring2025.myFirstProj.dto.UserUpdateRequestDto;
import haedalSpring2025.myFirstProj.repository.PostRepository;
import haedalSpring2025.myFirstProj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository; //db접근하는 기능을 맡음
    private final ImageService imageService;
    private final PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository, ImageService imageService, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.postRepository = postRepository;


    }

    public UserSimpleResponseDto saveUser(User newUser) {
        // 중복 회원 검증
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new IllegalStateException("중복되는 username입니다.");
        }

        userRepository.save(newUser);
        return convertUserToSimpleDto(newUser, newUser);
    }

    //json 형태로 변환
    public UserSimpleResponseDto convertUserToSimpleDto(User currentUser, User targetUser) {
        String imageUrl = targetUser.getImageUrl();
        String imageData = imageService.encodeImageToBase64
                (System.getProperty("user.dir") + "/src/main/resources/static/" + imageUrl);
        return new UserSimpleResponseDto(
                targetUser.getId(),
                targetUser.getUsername(),
                targetUser.getName(),
                null,
                false
        );
    }

    public UserDetailResponseDto convertUserToDetailDto(User currentUser, User targetUser) {
        String imageUrl = targetUser.getImageUrl();
        String imageData = imageService.encodeImageToBase64
                (System.getProperty("user.dir") + "/src/main/resources/static/" + imageUrl);
        return new UserDetailResponseDto(
                targetUser.getId(),
                targetUser.getUsername(),
                targetUser.getName(),
                imageData,
                false,
                targetUser.getBio(),
                targetUser.getJoinedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")),
                postRepository.countByUser(targetUser),
                0L,
                0L
        );
    }


    public List<UserSimpleResponseDto> getAllUsers(User currentUser) {
        List<User> users = userRepository.findAll(); //전체
        users.remove(currentUser);
        return users.stream().map(user -> convertUserToSimpleDto(user, user)).toList();

    }

    public List<UserSimpleResponseDto> getUserByUsername(User currentUser, String username){
        List<UserSimpleResponseDto> user = new ArrayList<>();
        User targetUser = userRepository.findByUsername(username).orElse(null); //없으면 null
        if(targetUser != null){
            UserSimpleResponseDto userSimpleResponseDto = convertUserToSimpleDto(targetUser, targetUser);
            user.add(userSimpleResponseDto);
        }

        return user;
    }

    public UserDetailResponseDto updateUser(User currentUser, UserUpdateRequestDto userUpdateRequestDto) {
        //바뀐 부분만 넣어준다

        if (userUpdateRequestDto.getUsername() != null) {
            currentUser.setUsername(userUpdateRequestDto.getUsername());
        }
        if (userUpdateRequestDto.getPassword() != null) {
            currentUser.setPassword(userUpdateRequestDto.getPassword());
        }
        if (userUpdateRequestDto.getName() != null) {
            currentUser.setName(userUpdateRequestDto.getName());
        }
        if (userUpdateRequestDto.getBio() != null) {
            currentUser.setBio(userUpdateRequestDto.getBio());
        }

        userRepository.save(currentUser);

        return convertUserToDetailDto(currentUser, currentUser);
    }

    public UserDetailResponseDto getUserDetail(User currentUser, long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return convertUserToDetailDto(currentUser, targetUser);
    }

}
