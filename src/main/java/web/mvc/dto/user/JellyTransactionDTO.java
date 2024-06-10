package web.mvc.dto.user;


import lombok.*;
import web.mvc.enums.users.Transaction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JellyTransactionDTO {

    private Long transactionSeq;
    private Transaction transactionType;
    private String jellyAmount;
    private LocalDateTime transactionDate;

}
