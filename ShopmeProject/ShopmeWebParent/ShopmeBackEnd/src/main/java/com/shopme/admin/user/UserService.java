package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {


    public static final int USERS_PER_PAGE=6;


    private UserRepository repo;

    private RoleRepository roleRepo;

    private PasswordEncoder passwordEncoder;

    public User getByEmail(String email){
        return repo.getUserByEmail(email);
    }

    public UserService(UserRepository repo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAll(){
        return (List<User>) repo.findAll(Sort.by("firstName").ascending());
    }

    public Page<User> listByPage(int pageNum,String sortField, String sortDir,String keyword){
        Sort sort=Sort.by(sortField);
        sort=sortDir.equals("asc")?sort.ascending():sort.descending();
        Pageable pageable= PageRequest.of(pageNum-1,USERS_PER_PAGE,sort);

        if(keyword!=null){
            return repo.findAll(keyword,pageable);
        }
        return repo.findAll(pageable);
    }

   public List<Role> listRoles(){
        return (List<Role>) roleRepo.findAll();
   }

    public User save(User user) {
        boolean isUpdatingUser=(user.getId()!=null);
        if(isUpdatingUser){
            User existingUser=repo.findById(user.getId()).get();
            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }
            else{
                encodePassword(user);
            }
        }else{
            encodePassword(user);
        }

       return repo.save(user);
    }

    public User updateAccount(User userInForm){
        User userInDB = repo.findById(userInForm.getId()).get();

        if(!userInForm.getPassword().isEmpty()){
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }
        if(userInForm.getPhotos()!=null){
            userInDB.setPhotos(userInForm.getPhotos());
        }
        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return repo.save(userInDB);

    }



    private void encodePassword(User user){
        String encoded=passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
    }

    public boolean isEmailunique(Integer id,String email){
        User userByEmail = repo.getUserByEmail(email);
        if(userByEmail==null) return true;
        boolean isCreatingNew=(id==null);
        if(isCreatingNew){
            if(userByEmail!=null) return false;
        }
        else{
            if(userByEmail.getId()!=id){
                return false;
            }
        }
        return true;

    }


    public User get(Integer id) throws UserNotFoundException {
        try{
            return repo.findById(id).get();
        }
        catch (NoSuchElementException ex){
            throw new UserNotFoundException("Couldn't find any user with Id "+id);
        }

    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = repo.countById(id);
        if(countById==null ||countById==0){
            throw new UserNotFoundException("Couldn't find any user with Id:"+id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public void updateUserEnabledStatus(Integer id,boolean enabled){
        repo.updateEnabledStatus(id,enabled);
    }

    public void updateUserEnabledStatu(Integer id,boolean enabled){
        User user=repo.findById(id).orElseThrow();
        user.setEmail("ilkerim@gmail.com");
        user.setEnabled(enabled);
    }
}
