package com.project.navermoviesearch.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenreCode {

  DRAMA("드라마", "1"),
  FANTASY("판타지", "2"),
  WEST("서부", "3"),
  HORROR("공포", "4"),
  ROMANCE("로맨스", "5"),
  ADVENTURE("모험", "6"),
  THRILLER("스릴러", "7"),
  NOIR("느와르", "8"),
  CULT("컬트", "9"),
  DOCUMENTARY("다큐멘터리", "10"),
  COMEDY("코미디", "11"),
  FAMILY("가족", "12"),
  MYSTERY("미스터리", "13"),
  WAR("전쟁", "14"),
  ANIMATION("애니메이션", "15"),
  CRIME("범죄", "16"),
  MUSICAL("뮤지컬", "17"),
  SF("SF", "18"),
  ACTION("액션", "19"),
  MARTIAL_ARTS("무협", "20"),
  ERO("에로", "21"),
  SUSPENSE("서스펜스", "22"),
  EPIC("서사", "23"),
  BLACK_COMEDY("블랙코미디", "24"),
  EXPERIMENT("실험", "25"),
  MOVIE_CARTOON("영화카툰", "26"),
  MOVIE_MUSIC("영화음악", "27"),
  MOVIE_PARODY_POSTER("영화패러디포스터", "28");

  private final String description;
  private final String naverCode;
}
