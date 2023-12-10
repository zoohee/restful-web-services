package com.zoohee.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value= {"password", "ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;

    private String password;
    private String ssn;
}
