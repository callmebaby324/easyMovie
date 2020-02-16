package springboot.service;

import com.github.pagehelper.PageInfo;
import springboot.modal.vo.Movie;

public interface IMovieService {
    PageInfo<Movie> getMovies(int p, int limit);
}
