package com.ecommerce.user.service;


import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
//    public List<User> usersList = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyCloakAdminService keyCloakAdminService;



    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream().
                map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId().toString());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddressDTO(addressDTO);
        }
        return userResponse;
    }

    public void createUser(UserRequest userRequest) {
        String token = keyCloakAdminService.getAdminToken();
        String keycloakUserId = keyCloakAdminService.createUser(token, userRequest);
        User user = new User();
        userRequestToUser(user,userRequest);
        user.setKeyCloakId(keycloakUserId);
        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(),"USER",keycloakUserId);
        userRepository.save(user);
    }

    public Optional<UserResponse> getUser(String id) {
        return userRepository.findById(String.valueOf(id)).map(this::mapToUserResponse);
    }

    public Boolean updateUser(String id,UserRequest user) {
        return userRepository.findById(String.valueOf(id)).
                map(existingUser -> {
                    userRequestToUser(existingUser,user);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void userRequestToUser(User user,UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddressDTO() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddressDTO().getStreet());
            address.setCity(userRequest.getAddressDTO().getCity());
            address.setState(userRequest.getAddressDTO().getState());
            address.setCountry(userRequest.getAddressDTO().getCountry());
            address.setZipcode(userRequest.getAddressDTO().getZipcode());
            user.setAddress(address);
        }
    }
}
