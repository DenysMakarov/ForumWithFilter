package telran.forumservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import telran.forumservice.dto.CommentDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
@Getter
public class Post {
    @Id
    String id;
    @Setter
    String title;
    @Setter
    String content;
    @Setter
    String author;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime dateCreated = LocalDateTime.now();
    @Setter
    Set<String> tags;
    @Setter
    int likes;
//    @Setter
    Set<Comment> comments = new HashSet<>();

    public Post(String title, String content, String author, Set<String> tags) {
//        this.id = author + System.currentTimeMillis();
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
        dateCreated = LocalDateTime.now();
        comments = new HashSet<>();
    }
}
