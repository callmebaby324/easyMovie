package springboot.dao;

import org.springframework.stereotype.Repository;
import springboot.modal.vo.Movie;

import java.util.List;

/**
 * MovieMapper继承基类
 */
@Repository
public interface MovieMapper extends MyBatisBaseDao<Movie, Integer> {

    List<Movie> selectAll();

}