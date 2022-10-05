package com.epam.epmcacm.msademo.songsrv.service;

import com.epam.epmcacm.msademo.songsrv.dto.MetadataDto;
import com.epam.epmcacm.msademo.songsrv.entity.SongMetadata;
import com.epam.epmcacm.msademo.songsrv.exception.BadRequestException;
import com.epam.epmcacm.msademo.songsrv.repository.SongMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SongMetadataService {

    @Autowired
    SongMetadataRepository songMetadataRepository;

    public String createMetadata(MetadataDto metadataDto){
        SongMetadata songMetadata = SongMetadata.builder()
                .metadata(metadataDto.getMetadata())
                .id(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .build();
        String id = songMetadataRepository.save(songMetadata).getId();
        log.info("Song metadata created with id: {}", id);
        return id;
    }

    public MetadataDto getMetadata(String id){
        Optional<SongMetadata> songMetadataOptional = songMetadataRepository.findById(id);
        log.info("Get song metadata for id: {}", id);
        SongMetadata songMetadata = songMetadataOptional
                .orElseThrow(() -> new BadRequestException("String.format(\"Error retrieving metadata with id: %s\", id)"));
        MetadataDto metadataDto = new MetadataDto(songMetadata.getMetadata());
        return metadataDto;
    }

    public List<String> deleteMetadatasByIds(List<String> ids){
        songMetadataRepository.deleteAllById(ids);
        log.info("Songs metadata deleted for {} records", ids.size());
        return ids;
    }
}
