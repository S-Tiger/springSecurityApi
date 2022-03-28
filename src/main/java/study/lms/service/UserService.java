package study.lms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.lms.domain.user.UserEntity;
import study.lms.domain.user.UserRequestDto;
import study.lms.domain.user.UserResponseDto;
import study.lms.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Spring Security 로그인
     * @param userEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재 하지 않습니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(userEntity.getUserRoleValue()));

        return new User(userEntity.getUserEmail(), userEntity.getUserPwd(),
                true, true, true, true,
                authorities);
    }

    /**
     * 회원생성
     * @param userRequestDto
     * @return
     */
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        userRepository.findByUserEmail(userRequestDto.getUserEmail()).ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다."); });

        userRequestDto.setUserPwd(passwordEncoder.encode(userRequestDto.getUserPwd()));

        UserEntity userEntity = userRequestDto.toEntity();
        userRepository.save(userEntity);
        UserResponseDto userResponseDto = new ModelMapper().map(userEntity, UserResponseDto.class);

        return userResponseDto;
    }

    /**
     * 회원정보 업데이트
     * @param userRequestDto
     * @return
     */
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = userRepository.findByUserEmail(userRequestDto.getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재 하지 않습니다."));

        if(!StringUtils.isEmpty(userRequestDto.getUserPwd())) {
            userRequestDto.setUserPwd(passwordEncoder.encode(userRequestDto.getUserPwd()));
        }

        userEntity.toUpdate(userRequestDto);
        userRepository.save(userEntity);
        UserResponseDto userResponseDto = new ModelMapper().map(userEntity, UserResponseDto.class);

        return userResponseDto;
    }

    /**
     * 회원정보 삭제
     * @param userEmail
     */
    @Transactional
    public void deleteUser(String userEmail) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재 하지 않습니다."));

        userRepository.delete(userEntity);
    }

    /**
     * Email로 유저 조회
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true) // readOnly = 트랜잭션 범위는 유지하되 조회 속도가 개선
    public UserResponseDto getUserByEmail(String userEmail) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재 하지 않습니다."));

        UserResponseDto userResponseDto = new ModelMapper().map(userEntity, UserResponseDto.class);

        return userResponseDto;
    }

    /**
     * 모든 유저 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUserList(Pageable pageable){
        Page<UserEntity> userList = userRepository.findAll(pageable);
        if (userList.isEmpty()){
           new IllegalArgumentException("해당 데이터가 존재하지 않습니다.");
        }
        // UserList를 stream돌려서 UserEntity -> UserResponseDto 로 타입변환후 다시 리스트화
        List<UserResponseDto> result = userList.stream().map(o -> new ModelMapper().map(o, UserResponseDto.class)).collect(Collectors.toList());
        // List -> Page형태로 다시 변환후 반환 (리스트, 페이지정보, 총검색수 (result.size() 도 아마 가능할듯?))
        return new PageImpl<>(result, pageable, userList.getTotalElements());

    }
}
