package com.project.navermoviesearch.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.service.NaverSearchMovieService;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 영화 추가")
@ContextConfiguration(initializers = TestContextInitializer.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MovieAddServiceTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MovieAddService movieAddService;

  @Autowired
  private MovieRepository movieRepository;

  @MockBean
  private NaverSearchMovieService naverSearchMovieService;

  @AfterEach
  public void afterEach() throws InterruptedException {
    Thread.sleep(500L);
  }

  public static String[] existMovieTitleFromNaver() {
    return new String[]{"수사", "승리", "남산", "부장", "부산", "제주", "학교", "전쟁", "군"};
  }

  public String searchMovieResult = "{\n"
      + "    \"movieList\": [\n"
      + "        {\n"
      + "            \"title\": \"<b>국제수사</b>\",\n"
      + "            \"link\": \"https://movie.naver.com/movie/bi/mi/basic.nhn?code=175393\",\n"
      + "            \"image\": \"https://ssl.pstatic.net/imgmovie/mdi/mit110/1753/175393_P19_140119.jpg\",\n"
      + "            \"subtitle\": \"The Golden Holiday\",\n"
      + "            \"pubDate\": \"2020\",\n"
      + "            \"director\": \"김봉한|\",\n"
      + "            \"actor\": \"곽도원|김대명|김희원|김상호|\",\n"
      + "            \"userRating\": \"4.63\"\n"
      + "        }\n"
      + "    ]\n"
      + "}";

  @DisplayName("[성공]")
  @ParameterizedTest
  @MethodSource("existMovieTitleFromNaver")
  public void success(String title) throws JsonProcessingException {
    //given
    when(naverSearchMovieService.searchMovies(any()))
        .thenReturn(objectMapper.readValue(searchMovieResult, NaverSearchMovieAggregate.class));

    // when
    List<Movie> movies = movieAddService.addMovie(title);

    // then
    movies.forEach(movie -> assertThat(movieRepository.findById(movie.getId())).isPresent());
  }

  @DisplayName("[성공] 이미 존재하는 영화")
  @ParameterizedTest
  @MethodSource("existMovieTitleFromNaver")
  public void success_alreadyExists(String title) throws JsonProcessingException {
    //given
    when(naverSearchMovieService.searchMovies(any()))
        .thenReturn(objectMapper.readValue(searchMovieResult, NaverSearchMovieAggregate.class));

    movieAddService.addMovie(title);
    int beforeSize = movieRepository.findAll().size();
    assertThat(beforeSize).isGreaterThan(0);

    // when
    movieAddService.addMovie(title);

    // then
    int afterSize = movieRepository.findAll().size();
    assertThat(beforeSize).isEqualTo(afterSize);
  }
}
