//package com.example.ibproekt2.security;
//
//import com.example.ibproekt2.entity.User;
//import com.example.ibproekt2.repository.UserRepository;
//import jakarta.annotation.Resource;
//import jakarta.transaction.Transactional;
//import org.apache.catalina.Group;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//@Service("userDetailsService")
//@Transactional
//public class CustomUserDetailService implements UserDetailsService {
//
//    @Resource
//    UserRepository userRepository;
//
//    @Override
//    public CustomUser loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        final Optional<User> customerOpt = userRepository.findByEmail(email);
//        if (customerOpt.isEmpty()) {
//            throw new UsernameNotFoundException(email);
//        }
//        User customer = customerOpt.get();
//        boolean enabled = !customer.isAccountVerified(); // we can use this in case we want to activate account after customer verified the account
//        CustomUser user = CustomUser.CustomUserBuilder.aCustomUser()
//                .withUsername(customer.getEmail())
//                .withPassword(customer.getPassword())
//                .withEnabled(customer.isEnabled())
//                .withAuthorities(getAuthorities(customer))
//                .withSecret(customer.getSecret())
//                .withAccountNonLocked(false)
//                .build();
//
//        return user;
//    }
//
//
//
//    private Collection<GrantedAuthority> getAuthorities(User user){
//        Set<Group> userGroups = new HashSet<>(user.getRole());
//        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
//        for(Group userGroup : userGroups){
//            authorities.add(new SimpleGrantedAuthority(userGroup.getCode().toUpperCase()));
//        }
//
//        return authorities;
//    }
//}