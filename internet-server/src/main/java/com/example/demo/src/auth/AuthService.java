package com.example.demo.src.auth;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.auth.model.PostLoginReq;
import com.example.demo.src.auth.model.PostLoginRes;
import com.example.demo.src.auth.model.User;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
import static org.hibernate.sql.InFragment.NULL;

// Service Create, Update, Delete 의 로직 처리
@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthDao authDao;

    private final JwtService jwtService;


    @Autowired
    public AuthService(AuthDao authDao, JwtService jwtService) {
        this.authDao = authDao;
        this.jwtService = jwtService;

    }

    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {
        User user = authDao.getPwd(postLoginReq);
        System.out.print(user.getPassword());

       /* try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception e) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
*/
            if(user.getPassword()!=NULL){
                if(user.getPassword().equals(postLoginReq.getPassword())){
                    int userIdx = user.getUserIdx();
                    return new PostLoginRes(userIdx);
                }else{
                    throw new BaseException(FAILED_TO_LOGIN);
                }

           }else {
               throw new BaseException(FAILED_TO_LOGIN);
            }
    }
}

