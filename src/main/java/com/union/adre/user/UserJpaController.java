package com.union.adre.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/adre")
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{idx}")
    public EntityModel<User> retrieveUser(@PathVariable int idx) {
        Optional<User> user = userRepository.findById(idx);

        if (!user.isPresent()) {
            throw  new UserNotFoundException(String.format("ID[%s] not found", idx));
        }
        EntityModel<User> model = new EntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/idx}")
                .buildAndExpand(savedUser.getIdx())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{idx}")
    public ResponseEntity<User> updatePayUser(@PathVariable int idx, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(idx);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(String.format("IDX[%s} not found", idx));
        }

        User storedUser = optionalUser.get();
        storedUser.setUid(user.getUid());
        storedUser.setAdre_pay(user.getAdre_pay());

        User updateUser = userRepository.save(storedUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/idx}")
                .buildAndExpand(updateUser.getIdx())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{idx}")
    public void deleteUser(@PathVariable int idx) {
        userRepository.deleteById(idx);
    }

}
