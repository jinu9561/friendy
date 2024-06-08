package web.mvc.entity.chatting;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class chatting {

    @Id
    private Long chattingSeq;



}
