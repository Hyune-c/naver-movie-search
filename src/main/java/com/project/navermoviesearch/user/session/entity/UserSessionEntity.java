package com.project.navermoviesearch.user.session.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@Table(name = "user_session")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
public class UserSessionEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "uuid", columnDefinition = "BINARY(16)")
  private UUID uuid;

  @JoinColumn(name = "user_id", nullable = false)
  private Long userId;
}
