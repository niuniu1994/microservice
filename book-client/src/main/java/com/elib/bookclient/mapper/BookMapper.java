package com.elib.bookclient.mapper;

import com.elib.bookclient.dto.BookDto;
import com.elib.bookclient.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * @author kainingxin
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "publishDate",source = "date"),
            @Mapping(target="thumbnail",expression = "java(String.format(\"Book-cover-%s%s\",bookDto.getTitle(),bookDto.getThumbnail().getOriginalFilename().substring(bookDto.getThumbnail().getOriginalFilename().lastIndexOf(\".\"))))"),
            @Mapping(target="ebookUrl",expression = "java(String.format(\"Book-cover-%s.pdf\",bookDto.getTitle()))")
    })
    Book bookDto2Book(BookDto bookDto);
}
