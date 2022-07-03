package com.example.demo.src.post;


import com.example.demo.src.post.model.GetPostListRes;
import com.example.demo.src.post.model.GetPostsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetPostListRes> selectReviewList(){
        String selectReviewListQuery = "select postIdx, userIdx, title, content, createdAt from Review where status = 'ACTIVE' order by postIdx limit 10";
        return this.jdbcTemplate.query(selectReviewListQuery,
                (rs, rowNum) -> new GetPostListRes(
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("createdAt"))

                );
    }
    public GetPostsRes getPostsByIdx(int postIdx){
        String selectPostsQuery = "select postIdx, userIdx, title, content, createdAt, updatedAt, imgUrl from Review where postIdx=?";
        int selectPostsParam = postIdx;
        return this.jdbcTemplate.queryForObject(selectPostsQuery,
                (rs,rowNum) -> new GetPostsRes(
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("imgUrl")),
                                selectPostsParam);

    }
public int insertPosts(int userIdx, String title, String content){
        String insertPostQuery = "insert into Review(userIdx, title, content) VALUES(?, ?, ?) ";
        Object[] insertPostParams = new Object[] {userIdx, title, content};
        this.jdbcTemplate.update(insertPostQuery, insertPostParams);

        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
}
/*public int insertPostImgs(int postIdx, PostImgUrlReq postImgUrl){
     String insertPostImgsQuery = "insert into PostImgUrl (postIdx, imgUrl) VALUES(?,?)";
     Object[] insertPostImgsParams = new Object[]{postIdx, postImgUrl.getPostImgUrl()};
     this.jdbcTemplate.update(insertPostImgsQuery, insertPostImgsParams);

     String lastInsertIdxQuery = "select last_insert_id()";
     return this.jdbcTemplate.queryForObject(lastInsertIdxQuery,int.class);*/
//}
    public int updatePost(int postIdx, String content){
        String updatePostQuery = "UPDATE Review SET content=? WHERE postIdx=?;";
        Object[] updatePostParams = new Object[]{content, postIdx};
        return this.jdbcTemplate.update(updatePostQuery, updatePostParams);
    }
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }
    public int checkPostExist(int postIdx){
        String checkPostExistQuery = "select exists(select postIdx from Review where postIdx =?)";
        int checkPostExistParams = postIdx;
        return this.jdbcTemplate.queryForObject(checkPostExistQuery,
                int.class,
                checkPostExistParams);
    }
    public int deletePost(int postIdx){
        String deletePostQuery = "update Review set status = 'INACTIVE' where postIdx=?";
        Object deletePostQueryParams[] = new Object[]{postIdx};

        return this.jdbcTemplate.update(deletePostQuery,
                deletePostQueryParams);
    }
}