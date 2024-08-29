package org.personal.user_service.user.service;

import lombok.extern.slf4j.Slf4j;
import org.personal.user_service.config.MessageProducer;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.request.RequestUpdatePassword;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageProducer messageProducer;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MessageProducer messageProducer) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.messageProducer = messageProducer;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ResponseUser> getUserList() {

        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToResponseUser).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean registUser(RequestRegist requestRegist)  {
        try {
            if (isrequestRegistNull(requestRegist)) {
                throw new InvalidRequestException("잘못된 회원가입");
            }
            User user = userRepository.findByUserEmailOrUserNickname(requestRegist.userEmail(), requestRegist.userNickname());
            if(user != null){
                throw new InvalidRequestException(
                        ((user.getUserEmail().equals(requestRegist.userEmail()))? "이메일": "닉네임")
                                + " 중복");
            }

            user = User.regist(requestRegist, bCryptPasswordEncoder.encode(requestRegist.userPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidRequestException(e.getMessage());
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUser getUser(Long userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("잘못된 회원 번호"));
        return convertToResponseUser(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void putUserPassword(RequestUpdatePassword requestUpdatePassword) {
        String userRole = getAuthenticationUserRole();
        String userName = getAuthenticationUserName();
        // 회원 비밀번호 수정
        User user = userRepository.findById(requestUpdatePassword.userId())
                .orElseThrow(()->new NotFoundException("잘못된 회원 번호"));
        // 회원이 자신이 아니고 권한이 관리자도 아니면 예외 발생
        if(!userName.equals(user.getUserEmail()) && !userRole.equals(ROLE.ROLE_ADMIN.name()))
            throw new InvalidRequestException("잘못된 요청");

        user.setUserPassword(bCryptPasswordEncoder.encode(requestUpdatePassword.userPassword()));

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(Long userId) {

        // 회원 삭제
        log.info("userId: "+ userId);
        System.out.println("userId = " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("없는 회원번호"));

        user.deleteUser();
        messageProducer.send(convertToResponseUser(user));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDetail getUserByEmail(String userEmail) {

        User user = userRepository.findByUserEmail(userEmail);
        if (user==null)
            throw new NotFoundException("회원 정보 없음");
        return convertToResponseUserDetail(user);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseUserDetail registKakaoUser(RequestRegist requestRegistUser) {
        if (!userRepository.existsByUserEmail(requestRegistUser.userEmail()))
            registUser(requestRegistUser);
        return convertToResponseUserDetail(userRepository.findByUserEmail(requestRegistUser.userEmail()));
    }

    @Override
    public ResponseUser getMyInfo() {

        String userEmail = getAuthenticationUserName();
        System.out.println("userEmail = " + userEmail);
        return convertToResponseUser(userRepository.findByUserEmail(userEmail));
    }

    @Override
    public List<ResponseUser> getUserList(int page) {
        if(page<=0)
            throw new InvalidRequestException("잘못된 페이지입니다.");
        PageRequest pageable = PageRequest.of(page-1,10, Sort.by("userId").ascending());
        List<User> userList = userRepository.findAll(pageable).getContent();

        return userList.stream().map(this::convertToResponseUser).toList();
    }


    // 요청 Null 2차 확인 로직 (컨트롤러에서 1차 검사진행)
    private boolean isrequestRegistNull(RequestRegist requestRegist) {

        return requestRegist.userEmail() == null
                || requestRegist.userNickname() == null
                || requestRegist.userPassword() == null;
    }

    // User to ResponseUserDetail
    private ResponseUserDetail convertToResponseUserDetail(User user) {
        return new ResponseUserDetail(user);
    }

    // User to ResponseUser
    private ResponseUser convertToResponseUser(User user) {
        return new ResponseUser(user);
    }

    // 사용자 정보 가져옴
    public String getAuthenticationUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getAuthenticationUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 권한이 있는 경우 권한을 반환
        if (authentication != null && authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            return authentication.getAuthorities().iterator().next().getAuthority();
        }
        return null; // 권한이 없을 경우 null 반환
    }

}
