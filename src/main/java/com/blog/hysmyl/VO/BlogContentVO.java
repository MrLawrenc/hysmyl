package com.blog.hysmyl.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Liu Mingyao
 * @since 2019-03-17 14:17
 **/
@Setter
@Getter
@AllArgsConstructor@NoArgsConstructor
public class BlogContentVO {
    private String title;
    private Date date;
    private String content;
}
