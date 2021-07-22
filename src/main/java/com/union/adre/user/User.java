package com.union.adre.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
// @JsonIgnoreProperties(value = {"password"})
@NoArgsConstructor
// @JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name = "adre_user")
public class User {

    @Id
    @GeneratedValue
    private Integer idx;

    @Size(min = 2)
    @ApiModelProperty(notes = "사용자 uid.")
    private String uid;

    // @JsonIgnore
    @ApiModelProperty(notes = "사용자 결제 정보.")
    private String adre_pay;

    // @JsonIgnore
    @ApiModelProperty(notes = "사용자 차단 정보.")
    private String adre_block;

    @ApiModelProperty(notes = "사용자 등록일.")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createDate;
}
