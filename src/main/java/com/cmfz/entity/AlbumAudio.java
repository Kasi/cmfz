package com.cmfz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AlbumAudio implements Serializable {
    /*   指定字段不返回：@JsonIgnore

       指定日期格式：@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")

       空字段不返回值：@JsonInclude(Include.NON_NULL)

       给字段指定别名：@JsonProperty("bieming")
*/
    private String id;
    private String audio_Size;
    private String audio_Name;
    //起别名
    @JsonProperty("albumMp3")
    private String audio_Url;
    private Integer is_Delete;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date audio_Time;
    private String album_Id;
    private String other;
    private String audio_Timelong;


}
