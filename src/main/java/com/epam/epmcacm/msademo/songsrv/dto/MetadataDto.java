package com.epam.epmcacm.msademo.songsrv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDto {

    Map<String, String> metadata;

}
